using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK.Models.Swap;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace OKExSDK
{
    public class SwapApi : SdkApi
    {
        private string SWAP_SEGMENT = "api/swap/v3";

        /// <summary>
        /// SwapApi构造函数
        /// </summary>
        /// <param name="apiKey">API Key</param>
        /// <param name="secret">Secret</param>
        /// <param name="passPhrase">Passphrase</param>
        public SwapApi(string apiKey, string secret, string passPhrase) : base(apiKey, secret, passPhrase) { }

        /// <summary>
        /// 单个合约持仓信息
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<string> getPositionByInstrumentAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/{instrument_id}/position";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }

        /// <summary>
        /// 所有币种合约账户信息
        /// </summary>
        /// <returns></returns>
        public async Task<string> getAccountsAsync()
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/accounts";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }

        /// <summary>
        /// 单个币种合约账户信息
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getAccountsByInstrumentAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/{instrument_id}/accounts";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取某个合约的用户配置
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getSettingsByInstrumentAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/accounts/{instrument_id}/settings";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 设定某个合约的杠杆
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="leverage">新杠杆倍数，可填写1-40之间的整数</param>
        /// <param name="side">方向:1.LONG 2.SHORT 3.CROSS</param>
        /// <returns></returns>
        public async Task<JObject> setLeverageByInstrumentAsync(string instrument_id, int leverage, string side)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/accounts/{instrument_id}/leverage";
            var body = new { leverage = leverage, side = side };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 账单流水查询
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getLedgersByInstrumentAsync(string instrument_id, int? after, int? before, int? limit, int? type)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/accounts/{instrument_id}/ledger";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (after.HasValue)
                {
                    queryParams.Add("after", after.Value.ToString());
                }
                if (before.HasValue)
                {
                    queryParams.Add("before", before.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                if (type.HasValue)
                {
                    queryParams.Add("type", type.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 下单
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="type">1:开多2:开空3:平多4:平空</param>
        /// <param name="order_type">0:普通委托 1:只做Maker 2:FOK 3:IOC 4:市价委托</param>
        /// <param name="price">委托价格</param>
        /// <param name="size">下单数量</param>
        /// <param name="client_oid">由您设置的订单ID来识别您的订单</param>
        /// <param name="match_price">是否以对手价下单(0:不是 1:是)</param>
        /// <returns></returns>
        public async Task<JObject> makeOrderAsync(string instrument_id, string type, string order_type, decimal price, int size, string client_oid, string match_price)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/order";
            var body = new
            {
                instrument_id = instrument_id,
                type = type,
                order_type = order_type,
                price = price,
                size = size,
                client_oid = client_oid,
                match_price = match_price
            };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 批量下单
        /// </summary>
        /// <param name="order">订单信息</param>
        /// <returns></returns>
        public async Task<string> makeOrdersBatchAsync(OrderBatch order)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/orders";
            var bodyStr = JsonConvert.SerializeObject(order).Replace("\"[", "[").Replace("]\"", "]").Replace("\\", "");
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }

        /// <summary>
        /// 撤单
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="order_id">订单id</param>
        /// <returns></returns>
        public async Task<JObject> cancelOrderAsync(string instrument_id, string order_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/cancel_order/{instrument_id}/{order_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, "")))
            {
                var res = await client.PostAsync(url, new StringContent("", Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 批量撤单
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="orderIds">订单id列表</param>
        /// <returns></returns>
        public async Task<JObject> cancelOrderBatchAsync(string instrument_id, List<string> orderIds)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/cancel_batch_orders/{instrument_id}";
            var body = new { order_ids = orderIds };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取所有订单列表
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="status">订单状态(-2:失败 -1:撤单成功 0:等待成交 1:部分成交 2:完全成交)</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JObject> getOrdersAsync(string instrument_id, string status, int? after, int? before, int? limit)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/orders/{instrument_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("status", status);
                if (after.HasValue)
                {
                    queryParams.Add("after", after.Value.ToString());
                }
                if (before.HasValue)
                {
                    queryParams.Add("before", before.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 通过订单ID获取单个订单信息
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="order_id">订单ID</param>
        /// <returns></returns>
        public async Task<JObject> getOrderByIdAsync(string instrument_id, string order_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/orders/{instrument_id}/{order_id}";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取成交明细
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="order_id">订单ID</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getFillsAsync(string instrument_id, string order_id, int? after, int? before, int? limit)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/fills";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("instrument_id", instrument_id);
                queryParams.Add("order_id", order_id);
                if (after.HasValue)
                {
                    queryParams.Add("after", after.Value.ToString());
                }
                if (before.HasValue)
                {
                    queryParams.Add("before", before.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取合约信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getInstrumentsAsync()
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取深度数据
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="size">返回深度数量，最大值可传200，即买卖深度共400条</param>
        /// <returns></returns>
        public async Task<JObject> getBookAsync(string instrument_id, int? depth, int? size)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/depth";

            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (size.HasValue)
                {
                    queryParams.Add("size", size.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }
        /// <summary>
        /// 获取委托单列表
        /// </summary>
        /// <param name="instrument_id">合约id</param>
        /// <param name="order_type">订单类型	1：止盈止损；2：跟踪委托；3：冰山委托；4：时间加权</param>
        /// <param name="status">订单状态  1：待生效；2：已生效；3：已撤销；4：部分生效；5：暂停生效；6：委托失败；【只有冰山和加权有4、5状态】</param>
        /// <param name="algo_id">查询指定的委托单ID</param>
        /// <param name="before">请求此id之后（更新的数据）的分页内容</param>
        /// <param name="after">请求此id之前（更旧的数据）的分页内容</param>
        /// <param name="limit">分页返回的结果集数量，默认为100，最大为100</param>
        /// <returns></returns>
        public async Task<string> getOrder_algoAsync(string instrument_id, int order_type, int? status, int? algo_id, int? before, int? after, int? limit)
        {
            var url = $"{ this.BASEURL}{this.SWAP_SEGMENT}/order_algo/{instrument_id}";

            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("order_type", order_type.ToString());
                if (status.HasValue)
                {
                    queryParams.Add("status", status.Value.ToString());
                }
                if (algo_id.HasValue)
                {
                    queryParams.Add("algo_id", algo_id.Value.ToString());
                }
                if (before.HasValue)
                {
                    queryParams.Add("before", before.Value.ToString());
                }
                if (after.HasValue)
                {
                    queryParams.Add("after", after.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{ url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getTrade_fee()
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/trade_fee";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        //委托策略下单
        public async Task<string> order_algo(string instrument_id, string type, string order_type, string size, string trigger_price, string algo_price, string algo_type)
        {
            //止盈止损 trigger_price	algo_price
            //跟踪委托callback_rate, trigger_price
            //冰山委托algo_variance, avg_amount, price_limit
            //时间加权 sweep_range,  sweep_ratio , single_limit,price_limit,time_interval
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/order_algo";
            var body = new { instrument_id = instrument_id, type = type, order_type = order_type, size = size, trigger_price = trigger_price, algo_price = algo_price , algo_type = algo_type };
            var bodyStr = JsonConvert.SerializeObject(body);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> cancel_algos(string instrument_id, string algo_ids, string order_type)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/cancel_algos";
            var body = new { instrument_id = instrument_id, algo_ids = algo_ids, order_type = order_type };
            var bodyStr = JsonConvert.SerializeObject(body).Replace("\"[", "[").Replace("]\"", "]").Replace("\\\"", "\"");
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }

        /// <summary>
        /// 获取全部ticker信息
        /// </summary>
        /// <returns></returns>
        public async Task<JContainer> getTickersAsync()
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/ticker";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取某个ticker信息
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getTickerByInstrumentId(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/ticker";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取成交数据
        /// </summary>
        /// <param name="instrument_id">合约ID，如BTC-USD-180213</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getTradesAsync(string instrument_id, int? after, int? before, int? limit)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/trades";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (after.HasValue)
                {
                    queryParams.Add("after", after.Value.ToString());
                }
                if (before.HasValue)
                {
                    queryParams.Add("before", before.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取K线数据
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="start">开始时间</param>
        /// <param name="end">结束时间</param>
        /// <param name="granularity">时间粒度，以秒为单位，必须为60的倍数</param>
        /// <returns></returns>
        public async Task<string> getCandlesDataAsync(string instrument_id, DateTime? start, DateTime? end, int? granularity)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/candles";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (start.HasValue)
                {
                    queryParams.Add("start", TimeZoneInfo.ConvertTimeToUtc(start.Value).ToString("yyyy-MM-ddTHH:mm:ss.fffZ"));
                }
                if (end.HasValue)
                {
                    queryParams.Add("end", TimeZoneInfo.ConvertTimeToUtc(end.Value).ToString("yyyy-MM-ddTHH:mm:ss.fffZ"));
                }
                if (granularity.HasValue)
                {
                    queryParams.Add("granularity", granularity.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }

        /// <summary>
        /// 获取指数信息
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getIndexAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/index";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取法币汇率
        /// </summary>
        /// <returns></returns>
        public async Task<JObject> getRateAsync()
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/rate";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取平台总持仓量
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getOpenInterestAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/open_interest";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取当前限价
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getPriceLimitAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/price_limit";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取爆仓单
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="status">状态(0:最近7天的未成交 1:最近7天的已成交)</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<string> getLiquidationAsync(string instrument_id, string status, int? from, int? to, int? limit)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/liquidation";
            using (var client = new HttpClient())
            {
                var queryParams = new Dictionary<string, string>();
                queryParams.Add("status", status);
                if (from.HasValue)
                {
                    queryParams.Add("from", from.Value.ToString());
                }
                if (to.HasValue)
                {
                    queryParams.Add("to", to.Value.ToString());
                }
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }

        /// <summary>
        /// 获取合约挂单冻结数量
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getHoldsAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/accounts/{instrument_id}/holds";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取合约下一次结算时间
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getFundingTimeAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/funding_time";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }

        /// <summary>
        /// 获取合约标记价格
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <returns></returns>
        public async Task<JObject> getMarkPriceAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/mark_price";
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return JObject.Parse(contentStr);
            }
        }
        public async Task<string> getGeneralFundingRateAsync(string instrument_id)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/historical_funding_rate";
            using (var client = new HttpClient())
            {
                var res = await client.GetAsync(url);
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        /// <summary>
        /// 获取合约历史资金费率
        /// </summary>
        /// <param name="instrument_id">合约名称，如BTC-USD-SWAP</param>
        /// <param name="from">分页游标开始</param>
        /// <param name="to">分页游标截至</param>
        /// <param name="limit">分页数据数量，默认100</param>
        /// <returns></returns>
        public async Task<JContainer> getHistoricalFundingRateAsync(string instrument_id, int? limit)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/historical_funding_rate";
            using (var client = new HttpClient())
            {
                var queryParams = new Dictionary<string, string>();
                if (limit.HasValue)
                {
                    queryParams.Add("limit", limit.Value.ToString());
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                var contentStr = await res.Content.ReadAsStringAsync();
                if (contentStr[0] == '[')
                {
                    return JArray.Parse(contentStr);
                }
                return JObject.Parse(contentStr);
            }
        }
        public async Task<string> close_position(string instrument_id,string direction)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/close_position";
            var body =new { instrument_id =instrument_id, direction = direction };
            string bodyStr = JsonConvert.SerializeObject(body).Replace("\"[", "[").Replace("]\"", "]").Replace("\\\"", "\"");
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> cancel_all(string instrument_id, string direction)
        {
            var url = $"{this.BASEURL}{this.SWAP_SEGMENT}/cancel_all";
            var body = new { instrument_id = instrument_id, direction = direction };
            string bodyStr = JsonConvert.SerializeObject(body).Replace("\"[", "[").Replace("]\"", "]").Replace("\\\"", "\"");
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> amend_order(string instrument_id, string cancel_on_fail, string order_id = "", string client_oid = "", string request_id = "", string new_size = "", string new_price = "")
        {
            string url = $"{this.BASEURL}{this.SWAP_SEGMENT}/amend_order/{instrument_id}";
            var body = new { order_id = order_id, client_oid = client_oid, request_id = request_id, new_size = new_size, new_price = new_price };
            string bodyStr = JsonConvert.SerializeObject(body).Replace("\"[", "[").Replace("]\"", "]").Replace("\\\"", "\"");
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> amend_batch_orders(string instrument_id, string cancel_on_fail, string order_id = "")
        {
            string url = $"{this.BASEURL}{this.SWAP_SEGMENT}/amend_batch_orders/{instrument_id}";
            var body = new { amend_data = "[{\"order_id\":\"305512815291895607\",\"new_size\":\"2\"},{\"order_id\":\"305512815291895606\",\"new_size\":\"1\"}]" };
            string bodyStr = JsonConvert.SerializeObject(body).Replace("\"[", "[").Replace("]\"", "]").Replace("\\\"", "\"");
            Console.WriteLine(bodyStr);
            using (var client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, bodyStr)))
            {
                var res = await client.PostAsync(url, new StringContent(bodyStr, Encoding.UTF8, "application/json"));
                var contentStr = await res.Content.ReadAsStringAsync();
                return contentStr;
            }
        }
        public async Task<string> getHistory_candles(string instrument_id, string start = "", string end = "", string granularity = "",string limit="")
        {
            string url = $"{this.BASEURL}{this.SWAP_SEGMENT}/instruments/{instrument_id}/history/candles";
            using (HttpClient client = new HttpClient(new HttpInterceptor(this._apiKey, this._secret, this._passPhrase, null)))
            {
                var queryParams = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(start))
                {
                    queryParams.Add("start", start);
                }
                if (!string.IsNullOrWhiteSpace(end))
                {
                    queryParams.Add("end", end);
                }
                if (!string.IsNullOrWhiteSpace(granularity))
                {
                    queryParams.Add("granularity", granularity);
                }
                if (!string.IsNullOrWhiteSpace(limit))
                {
                    queryParams.Add("limit", limit);
                }
                var encodedContent = new FormUrlEncodedContent(queryParams);
                var paramsStr = await encodedContent.ReadAsStringAsync();
                var res = await client.GetAsync($"{url}?{paramsStr}");
                string content = await res.Content.ReadAsStringAsync();
                return content;
            }
        }
    }
}
