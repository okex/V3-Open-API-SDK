import asyncio
import websockets
import json
import requests
import dateutil.parser as dp
import hmac
import base64
import zlib
import datetime


class OkExchangeWebSocket():

    def __init__(self, api_key=None, secret_key=None, passphrase=None):

        self.api_key = api_key
        self.secret_key = secret_key
        self.passphrase = passphrase

        self.socket_url = 'wss://real.okex.com:8443/ws/v3'
        self.server_time_url = "https://www.okex.com/api/general/v3/time"

        self.ws = None
        self.depth_data = {}
        self.channels = []

    # subscribe to channels
    async def subscribe(self, channels):
        # save channels
        self.channels = channels
        # create web socket connection
        await self.__create_ws()
        # listener
        while True:
            try:
                server_response_bytes = await asyncio.wait_for(self.ws.recv(), timeout=25)
            except (asyncio.TimeoutError, websockets.exceptions.ConnectionClosed):
                await self.__ping_pong()
            except Exception as err:
                print('Connection error: {}'.format(err))
                await self.unsubscribe()
                await self.__create_ws()
            else:
                server_response = self.__inflate(server_response_bytes).decode('utf-8')
                print("{} - {}".format(self.__get_timestamp(), server_response))

                # any logic with server messages here

                if '"table":"spot/depth"' in server_response:
                    await self.__update_depth(server_response)

    # unsubscribe from channels
    async def unsubscribe(self):

        # login
        if not None in [self.api_key, self.secret_key, self.passphrase]:
            login_str = self.__login()
            await self.ws.send(login_str)

        # unsubscribe
        sub_str = json.dumps({"op": "unsubscribe", "args": self.channels})
        await self.ws.send(sub_str)
        # print(timestamp + f"send: {sub_str}")

    # create new web socket connection
    async def __create_ws(self):
        while True:
            try:
                self.ws = await websockets.connect(self.socket_url)
                # login
                if not None in [self.api_key, self.secret_key, self.passphrase]:
                    login_str = self.__login()
                    await self.ws.send(login_str)
                # subscribe
                sub_str = json.dumps({"op": "subscribe", "args": self.channels})
                await self.ws.send(sub_str)
                break

            except Exception as err:
                print("{} - error: {}. Disconnected, reconnecting……".format(self.__get_timestamp(), err))

    # check connection
    async def __ping_pong(self):
        try:
            await self.ws.send('ping')
            server_response = await self.ws.recv()
            server_response = self.__inflate(server_response).decode('utf-8')
            print("{} ping pong: {}".format(self.__get_timestamp(), server_response))
        except Exception as err:
            print("{} - error: {}. Disconnected, reconnecting……".format(self.__get_timestamp(), err))
            await self.unsubscribe()
            await self.__create_ws()

    # update markets depth
    async def __update_depth(self, server_response):

        server_response = eval(server_response)
        instrument_id = server_response['data'][0]['instrument_id']
        server_checksum = server_response['data'][0]['checksum']
        # partial
        if server_response['action'] == 'partial':
            bids = server_response['data'][0]['bids']
            asks = server_response['data'][0]['asks']
        # update
        else:
            bids = self.__update_bids(server_response, self.depth_data[instrument_id]['bids'])
            asks = self.__update_asks(server_response, self.depth_data[instrument_id]['asks'])

        await self.__verify_checksum(instrument_id, server_checksum, bids, asks)

    # verify checksum
    async def __verify_checksum(self, instrument_id, server_checksum, bids, asks):

        if self.__compare_checksum(server_checksum, bids, asks):
            print("The verification result is：True")
            self.depth_data[instrument_id] = {
                'timestamp': self.__get_timestamp(),
                'bids': bids,
                'asks': asks}
        else:
            print("The verification result is：False，resubscribing……")
            await self.unsubscribe()
            await self.__create_ws()

    # login signature
    def __login(self):
        timestamp = str(self.__get_server_time())
        message = timestamp + 'GET' + '/users/self/verify'

        mac = hmac.new(bytes(self.secret_key, encoding='utf8'), bytes(message, encoding='utf-8'), digestmod='sha256')
        d = mac.digest()
        sign = base64.b64encode(d)

        login_param = {"op": "login", "args": [self.api_key, self.passphrase, timestamp, sign.decode("utf-8")]}
        login_str = json.dumps(login_param)
        return login_str

    def __inflate(self, data):
        decompress = zlib.decompressobj(
            -zlib.MAX_WBITS  # see above
        )
        inflated = decompress.decompress(data)
        inflated += decompress.flush()
        return inflated

    # update depth bids
    def __update_bids(self, res, old_bids):
        new_bids = res['data'][0]['bids']
        # bids合并
        for new_bid in new_bids:
            for index, old_bid in enumerate(old_bids):

                if new_bid[0] > old_bid[0]:
                    old_bids.insert(index, new_bid)
                    break

                if new_bid[0] == old_bid[0]:
                    if new_bid[1] == '0':
                        old_bids.remove(old_bid)
                        break

                    old_bids[index] = new_bid
                    break

        # print("{} combined bids: {}, number of bids: {}".format(timestamp, old_bids, len(old_bids)))
        return old_bids

    # update depth asks
    def __update_asks(self, res, old_asks):
        new_asks = res['data'][0]['asks']
        # bids合并
        for new_ask in new_asks:
            for index, old_ask in enumerate(old_asks):

                if new_ask[0] < old_ask[0]:
                    old_asks.insert(index, new_ask)
                    break

                if new_ask[0] == old_ask[0]:
                    if new_ask[1] == '0':
                        old_asks.remove(old_ask)
                        break

                    old_asks[index] = new_ask
                    break

        # print("{} combined asks: {}, number of asks: {}".format(timestamp, old_asks, len(old_asks)))
        return old_asks

    # compare checksum's
    def __compare_checksum(self, server_checksum, bids, asks):
        my_checksum = self.__calculate_checksum(bids, asks)
        return server_checksum == my_checksum

    # calculate checksum
    def __calculate_checksum(self, bids, asks):
        my_string = ""
        for i in range(25):
            if i < len(bids):
                my_string += "{}:{}:".format(bids[i][0], bids[i][1])

            if i < len(asks):
                my_string += "{}:{}:".format(asks[i][0], asks[i][1])

        my_string = my_string[:-1]
        int_checksum = zlib.crc32(my_string.encode())

        check_num = pow(2, 31) - 1
        if int_checksum > check_num:
            return int_checksum - check_num * 2 - 2
        return int_checksum

    # get server time
    def __get_server_time(self):
        server_time = ""
        response = requests.get(self.server_time_url)
        if response.status_code == 200:
            server_time = response.json()['iso']

        return dp.parse(server_time).timestamp()

    # get current time
    def __get_timestamp(self):
        now = datetime.datetime.now()
        return now.isoformat("T", "milliseconds") + "Z"


api_key = ""
secret_key = ""
passphrase = ""

# 现货
# 用户币币账户频道
# channels = ["spot/account:USDT"]
# 用户杠杆账户频道
# channels = ["spot/margin_account:BTC-USDT"]
# 用户委托策略频道
# channels = ["spot/order_algo:XRP-USDT"]
# 用户交易频道
# channels = ["spot/order:XRP-USDT"]
# 公共-Ticker频道
# channels = ["spot/ticker:ETH-USDT"]
# 公共-K线频道
# channels = ["spot/candle60s:BTC-USDT"]
# 公共-交易频道
# channels = ["spot/trade:BTC-USDT"]
# 公共-5档深度频道
# channels = ["spot/depth5:BTC-USDT"]
# 公共-400档深度频道
channels = ["spot/depth:BTC-USDT", "spot/depth:ETH-USDT"]
# 公共-400档增量数据频道
# channels = ["spot/depth_l2_tbt:BTC-USDT"]

# 交割合约
# 用户持仓频道
# channels = ["futures/position:XRP-USD-200327"]
# 用户账户频道
# channels = ["futures/account:XRP"]
# 用户交易频道
# channels = ["futures/order:BTC-USDT-200626"]
# 用户委托策略频道
# channels = ["futures/order_algo:XRP-USD-200327"]
# 公共-全量合约信息频道
# channels = ["futures/instruments"]
# 公共-Ticker频道
# channels = ["futures/ticker:BTC-USD-200626"]
# 公共-K线频道
# channels = ["futures/candle60s:BTC-USD-200626"]
# 公共-交易频道
# channels = ["futures/trade:BTC-USD-200117"]
# 公共-预估交割价频道
# channels = ["futures/estimated_price:BTC-USD-200228"]
# 公共-限价频道
# channels = ["futures/price_range:BTC-USD-200327"]
# 公共-5档深度频道
# channels = ["futures/depth5:BTC-USD-200327"]
# 公共-400档深度频道
# channels = ["futures/depth:XRP-USD-200327"]
# 公共-400档增量数据频道
# channels = ["futures/depth_l2_tbt:BTC-USD-200327"]
# 公共-标记价格频道
# channels = ["futures/mark_price:BTC-USD-200327"]

# 永续合约
# 用户持仓频道
# channels = ["swap/position:BTC-USD-SWAP"]
# 用户账户频道
# channels = ["swap/account:BTC-USD-SWAP"]
# 用户交易频道
# channels = ["swap/order:BTC-USD-SWAP"]
# 用户委托策略频道
# channels = ["swap/order_algo:LTC-USD-SWAP"]
# 公共-Ticker频道
# channels = ["swap/ticker:BTC-USD-SWAP"]
# 公共-K线频道
# channels = ["swap/candle60s:BTC-USD-SWAP"]
# 公共-交易频道
# channels = ["swap/trade:BTC-USD-SWAP"]
# 公共-资金费率频道
# channels = ["swap/funding_rate:BTC-USD-SWAP"]
# 公共-限价频道
# channels = ["swap/price_range:BTC-USD-SWAP"]
# 公共-5档深度频道
# channels = ["swap/depth5:BTC-USD-SWAP"]
# 公共-400档深度频道
# channels = ["swap/depth:BTC-USDT-SWAP"]
# 公共-400档增量数据频道
# channels = ["swap/depth_l2_tbt:BTC-USD-SWAP"]
# 公共-标记价格频道
# channels = ["swap/mark_price:BTC-USD-SWAP"]

# 期权合约
# 用户持仓频道
# channels = ["option/position:BTC-USD"]
# 用户账户频道
# channels = ["option/account:BTC-USD"]
# 用户交易频道
# channels = ["option/order:BTC-USD"]
# 公共-合约信息频道
# channels = ["option/instruments:BTC-USD"]
# 公共-期权详细定价频道
# channels = ["option/summary:BTC-USD"]
# 公共-K线频道
# channels = ["option/candle60s:BTC-USD-200327-11000-C"]
# 公共-最新成交频道
# channels = ["option/trade:BTC-USD-200327-11000-C"]
# 公共-Ticker频道
# channels = ["option/ticker:BTC-USD-200327-11000-C"]
# 公共-5档深度频道
# channels = ["option/depth5:BTC-USD-200327-11000-C"]
# 公共-400档深度频道
# channels = ["option/depth:BTC-USD-200327-11000-C"]
# 公共-400档增量数据频道
# channels = ["option/depth_l2_tbt:BTC-USD-200327-11000-C"]

# ws公共指数频道
# 指数行情
# channels = ["index/ticker:BTC-USD"]
# 指数K线
# channels = ["index/candle60s:BTC-USD"]
if __name__ == "__main__":
    # public data (no login)
    my_ws_listener = OkExchangeWebSocket()

    # private data (login)
    # my_ws_listener = OkExchangeWebSocket(api_key, secret_key, passphrase)

    # main loop
    loop = asyncio.get_event_loop()
    loop.run_until_complete(my_ws_listener.subscribe(channels))
    loop.close()
