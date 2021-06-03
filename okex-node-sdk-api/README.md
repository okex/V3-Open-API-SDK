**apikey**相关申请和修改，请在登录 **"www.okex.com"**成功后，"**账户-个人中心-我的API**"申请V3 API页面进行相关操作。
###安装方法

	npm i @okfe/okex-node
	
使用指南

	const { PublicClient } = require(‘@okfe/okex-node’);
	const { V3WebsocketClient } = require(‘@okfe/okex-node’);
	const { AuthenticatedClient } = require(‘@okfe/okex-node’);
	const pClient = new PublicClient();
	const authClient = new AuthenticatedClient(key,secret, passphrase);
	const wss = new V3WebsocketClient();

测试Demo：
https://github.com/zisuting/okex-node-sdk-sample

### API指南
**WebSocket**行情订阅和推送，交易推送API，对应的源码文件是
**WebsocketClient.js**

用法如：

	wss.connect();
	wss.login(key, secret,passpharase )
	wss.subscribe(‘spot/depth5:ETH-USDT’);
	

接口  | 说明
------------- | -------------
connect()  | websocket连接
subscribe(..args)  | 行情订阅
unsubscribe(...args) | 取消行情订阅
checksum(data)	| 检查数据有效性

## <a name="markdown-pane"></a>
**HTTP**接口部分是下单，撤单等操作

**PublicClient.js**是无需登录即可获取的数据接口，

用法实例如:

	pClient.spot().getSpotBook(‘BTC-USDT’, {‘size’:’10’});


<mark>币币API</mark>


接口  | 说明
------------- | -------------
spot().getSpotInstruments()	| 获取币对信息
spot().getSpotBook(instrument_id, params)| 获取深度数据
spot().getSpotTicker(instrument_id)|	获取tiker信息
spot().getSpotTrade(instrument_id, params)|获取成交数据
spot().getSpotCandles(instrument_id, params)|获取k线数据



<mark>交割合约API</mark>

接口  | 说明
------------- | -------------
futures().getInstruments()	|交割合约信息
futures().getBook(instrument_id, params)|获取交割合约深度信息
futures().getTicker(instrument_id))	|获取ticker信息
futures().getTrades(instrument_id, params)|获取成交数据
futures().getCandles(instrument_id, params)|获取k线数据
futures().getIndex(instrument_id)|	获取指数信息
futures().getRate()	|获取法币汇率
futures().getEstimatedPrice(instrument_id)|获取预估交割价
futures().getOpenInterest(instrument_id)|获取平台总持仓量
futures().getPriceLimit(instrument_id)	|获取当前限价
futures().getLiquidation(instrument_id, params)|获取爆仓单
futures().getMarkPrice(instrument_id) | 获取标记价格


<mark>永续合约API</mark>

接口  | 说明
------------- | -------------
swap().getInstruments()	|合约信息
swap().getDepth(instrument_id,size)	|深度信息
swap().getTicker(instrument_id)	|Tiker信息
swap().getTrades(instrument_id, params)	|获取成交数据
swap().getCandles(instrument_id, params)|获取k线数据
swap().getIndex(instrument_id)	|获取指数信息
swap().getRate()	|获取法币汇率
swap().getOpenInterest(instrument_id)	|获取平台总持仓量
swap().getPriceLimit(instrument_id)	|获取当前限价
swap().getLiquidation(instrument_id, params)|获取爆仓单
swap().getFundingTime(instrument_id)	|获取成交数据
swap().getMarkPrice(instrument_id)	|获取k线数据
swap().getHistoricalFudingRate(instrument_id, params)|获取合约历史资金费率

<mark>ETT API</mark>

接口  | 说明
------------- | -------------
ett().getConstituents(ett)	|获取组合成分
ett().getDefinePrice(ett)	|获取ETT清算历史定价

***
**AuthenticatedClient.js**是需要登录验证才可以调用的接口，包括下单，撤单等

用法实例如下：

	authClient.spot().getAccounts();
	authClient.swap().postOrder({“match_price”:”1”, “price”:”109”,”type”:”1”, “instrument_id”:”BTC-USD_SWAP”});


<mark>币币API相关</mark>

接口  | 说明
------------- | -------------
spot().getAccounts(currency)	|币币账户信息(currency不填则返回所有币种)
spot().getLedger(currency)	|账单流水
spot().postOrder(params)	|下单
spot().postBatchOrders(params)	|批量下单
spot().postCancelOrder(order_id, params)|撤销指定订单
spot().postCancelBatchOrders(params)	|批量撤销订单
spot().getOrders(params)	|获取订单列表
spot().getOrdersPending(params)	|获取所有未成交订单
spot().getOrder(order_id, params)	|获取订单信息
spot().getFills(params)  |	获取成交明细
spot().getTradeFee(instrument_id?:string) 获取当前账户费率

<mark>币币杠杆API</mark>

接口  | 说明
------------- | -------------
margin().getAccounts(instrument_id)	|币币账户信息(currency不填则返回所有币种)
margin().getLedger(instrument_id)	|账单流水
margin().getAvailability(instrument_id) | 杠杆配置信息(currency填写表示某个杠杆信息否则返回所有)
margin().getborrowed(instrument_id) |  获取借币记录(currency填写表示某个账户借币信息否则代表所有)
margin().postBorrow(params) | 借币接口
margin().postRepayment(params) | 还币
margin().postOrder(params) | 下单
margin().postBatchOrder(params) | 批量下单
margin().postCancelOrder(params) | 撤销指定订单
margin().postCancelBatchOrders(params) | 批量撤销订单
margin().getOrders(params) | 获取订单列表
margin().getOrder(order_id, params) | 获取指定订单ID的信息
margin().getOrdersPending(params) | 获取所有未成交订单
margin().getFills(params) | 获取成交明细



<mark>账户API相关</mark>

接口  | 说明
------------- | -------------
account().getCurrencies()	|获取币种列表
account().getWithdrawalFee(currency)	|提币手续费（currency不填则返回所有）
account().getWallet(currency)	|钱包账户信息(currency不填则获取所有币种，否则获取指定币种的信息)
account().postTransfer(params)	|资金划转
account().postWithdrawal(params)	|提币
account().getWithdrawalHistory(currency)|查询最近的提币记录
account().getLedger(params)	|账单流水查询(不填参数返回所有)
account().getAddress(params)	|获取充值地址
account().getDepositHistory(currency)	|获取所有币种/单个 充值记录

<mark>交割合约API相关</mark>

接口  | 说明
------------- | -------------
futures().getPosition(instrument_id)	|所有合约/单个合约持仓信息
futures().getAccounts(currency)	|所有合约/单个合约账户信息 
futures().getLeverage(currency)	|获取合约币种杠杆倍数
Futures().postLeverage(currency, params)|设定合约币种杠杆倍数
futures().getAccountsLedger(currency, params)|账单流水查询
futures().postOrder(params)	|下单
futures().postOrders(params)	|批量下单
futures().cancelOrder(instrument_id, order_id)|撤销指定订单
futures().cancrdelBatchOers(instrument_id, params)|批量撤销订单
futures().getOrders(instrument_id, params)|获取订单列表
futures().getOrder(instrument_id, order_id)|获取订单信息
futures().getFills(params)	|获取成交明细
futures().getHolds(instrument)	|获取合约挂单冻结数量

<mark>永续合约API</mark>

接口  | 说明
------------- | -------------
swap().getPosition(instrument_id)	|所有合约/单个合约持仓信息
swap().getAccount(instrument_id)	|所有币种/单个币账户信息
swap().getSettings(instrument_id)	|获取某个合约的用户配置
swap().postLeverage(instrument_id, params)|设定某个合约的杠杆
swap().getLedger(instrument_id)	|账单流水查询
swap().postOrder(params)	|下单
swap().postBatchOrder(params)	|批量下单
swap().postCancelOrder(instrument_id, order_id)|撤单
swap().postCancelBatchOrder(instrument_id, params)|批量撤销订单
swap().getOrders(instrument_id, params)	|获取所有订单列表
swap().getFills(params)	|获取成交明细
swap().getHolds(instrument)	|获取合约挂单冻结数量
swap().getOrder(instrument_id, order_id)|获取订单信息


<mark>ETT API</mark>

接口  | 说明
------------- | -------------
ett().getAccounts(currency)	|组合/单一币种账户信息
ett().getAccountsLedger(currency)	|账单流水查询
ett().postOrder(currency)	|下单
ett().deleteOrder(order_id)	|撤销指定订单
ett().getOrders(params)	|获取订单列表
ett().getOrder(order_id)	|获取订单信息

