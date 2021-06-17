using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using OKExSDK;
using OKExSDK.Models;
using OKExSDK.Models.Account;
using OKExSDK.Models.Ett;
using OKExSDK.Models.Futures;
using OKExSDK.Models.Margin;
using OKExSDK.Models.Spot;
using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Net;
using System.Net.WebSockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using swap = OKExSDK.Models.Swap;

namespace SampleCS
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        private GeneralApi generalApi;
        private AccountApi accountApi;
        private FuturesApi futureApi;
        private SpotApi spotApi;
        private MarginApi marginApi;
        private EttApi ettApi;
        private SwapApi swapApi;
        private OptionApi optionApi;
        private IndexApi indexApi;
        private SystemApi systemApi;
        private InformationAPI informationApi;

        private string apiKey = "";
        private string secret = "";
        private string passPhrase = "";

        public MainWindow()
        {
            InitializeComponent();
            this.generalApi = new GeneralApi(this.apiKey, this.secret, this.passPhrase);
            this.futureApi = new FuturesApi(this.apiKey, this.secret, this.passPhrase);
            this.accountApi = new AccountApi(this.apiKey, this.secret, this.passPhrase);
            this.spotApi = new SpotApi(this.apiKey, this.secret, this.passPhrase);
            this.marginApi = new MarginApi(this.apiKey, this.secret, this.passPhrase);
            this.ettApi = new EttApi(this.apiKey, this.secret, this.passPhrase);
            this.swapApi = new SwapApi(this.apiKey, this.secret, this.passPhrase);
            this.optionApi = new OptionApi(this.apiKey, this.secret, this.passPhrase);
            this.indexApi = new IndexApi(this.apiKey, this.secret, this.passPhrase);
            this.systemApi = new SystemApi(this.apiKey, this.secret, this.passPhrase);
            this.informationApi = new InformationAPI(this.apiKey, this.secret, this.passPhrase);
            this.DataContext = new MainViewModel();
            System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12 | SecurityProtocolType.Tls;
        }
        private void btnSetKey(object sender, RoutedEventArgs e)
        {
            var keyinfo = ((MainViewModel)this.DataContext).KeyInfo;
            apiKey = keyinfo.api_key;
            secret = keyinfo.secret;
            passPhrase = keyinfo.passphrase;
            this.generalApi = new GeneralApi(apiKey, secret, passPhrase);
            this.accountApi = new AccountApi(apiKey, secret, passPhrase);
            this.futureApi = new FuturesApi(apiKey, secret, passPhrase);
            this.spotApi = new SpotApi(apiKey, secret, passPhrase);
            this.marginApi = new MarginApi(apiKey, secret, passPhrase);
            this.ettApi = new EttApi(apiKey, secret, passPhrase);
            this.swapApi = new SwapApi(apiKey, secret, passPhrase);
            this.optionApi = new OptionApi(apiKey, secret, passPhrase);
            Console.WriteLine("完成");
        }

        private async void btnSyncServerTimeClick(object sender, RoutedEventArgs e)
        {
            try
            {
                var result = await this.generalApi.syncTimeAsync();
                Console.WriteLine(JsonConvert.SerializeObject(result));
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetPositions(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getPositionsAsync();

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetPositionByInstrumentId(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getPositionByIdAsync("EOS-USD-191227");

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetFuturesAccounts(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getAccountsAsync();

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOneCurencyAccounts(object sender, RoutedEventArgs e)
        {
            try
            {
                var currency = "btc-usd";
                var resResult = await this.futureApi.getAccountByCurrencyAsync(currency);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    //{"equity":"1.224","margin":"0","realized_pnl":"0","unrealized_pnl":"0","margin_ratio":"10000","margin_mode":"crossed","total_avail_balance":"1.224"}
                    var margin_mode = (string)resResult["margin_mode"];
                    if (margin_mode == "crossed")
                    {
                        //全仓
                        var accountInfo = resResult.ToObject<AccountCrossed>();
                        Console.WriteLine(currency + "：" + JsonConvert.SerializeObject(accountInfo));
                    }
                    else
                    {
                        //逐仓
                        var accountInfo = resResult.ToObject<AccountFixed>();
                        Console.WriteLine(currency + "：" + JsonConvert.SerializeObject(accountInfo));
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetLeverage(object sender, RoutedEventArgs e)
        {
            try
            {

                var resResult = await this.futureApi.getLeverageAsync("btc-usd");

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnSetCrossedLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.setCrossedLeverageAsync("BTC-USDT", 20);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var result = resResult.ToObject<SetCrossedLeverageResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(result));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnSetFixedLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.setFixedLeverageAsync(this.currency.Text, int.Parse(this.leverage.Text), this.instrument_id.Text, this.direction.Text);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var result = resResult.ToObject<SetCrossedLeverageResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(result));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                //[{"ledger_id":"1730792498235392","timestamp":"2018-11-02T08:03:17.0Z","amount":"-0.019","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730166161965057","timestamp":"2018-11-02T05:24:00.0Z","amount":"-0.007","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1730166159284224,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730166161965056","timestamp":"2018-11-02T05:24:00.0Z","amount":"-0.005","balance":"-13","currency":"EOS","type":"match","details":{"order_id":1730166159284224,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730165947662337","timestamp":"2018-11-02T05:23:57.0Z","amount":"-0.007","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1730165941482496,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1730165947662336","timestamp":"2018-11-02T05:23:57.0Z","amount":"0","balance":"13","currency":"EOS","type":"match","details":{"order_id":1730165941482496,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1691155227772928","timestamp":"2018-10-26T08:03:00.0Z","amount":"0.00052289","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1689818088308738","timestamp":"2018-10-26T02:22:57.0Z","amount":"-0.00055597","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1689818080547840,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1689818088308737","timestamp":"2018-10-26T02:22:57.0Z","amount":"0.004","balance":"-1","currency":"EOS","type":"match","details":{"order_id":1689818080547840,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684987004485635","timestamp":"2018-10-25T05:54:21.0Z","amount":"-0.0005571","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1684986992538624,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684987004485634","timestamp":"2018-10-25T05:54:21.0Z","amount":"0","balance":"1","currency":"EOS","type":"match","details":{"order_id":1684986992538624,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684986777075713","timestamp":"2018-10-25T05:54:17.0Z","amount":"-0.0005571","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1684986768278528,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684986777075712","timestamp":"2018-10-25T05:54:17.0Z","amount":"-0.001","balance":"-1","currency":"EOS","type":"match","details":{"order_id":1684986768278528,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684129732001795","timestamp":"2018-10-25T02:16:20.0Z","amount":"-0.00055741","balance":"0","currency":"EOS","type":"fee","details":{"order_id":1684129718574080,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1684129732001794","timestamp":"2018-10-25T02:16:20.0Z","amount":"0","balance":"1","currency":"EOS","type":"match","details":{"order_id":1684129718574080,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1651521273235456","timestamp":"2018-10-19T08:03:34.0Z","amount":"-0.2","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181228"}},{"ledger_id":"1651521271793668","timestamp":"2018-10-19T08:03:34.0Z","amount":"0","balance":"0","currency":"EOS","type":"match","details":{"order_id":0,"instrument_id":"EOS-USD-181019"}}]
                var resResult = await this.futureApi.getLedgersByCurrencyAsync("btc-usdt", null, null, 10);

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private Dictionary<string, string> orderTypes { get; set; } = new Dictionary<string, string>();

        private async void btnMakeOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).OrderSingle;
                var resResult = await this.futureApi.makeOrderAsync(order.instrument_id,
                    order.type,
                    order.price,
                    order.size,
                    order.leverage,
                    order.client_oid,
                    order.match_price == "True" ? "1" : "0");

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).OrderBatch;
                var orderDetails = ((MainViewModel)this.DataContext).OrderDetails;
                orderDetails.ForEach(od =>
                {
                    od.match_price = od.match_price == "True" ? "1" : "0";
                });
                order.orders_data = JsonConvert.SerializeObject(orderDetails);
                var resResult = await this.futureApi.makeOrdersBatchAsync(order);

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.cancelOrderAsync(this.instrument_id_cancel.Text, this.order_id.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<CancelOrderResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var orderIds = new List<long>();
                orderIds.Add(long.Parse(this.order_id.Text));
                var resResult = await this.futureApi.cancelOrderBatchAsync(this.instrument_id_cancel.Text, orderIds);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<CancelOrderBatchResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getOrdersAsync("TRX-USD-191122", "2", null, null, 10);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ledgers = resResult.ToObject<OrderListResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOrderById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getOrderByIdAsync("ETC-USD-191213", "4017095317468161");


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetFills(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getFillsAsync("TRX-USD-191129", "376549434978166784", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var fills = resResult.ToObject<List<Fill>>();
                    Console.WriteLine(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetInstruments(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getInstrumentsAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var instruments = resResult.ToObject<List<Instrument>>();
                    Console.WriteLine(JsonConvert.SerializeObject(instruments));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetBook(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getBookAsync("EOS-USD-191227", 20);
                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var book = resResult.ToObject<Book>();
                    Console.WriteLine(JsonConvert.SerializeObject(book));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetTickers(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getTickersAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ticker = resResult.ToObject<List<Ticker>>();
                    Console.WriteLine(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetTickerById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getTickerByInstrumentId("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ticker = resResult.ToObject<Ticker>();
                    Console.WriteLine(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetTrades(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getTradesAsync("BTC-USD-191129", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var trades = resResult.ToObject<List<Trade>>();
                    Console.WriteLine(JsonConvert.SerializeObject(trades));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetKData(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getCandlesDataAsync("EOS-USD-191227", DateTime.UtcNow.AddMinutes(-1), DateTime.UtcNow, 60);
                Console.WriteLine(resResult);
                //if (resResult.Type == JTokenType.Object)
                //{
                //    JToken codeJToken;
                //    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                //    {
                //        var errorInfo = resResult.ToObject<ErrorResult>();
                //        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                //    }
                //}
                //else
                //{
                //    var candles = resResult.ToObject<List<List<decimal>>>();

                //    Console.WriteLine(JsonConvert.SerializeObject(candles));
                //}
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetIndex(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getIndexAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var index = resResult.ToObject<Index>();
                    Console.WriteLine(JsonConvert.SerializeObject(index));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetRate(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getRateAsync();

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var rate = resResult.ToObject<Rate>();
                    Console.WriteLine(JsonConvert.SerializeObject(rate));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetEstimatedPrice(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getEstimatedPriceAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var estimatedPrice = resResult.ToObject<EstimatedPrice>();
                    Console.WriteLine(JsonConvert.SerializeObject(estimatedPrice));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOpenInteest(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getOpenInterestAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var openInterest = resResult.ToObject<OpenInterest>();
                    Console.WriteLine(JsonConvert.SerializeObject(openInterest));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnPriceLimit(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getPriceLimitAsync("EOS-USD-191227");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var priceLimit = resResult.ToObject<PriceLimit>();
                    Console.WriteLine(JsonConvert.SerializeObject(priceLimit));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnLiquidation(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getLiquidationAsync("BTC-USD-191129", "0", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var liquidations = resResult.ToObject<List<Liquidation>>();
                    Console.WriteLine(JsonConvert.SerializeObject(liquidations));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnHolds(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.getHoldsAsync("EOS-USD-191227");

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetCurrencies(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.accountApi.getCurrenciesAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var currencies = resResult.ToObject<List<Currency>>();
                    Console.WriteLine(JsonConvert.SerializeObject(currencies));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetWallet(object sender, RoutedEventArgs e)
        {
            //资金账户信息
            try
            {
                var resResult = await this.accountApi.getWalletInfoAsync();
                Console.WriteLine(resResult);
                //if (resResult.Type == JTokenType.Object)
                //{

                //    JToken codeJToken;
                //    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                //    {
                //        var errorInfo = resResult.ToObject<ErrorResult>();
                //        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                //    }
                //}
                //else
                //{
                //    var walletInfo = resResult.ToObject<List<Wallet>>();
                //    Console.WriteLine(JsonConvert.SerializeObject(walletInfo));
                //}
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetWalletByCurrency(object sender, RoutedEventArgs e)
        {
            //单一账户币种信息
            try
            {
                var resResult = await this.accountApi.getWalletInfoByCurrencyAsync("usdt");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var walletInfo = resResult.ToObject<List<Wallet>>();
                    Console.WriteLine(JsonConvert.SerializeObject(walletInfo));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeTransfer(object sender, RoutedEventArgs e)
        {
            //资金划转
            try
            {
                var resResult = await this.accountApi.makeTransferAsync(((MainViewModel)this.DataContext).Transfer);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var transferResult = resResult.ToObject<TransferResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(transferResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeWithDrawal(object sender, RoutedEventArgs e)
        {
            //提币
            try
            {
                var resResult = await this.accountApi.makeWithDrawalAsync(((MainViewModel)this.DataContext).WithDrawal);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var withdrawalResult = resResult.ToObject<WithDrawalResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(withdrawalResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetWithdrawalFee(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.accountApi.getWithDrawalFeeAsync("eos");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var fee = resResult.ToObject<List<WithdrawalFee>>();
                    Console.WriteLine(JsonConvert.SerializeObject(fee));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetWithdrawalHistory(object sender, RoutedEventArgs e)
        {
            //提币记录
            try
            {
                var resResult = await this.accountApi.getWithDrawalHistoryAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<WithDrawalHistory>>();
                    Console.WriteLine(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetWithdrawalHistoryByCurrency(object sender, RoutedEventArgs e)
        {
            //单个币种提币记录
            try
            {
                var resResult = await this.accountApi.getWithDrawalHistoryByCurrencyAsync("eos");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<WithDrawalHistory>>();
                    Console.WriteLine(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetWalletLedger(object sender, RoutedEventArgs e)
        {
            //账单流水查询
            try
            {
                var resResult = await this.accountApi.getLedgerAsync("usdt", "20", null, null, 10);
                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetDepositAddress(object sender, RoutedEventArgs e)
        {
            //获取充值地址
            try
            {
                var resResult = await this.accountApi.getDepositAddressAsync("eos");

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetDepositHistory(object sender, RoutedEventArgs e)
        {
            //获取所有币种充值记录
            try
            {
                var resResult = await this.accountApi.getDepositHistoryAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<DepositHistory>>();
                    Console.WriteLine(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetDepositHistoryByCurrency(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.accountApi.getDepositHistoryByCurrencyAsync("eth");
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var history = resResult.ToObject<List<DepositHistory>>();
                    Console.WriteLine(JsonConvert.SerializeObject(history));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotAccount(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getSpotAccountsAsync();
                Console.WriteLine(resResult);
                //if (resResult.Type == JTokenType.Object)
                //{
                //    JToken codeJToken;
                //    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                //    {
                //        var errorInfo = resResult.ToObject<ErrorResult>();
                //        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                //    }
                //}
                //else
                //{
                //    var spotaccount = resResult.ToObject<List<SpotAccount>>();
                //    Console.WriteLine(JsonConvert.SerializeObject(spotaccount));
                //}
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotAccountByCurrency(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getAccountByCurrencyAsync("trx");


                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getSpotLedgerByCurrencyAsync("trx", null, null, 10, null);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var walletLedger = resResult.ToObject<List<SpotLedger>>();
                    Console.WriteLine(JsonConvert.SerializeObject(walletLedger));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeMarketOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.makeOrderAsync<SpotOrderMarket>(((MainViewModel)this.DataContext).SpotOrderMarket);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<SpotOrderResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeLimitOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.makeOrderAsync<SpotOrderLimit>(((MainViewModel)this.DataContext).SpotOrderLimit);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<SpotOrderResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnMakeMarketOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).SpotOrderMarket;
                var resResult = await this.spotApi.makeOrderBatchAsync<SpotOrderMarket>(new List<SpotOrderMarket>() { order });

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        Console.WriteLine(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<SpotOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnMakeLimitOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).SpotOrderLimit;
                var resResult = await this.spotApi.makeOrderBatchAsync<SpotOrderLimit>(new List<SpotOrderLimit>() { order });

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        Console.WriteLine(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<SpotOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelSpotOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.cancelOrderByOrderIdAsync(this.spot_order_id.Text, this.spot_instrument_id.Text, null);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<SpotOrderResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelSpotOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var orders = new List<CancelOrderBatch>();
                var order = new CancelOrderBatch()
                {
                    instrument_id = this.spot_instrument_id.Text,
                    order_ids = new List<long>() { long.Parse(this.spot_order_id.Text) },
                };
                orders.Add(order);
                var resResult = await this.spotApi.cancelOrderBatchAsync(orders);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        Console.WriteLine(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<SpotOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getOrdersAsync("BTC-USDT", "7", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var orders = resResult.ToObject<List<OrderFullInfo>>();
                    Console.WriteLine(JsonConvert.SerializeObject(orders));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetPendingOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getPendingOrdersAsync("CAI-BTC", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var orders = resResult.ToObject<List<OrderFullInfo>>();
                    Console.WriteLine(JsonConvert.SerializeObject(orders));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotOrderById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getOrderByIdAsync(this.spotinstrument_id.Text, this.spotorder_id.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var fills = resResult.ToObject<OrderFullInfo>();
                    Console.WriteLine(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnGetSpotFills(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getFillsAsync(long.Parse(this.spotorder_id.Text), this.spotinstrument_id.Text, 1, null, 10);
                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotInstruments(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getInstrumentsAsync();

                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var instruments = resResult.ToObject<List<SpotInstrument>>();
                    Console.WriteLine(JsonConvert.SerializeObject(instruments.Take(10)));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotBook(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getBookAsync("CAI-BTC", null, null);
                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var book = resResult.ToObject<SpotBook>();
                    Console.WriteLine(JsonConvert.SerializeObject(book));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotTicker(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getTickerAsync();

                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var tickers = resResult.ToObject<List<SpotTicker>>();
                    Console.WriteLine(JsonConvert.SerializeObject(tickers.Take(10)));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotTickerByInstrument(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getTickerByInstrumentIdAsync("BTC-USDT");


                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ticker = resResult.ToObject<SpotTicker>();
                    Console.WriteLine(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotTrades(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getTradesAasync("CAI-BTC", null, null, 10);

                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var trades = resResult.ToObject<List<SpotTrade>>();
                    Console.WriteLine(JsonConvert.SerializeObject(trades));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSpotCandles(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getCandlesAsync("CAI-BTC", DateTime.UtcNow.AddHours(-1), DateTime.UtcNow, 60);


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarginAccount(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAccountsAsync();


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarginAccountByInstrument(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAccountsByInstrumentIdAsync("BTC_USDT");


                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarginLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getLedgerAsync("BTC_USDT", null, null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var marginLedger = resResult.ToObject<List<MarginLedger>>();
                    Console.WriteLine(JsonConvert.SerializeObject(marginLedger));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetAvailability(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAvailabilityAsync();

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetAvailable(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getAvailabilityByInstrumentId("BTC_USDT");

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetBorrowed(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getBorrowedAsync("0", null, null, 10);

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetBorrowByInstrument(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getBorrowedByInstrumentIdAsync("BTC_USDT", "0", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var borrows = resResult.ToObject<List<Borrowed>>();
                    Console.WriteLine(JsonConvert.SerializeObject(borrows));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeBorrow(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.makeBorrowAsync(this.borrow_instrument_id.Text, this.borrow_currency.Text, this.borrow_amount.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var borrowResult = resResult.ToObject<BorrowResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(borrowResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeRepayment(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.makeRepaymentAsync(long.Parse(this.repay_borrow_id.Text), this.repay_instrument_id.Text, this.repay_currency.Text, this.repay_amount.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var borrowResult = resResult.ToObject<RepaymentResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(borrowResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeMarginMarketOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).MarginOrderMarket;
                order.order_type = "0";
                var resResult = await this.marginApi.makeOrderAsync<MarginOrderMarket>(order);


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeMarginMarketOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).MarginOrderMarket;
                order.order_type = "0";
                var resResult = await this.marginApi.makeOrderBatchAsync<MarginOrderMarket>(new List<MarginOrderMarket>() { order });


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeMarginLimitOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.makeOrderAsync<MarginOrderLimit>(((MainViewModel)this.DataContext).MarginOrderLimit);

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeMarginLimitOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).MarginOrderLimit;
                order.order_type = "0";
                var resResult = await this.marginApi.makeOrderBatchAsync<MarginOrderLimit>(new List<MarginOrderLimit>() { order });

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelMarginOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.cancelOrderByOrderIdAsync(this.margin_order_id.Text, this.margin_instrument_id.Text, null);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<MarginOrderResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelMarginOrderBatch(object sender, RoutedEventArgs e)
        {
            try
            {
                var orders = new List<MarginCancelOrderBatch>();
                var order = new MarginCancelOrderBatch()
                {
                    instrument_id = this.margin_instrument_id.Text,
                    order_ids = new List<long>() { long.Parse(this.margin_order_id.Text) },
                };
                orders.Add(order);
                var resResult = await this.marginApi.cancelOrderBatchAsync(orders);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var obj = resResult.Value<JObject>();
                    foreach (var property in obj)
                    {
                        Console.WriteLine(property.Key + ":" + JsonConvert.SerializeObject(property.Value.ToObject<List<MarginOrderResult>>()));
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarginOrderById(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getOrderByIdAsync(this.margininstrument_id.Text, this.marginorder_id.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var fills = resResult.ToObject<MarginOrderFullInfo>();
                    Console.WriteLine(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarginFills(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getFillsAsync(long.Parse(this.marginorder_id.Text), this.margininstrument_id.Text, 1, null, 10);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<List<MarginFill>>();
                    Console.WriteLine(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnMargin_GetLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "";
                var resResult = await this.marginApi.getLeverage(instrument_id);
                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarginOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getOrdersAsync("BTC-USDT", "2", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var orders = resResult.ToObject<List<MarginOrderFullInfo>>();
                    Console.WriteLine(JsonConvert.SerializeObject(orders));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarginPendingOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.getPendingOrdersAsync("CAI-BTC", null, null, 10);

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnGetOrders_margin(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.marginApi.btnGetOrders("btc-usdt", "23458");

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnGetEttAccount(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getAccountsAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var accounts = resResult.ToObject<List<EttAccount>>();
                    Console.WriteLine(JsonConvert.SerializeObject(accounts));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetEttAccountByCurrency(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getAccountByCurrencyAsync("eos");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var accounts = resResult.ToObject<EttAccount>();
                    Console.WriteLine(JsonConvert.SerializeObject(accounts));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetEttLedger(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getLedgerAsync("eos", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ettLedger = resResult.ToObject<List<EttLedger>>();
                    Console.WriteLine(JsonConvert.SerializeObject(ettLedger));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeEttOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).EttOrder;
                var resResult = await this.ettApi.makeOrderAsync(order);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<EttOrderResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetEttOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.ettApi.getOrdersAsync("ok06ett", "1", "0", 1, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ettLedger = resResult.ToObject<List<OrderFullInfo>>();
                    Console.WriteLine(JsonConvert.SerializeObject(ettLedger));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetPositionByInstrumentSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                var resResult = await this.swapApi.getPositionByInstrumentAsync(instrumentId);
                Console.WriteLine(JsonConvert.SerializeObject(resResult));
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSwapAccounts(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getAccountsAsync();

                Console.WriteLine(JsonConvert.SerializeObject(resResult));
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOneAccountSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                var resResult = await this.swapApi.getAccountsByInstrumentAsync(instrumentId);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    Console.WriteLine(JsonConvert.SerializeObject(resResult.ToObject<swap.AccountResult>()));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetSwapSettings(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                var resResult = await this.swapApi.getSettingsByInstrumentAsync(instrumentId);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    Console.WriteLine(JsonConvert.SerializeObject(resResult.ToObject<swap.Leverage>()));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnSetInstrumentLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "BTC-USD-SWAP";
                int leverage = 100;
                string side = "1";
                var resResult = await this.swapApi.setLeverageByInstrumentAsync(instrumentId, leverage, side);
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    Console.WriteLine(JsonConvert.SerializeObject(resResult.ToObject<swap.Leverage>()));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetLedgerSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "ETC-USD-SWAP";
                int after = 1;
                int before = 0;
                int limit = 10;
                int type = 0;
                var resResult = await this.swapApi.getLedgersByInstrumentAsync(instrument_id, null, null, limit, null);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ledgers = resResult.ToObject<List<swap.Ledger>>();
                    Console.WriteLine(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeOrderSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).SwapOrderSingle;
                order.client_oid = null;
                var resResult = await this.swapApi.makeOrderAsync(order.instrument_id,
                    order.type,
                    order.order_type == null ? "0" : order.order_type,
                    order.price,
                    order.size,
                    order.client_oid,
                    order.match_price == "True" ? "1" : "0");
                JToken codeJToken;
                if (resResult.TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var orderResult = resResult.ToObject<swap.OrderResultSingle>();
                    Console.WriteLine(JsonConvert.SerializeObject(orderResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnMakeOrderBatchSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var order = ((MainViewModel)this.DataContext).OrderBatchSwap;
                var orderDetails = ((MainViewModel)this.DataContext).OrderDetailsSwap;
                orderDetails.ForEach(od =>
                {
                    od.match_price = od.match_price == "True" ? "1" : "0";
                });

                order.order_data = JsonConvert.SerializeObject(orderDetails);
                var resResult = await this.swapApi.makeOrdersBatchAsync(order);

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelOrderSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.cancelOrderAsync(this.instrument_id_cancel_swap.Text, this.order_id_swap.Text);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<swap.CancelOrderResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnCancelOrderBatchSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var orderIds = new List<string>();
                orderIds.Add(this.order_id_swap.Text);
                var resResult = await this.swapApi.cancelOrderBatchAsync(this.instrument_id_cancel_swap.Text, orderIds);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var cancelResult = resResult.ToObject<swap.CancelOrderBatchResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(cancelResult));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOrdersSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "ETC-USD-SWAP";
                int after = 10;
                int before = 0;
                int limit = 10;
                string state = "2";
                var resResult = await this.swapApi.getOrdersAsync(instrumentId, "2", null, null, limit);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ledgers = resResult.ToObject<swap.OrderListResult>();
                    Console.WriteLine(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnGetOrder_algoSpot(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.getOrder_algoAsync("BTC-USDT", 1, 2, null, null, null, null);

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnGetOrder_algoSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getOrder_algoAsync("ETC-USD-SWAP", 1, 2, null, null, null, null);

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnTrade_fee(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTrade_fee();

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnorder_algo_swap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.order_algo("BTC-USD-SWAP", "1", "1", "1", "432.11", "341.99", "1");

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btncancel_algos_swap(object sender, RoutedEventArgs e)
        {
            try
            {
                List<string> algo_ids = new List<string>();
                algo_ids.Add("1");
                string str_algoIds = JsonConvert.SerializeObject(algo_ids);
                var resResult = await this.swapApi.cancel_algos("ETC-USD-191206", str_algoIds, "1");

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOrderByIdSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrumentId = "ETC-USD-SWAP";
                string orderId = "376549434978166784";//orderid或clientid
                var resResult = await this.swapApi.getOrderByIdAsync(instrumentId, orderId);

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ledgers = resResult.ToObject<swap.Order>();
                    Console.WriteLine(JsonConvert.SerializeObject(ledgers));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetFillsSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getFillsAsync("ETC-USD-SWAP", "376549434978166784", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var fills = resResult.ToObject<List<swap.Fill>>();
                    Console.WriteLine(JsonConvert.SerializeObject(fills));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetInstrumentsSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getInstrumentsAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var instruments = resResult.ToObject<List<swap.Instrument>>();
                    Console.WriteLine(JsonConvert.SerializeObject(instruments));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetDepthSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getBookAsync("BTC-USD-SWAP", null, 20);
                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var depth = resResult.ToObject<swap.Depth>();
                    Console.WriteLine(JsonConvert.SerializeObject(depth));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetTickersSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTickersAsync();
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var ticker = resResult.ToObject<List<swap.Ticker>>();
                    Console.WriteLine(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetTickerByIdSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTickerByInstrumentId("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var ticker = resResult.ToObject<swap.Ticker>();
                    Console.WriteLine(JsonConvert.SerializeObject(ticker));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetTradesSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getTradesAsync("ETC-USD-SWAP", null, null, 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var trades = resResult.ToObject<List<swap.Trade>>();
                    Console.WriteLine(JsonConvert.SerializeObject(trades));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetKDataSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getCandlesDataAsync("BTC-USD-SWAP", DateTime.UtcNow.AddMinutes(-1), DateTime.UtcNow, 60);

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetIndexSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getIndexAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var index = resResult.ToObject<swap.Index>();
                    Console.WriteLine(JsonConvert.SerializeObject(index));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetRateSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getRateAsync();

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var rate = resResult.ToObject<swap.Rate>();
                    Console.WriteLine(JsonConvert.SerializeObject(rate));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetOpenInteestSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getOpenInterestAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var openInterest = resResult.ToObject<swap.OpenInterest>();
                    Console.WriteLine(JsonConvert.SerializeObject(openInterest));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnPriceLimitSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getPriceLimitAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var priceLimit = resResult.ToObject<swap.PriceLimit>();
                    Console.WriteLine(JsonConvert.SerializeObject(priceLimit));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnLiquidationSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getLiquidationAsync("BTC-USD-SWAP", "0", 1, null, 10);

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnHoldsSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getHoldsAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var holds = resResult.ToObject<swap.Hold>();
                    Console.WriteLine(JsonConvert.SerializeObject(holds));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetFundingTimeSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getFundingTimeAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var holds = resResult.ToObject<swap.FundingTime>();
                    Console.WriteLine(JsonConvert.SerializeObject(holds));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btnGetMarkPriceSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getMarkPriceAsync("BTC-USD-SWAP");

                JToken codeJToken;
                if (((JObject)resResult).TryGetValue("code", out codeJToken))
                {
                    var errorInfo = resResult.ToObject<ErrorResult>();
                    Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                }
                else
                {
                    var holds = resResult.ToObject<swap.MarkPrice>();
                    Console.WriteLine(JsonConvert.SerializeObject(holds));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnGetGenaralSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getGeneralFundingRateAsync("BTC-USD-SWAP");
                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnGetHistoricalSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.swapApi.getHistoricalFundingRateAsync("BTC-USD-SWAP", 10);
                if (resResult.Type == JTokenType.Object)
                {
                    JToken codeJToken;
                    if (((JObject)resResult).TryGetValue("code", out codeJToken))
                    {
                        var errorInfo = resResult.ToObject<ErrorResult>();
                        Console.WriteLine("错误代码：" + errorInfo.code + ",错误消息：" + errorInfo.message);
                    }
                }
                else
                {
                    var liquidations = resResult.ToObject<List<swap.HistoricalFundingRate>>();
                    Console.WriteLine(JsonConvert.SerializeObject(liquidations));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        // 创建Websocketor对象
        private WebSocketor websocketor = new WebSocketor();
        /// <summary>
        /// WebSocket消息推送侦听
        /// </summary>
        /// <param name="msg">WebSocket消息</param>
        private void handleWebsocketMessage(string msg)
        {
            try
            {
                this.Dispatcher.BeginInvoke(new Action(() =>
                {
                    //this.msgBox.AppendText(msg + Environment.NewLine);//换行标识
                    Console.WriteLine(msg);
                }));
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message + ex.StackTrace.ToString());
            }
        }

        private async void btnConnect(object sender, RoutedEventArgs e)
        {
            websocketor.WebSocketPush -= this.handleWebsocketMessage;
            websocketor.WebSocketPush += this.handleWebsocketMessage;
            await websocketor.ConnectAsync();
            Console.WriteLine("连接成功");
        }

        //private async void btnSubscribe(object sender, RoutedEventArgs e)
        //{
        //    await websocketor.Subscribe(new List<String>() { "futures/depth:BTC-USDT-191227" });
        //}
        private async void btnSubscribe(object sender, RoutedEventArgs e)
        {
            string channel = "", contract = "", currencyType = "", candle = "", contractID = "";
            Button btn = (Button)sender;
            string btn_type = btn.Content.ToString();
            switch (btn_type)
            {
                case "订阅(spot)":
                    channel = cleanTag(this.channel_spot.Text);
                    contract = cleanTag(this.contract_spot.Text);
                    currencyType = cleanTag(this.currency_spot.Text);
                    candle = cleanTag(this.candle_spot.Text);
                    contractID = this.contract_spot.Text;
                    break;
                case "订阅(futures)":
                    channel = cleanTag(this.channel_futures.Text);
                    contract = cleanTag(this.contract_futures.Text);
                    candle = cleanTag(this.candle_futures.Text);
                    contractID = this.contract_futures.Text;
                    break;
                case "订阅(swap)":
                    channel = cleanTag(this.channel_swap.Text);
                    contract = cleanTag(this.contract_swap.Text);
                    candle = cleanTag(this.candle_swap.Text);
                    contractID = this.contract_swap.Text;
                    break;
                case "订阅(index)":
                    channel = cleanTag(this.channel_index.Text);
                    candle = cleanTag(this.candle_index.Text);
                    contractID = this.contract_index.Text;
                    break;
                case "订阅(option)":
                    channel = cleanTag(this.channel_option.Text);
                    candle = cleanTag(this.candle_option.Text);
                    contractID = this.contract_option.Text;
                    break;
                default:
                    break;
            }
            if (!string.IsNullOrWhiteSpace(candle) && candle != "请选择时间粒度")
            {
                if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
                {
                    await websocketor.Subscribe(new List<string>() { $"{channel}{candle}s:{contract}" });
                }
                else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
                {
                    await websocketor.Subscribe(new List<string>() { $"{channel}{candle}s:{currencyType}" });
                }
                else if (!string.IsNullOrWhiteSpace(contractID))
                {
                    await websocketor.Subscribe(new List<string>() { $"{channel}{candle}s:{contractID}" });
                }
            }
            else if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
            {
                await websocketor.Subscribe(new List<string>() { $"{channel}:{contract}" });
            }
            else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
            {
                await websocketor.Subscribe(new List<string>() { $"{channel}:{currencyType}" });
            }
            else if (!string.IsNullOrWhiteSpace(contractID))
            {
                await websocketor.Subscribe(new List<string>() { $"{channel}:{contractID}" });
            }
            else
            {
                await websocketor.Subscribe(new List<string>() { $"{channel}" });
            }

        }
        private string cleanTag(string content)
        {
            int sign = content.IndexOf("(");
            return sign > 0 ? content.Substring(0, sign) : content;
        }

        //private async void btnUnSubscribe(object sender, RoutedEventArgs e)
        //{
        //    await websocketor.UnSubscribe(new List<String>() { "swap/depth:BTC-USD-SWAP", "swap/candle60s:BTC-USD-SWAP" });
        //}
        private async void btnUnSubscribe(object sender, RoutedEventArgs e)
        {
            string channel = "", contract = "", currencyType = "", candle = "", contractID = "";
            Button btn = (Button)sender;
            string btn_type = btn.Content.ToString();
            switch (btn_type)
            {
                case "取消订阅(spot)":
                    channel = cleanTag(this.channel_spot.Text);
                    contract = cleanTag(this.contract_spot.Text);
                    currencyType = cleanTag(this.currency_spot.Text);
                    candle = cleanTag(this.candle_spot.Text);
                    contractID = this.contract_spot.Text;
                    break;
                case "取消订阅(futures)":
                    channel = cleanTag(this.channel_futures.Text);
                    contract = cleanTag(this.contract_futures.Text);
                    candle = cleanTag(this.candle_futures.Text);
                    contractID = this.contract_futures.Text;
                    break;
                case "取消订阅(swap)":
                    channel = cleanTag(this.channel_swap.Text);
                    contract = cleanTag(this.contract_swap.Text);
                    candle = cleanTag(this.candle_swap.Text);
                    contractID = this.contract_swap.Text;
                    break;
                case "取消订阅(index)":
                    channel = cleanTag(this.channel_index.Text);
                    candle = cleanTag(this.candle_index.Text);
                    contractID = this.contract_index.Text;
                    break;
                case "取消订阅(option)":
                    channel = cleanTag(this.channel_option.Text);
                    candle = cleanTag(this.candle_option.Text);
                    contractID = cleanTag(this.contract_option.Text);
                    break;
                default:
                    break;
            }
            if (!string.IsNullOrWhiteSpace(candle) && candle != "请选择时间粒度")
            {
                if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
                {
                    await websocketor.UnSubscribe(new List<string>() { $"{channel}{candle}s:{contract}" });
                }
                else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
                {
                    await websocketor.UnSubscribe(new List<string>() { $"{channel}{candle}s:{currencyType}" });
                }
                else if (!string.IsNullOrWhiteSpace(contractID))
                {
                    await websocketor.UnSubscribe(new List<string>() { $"{channel}{candle}s:{contractID}" });
                }
            }
            else if (!string.IsNullOrWhiteSpace(contract) && contract != "请选择合约类型")
            {
                await websocketor.UnSubscribe(new List<string>() { $"{channel}:{contract}" });
            }
            else if (!string.IsNullOrWhiteSpace(currencyType) && currencyType != "请选择币种类型")
            {
                await websocketor.UnSubscribe(new List<string>() { $"{channel}:{currencyType}" });
            }
            else if (!string.IsNullOrWhiteSpace(contractID))
            {
                await websocketor.UnSubscribe(new List<string>() { $"{channel}:{contractID}" });
            }
            else
            {
                await websocketor.UnSubscribe(new List<string>() { $"{channel}" });
            }
        }

        private async void btnLogin(object sender, RoutedEventArgs e)
        {
            await websocketor.LoginAsync(this.apiKey, this.secret, this.passPhrase);
        }

        private async void btnSubscribeLogin(object sender, RoutedEventArgs e)
        {
            await websocketor.Subscribe(new List<String>() { "futures/account:BTC" });
        }

        private void btnDispose(object sender, RoutedEventArgs e)
        {
            websocketor.Dispose();
        }

        private async void btnProgress(object sender, RoutedEventArgs e)
        {
            // 取消事件侦听
            websocketor.WebSocketPush -= this.handleWebsocketMessage;
            // 添加事件侦听
            websocketor.WebSocketPush += this.handleWebsocketMessage;
            try
            {
                // 建立WebSocket连接
                await websocketor.ConnectAsync();
                // 订阅无需登录的Channel
                await websocketor.Subscribe(new List<String>() { "swap/ticker:BTC-USD-SWAP", "swap/candle60s:BTC-USD-SWAP" });
                // 登录
                await websocketor.LoginAsync(this.apiKey, this.secret, this.passPhrase);
                // 等待登录
                await Task.Delay(500);
                // 订阅需要登录的Channel
                await websocketor.Subscribe(new List<String>() { "futures/account:BTC" });
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private void TextBox_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private async void btnSetMargin_mode(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.setMargin_modeAsync("btc-usd", "crossed");

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnclose_position(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.close_position("BTC-USD-191129", "short");

                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void cancel_all(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.cancel_all("BTC-USD-191129", "short");
                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void order_algo(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.order_algo("BTC-USD-191129", "1", 2, null, null, null, null);
                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void mark_price(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.mark_price("BTC-USD-191129");


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnOrder_algoSpot(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.spotApi.btnOrder_algoSpot("BTC-USDT", "1", "1", "0.05", "sell", "8000", "7500");


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private async void btncancel_batch_algosSpot(object sender, RoutedEventArgs e)
        {
            try
            {
                List<string> algo_ids = new List<string>();
                algo_ids.Add("1600593327162368");
                string algoids = JsonConvert.SerializeObject(algo_ids);
                var resResult = await this.spotApi.cancel_batch_algosSpot("BTC-USDT", algoids, "1");


                Console.WriteLine(resResult);

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void order_algo_futures(object sender, RoutedEventArgs e)
        {
            try
            {
                var resResult = await this.futureApi.order_algo("ETC-USD-191206", "1", "1", "1", "432.11", "341.99", "1");

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void cancel_algos_futures(object sender, RoutedEventArgs e)
        {
            try
            {
                List<string> algo_ids = new List<string>();
                algo_ids.Add("1");
                string str_algoIds = JsonConvert.SerializeObject(algo_ids);
                var resResult = await this.futureApi.cancel_algos("ETC-USD-191206", str_algoIds, "1");

                Console.WriteLine(resResult);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private void TabControl_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }
        private async void btngetOptionAccountsAsync(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }

                var resStr = await this.optionApi.getOptionAccountsAsync(underlying);
                Console.WriteLine(resStr);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnAmend_batch_orders(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                var order_ids = this.option_order_ids.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                List<string> orderIds = order_ids.Split(',').ToList();
                List<object> data = new List<object>();
                orderIds.ForEach(order =>
                {
                    var detail = order.Split('|').ToArray();
                    if (detail.Length == 2)
                    {
                        data.Add(new { order_id = detail[0], new_size = detail[1] });
                    }
                    else if (detail.Length == 3)
                    {
                        data.Add(new { client_oid = detail[0], request_id = detail[1], new_size = detail[2] });
                    }
                });
                //amend_data
                string bodystr = $"{{\"amend_data\":{ JsonConvert.SerializeObject(data)}}}";
                var resStr = await this.optionApi.Amend_batch_orders(underlying, bodystr);
                Console.WriteLine(resStr);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnAmend_order(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                string order_id = this.option_order_id.Text;
                string client_oid = this.option_client_oid.Text;
                string new_size = this.option_new_size.Text;
                string new_price = this.option_price.Text;
                string request_id = this.option_request_id.Text;
                Dictionary<string, string> data = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(order_id))
                {
                    data.Add("order_id", order_id);
                }
                if (!string.IsNullOrWhiteSpace(client_oid))
                {
                    data.Add("client_oid", client_oid);
                }
                if (!string.IsNullOrWhiteSpace(new_size))
                {
                    data.Add("new_size", new_size);
                }
                if (!string.IsNullOrWhiteSpace(new_price))
                {
                    data.Add("new_price", new_price);
                }
                if (!string.IsNullOrWhiteSpace(request_id))
                {
                    data.Add("request_id", request_id);
                }
                string bodystr = JsonConvert.SerializeObject(data);
                var resStr = await this.optionApi.Amend_order(underlying, bodystr);
                Console.WriteLine(resStr);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionBook(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = this.option_instrument_id.Text;
                string size = this.option_size.Text;
                var res = await this.optionApi.getOptionBook(instrument_id, size);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnCancel_Batch_Orders(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                string order_ids = this.option_order_ids.Text;
                var data = order_ids.Split(',');
                string bodystr = $"{{\"order_ids\":[{JsonConvert.SerializeObject(data)}]}}".Replace("[[", "[").Replace("]]", "]");
                var res = await this.optionApi.Cancel_Batch_Orders(underlying, bodystr);
                if (res.Contains("order_id parameter value error"))
                {
                    bodystr = bodystr.Replace("order_ids", "client_oids");
                    res = await this.optionApi.Cancel_Batch_Orders(underlying, bodystr);
                }
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnCancel_Order(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = this.option_instrument_id.Text;
                string order_id = this.option_order_id.Text;
                if (string.IsNullOrWhiteSpace(order_id))
                {
                    string client_oid = this.option_client_oid.Text;
                    var response = await this.optionApi.Cancel_Order(instrument_id, client_oid);
                    Console.WriteLine(response);
                    return;
                }
                var res = await this.optionApi.Cancel_Order(instrument_id, order_id);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionDeal_data(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = this.option_instrument_id.Text;
                if (string.IsNullOrWhiteSpace(instrument_id))
                {
                    Console.WriteLine("请输入instrument_id");
                    return;
                }
                string after = string.IsNullOrWhiteSpace(this.option_after.Text) ? "" : this.option_after.Text;
                string before = string.IsNullOrWhiteSpace(this.option_before.Text) ? "" : this.option_before.Text;
                string limit = string.IsNullOrWhiteSpace(this.option_limit.Text) ? "" : this.option_limit.Text;
                var res = await this.optionApi.getOptionDeal_data(instrument_id, after, before, limit);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionFills(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string instrument_id = string.IsNullOrWhiteSpace(this.option_instrument_id.Text) ? "" : this.option_instrument_id.Text;
                string order_id = string.IsNullOrWhiteSpace(this.option_order_id.Text) ? "" : this.option_order_id.Text;
                string after = string.IsNullOrWhiteSpace(this.option_after.Text) ? "" : this.option_after.Text;
                string before = string.IsNullOrWhiteSpace(this.option_before.Text) ? "" : this.option_before.Text;
                string limit = string.IsNullOrWhiteSpace(this.option_limit.Text) ? "" : this.option_limit.Text;
                var res = await this.optionApi.getOptionFills(underlying, order_id, instrument_id, after, before, limit);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionInstrument_summary_byinis(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string instrument_id = this.option_instrument_id.Text;
                var res = await this.optionApi.getOptionInstrument_summary_byinis(underlying, instrument_id);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionInstrument_summary(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string delivery = this.option_delivery.Text;
                var res = await this.optionApi.getOptionInstrument_summary(underlying, delivery);
                Console.WriteLine(res);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionInstrument(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string instrument_id = this.option_instrument_id.Text;
                string delivery = this.option_delivery.Text;
                var res = await this.optionApi.getOptionInstrument(underlying, delivery, instrument_id);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionLedgert(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string after = string.IsNullOrWhiteSpace(this.option_after.Text) ? "" : this.option_after.Text;
                string before = string.IsNullOrWhiteSpace(this.option_before.Text) ? "" : this.option_before.Text;
                string limit = string.IsNullOrWhiteSpace(this.option_limit.Text) ? "" : this.option_limit.Text;
                var res = await this.optionApi.getOptionLedgert(underlying, after, before, limit);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOptionLine(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = this.option_instrument_id.Text;
                if (string.IsNullOrWhiteSpace(instrument_id))
                {
                    Console.WriteLine("请输入instrument_id");
                    return;
                }
                string start = this.option_start.Text;
                string end = this.option_end.Text;
                string granularity = this.option_granularity.Text;
                var res = await this.optionApi.getOptionLine(instrument_id, start, end, granularity);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOrder_information(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string order_id = string.IsNullOrWhiteSpace(this.option_order_id.Text) ? this.option_client_oid.Text : this.option_order_id.Text;
                var res = await this.optionApi.getOrder_information(underlying, order_id);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOrder_list(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                string instrument_id = this.option_instrument_id.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string state = this.option_state.Text;
                string after = this.option_after.Text;
                string before = this.option_before.Text;
                string limit = this.option_limit.Text;
                var res = await this.optionApi.getOrder_list(underlying, state, instrument_id, after, before, limit);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOrder(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = this.option_instrument_id.Text;
                string side = this.option_side.Text;
                string price = this.option_price.Text;
                string size = this.option_size.Text;
                string client_oid = this.option_client_oid.Text;
                string order_type = this.option_order_type.Text;
                string match_price = this.option_match_price.Text;
                if (string.IsNullOrWhiteSpace(instrument_id))
                {
                    Console.WriteLine("请输入instrument_id");
                    return;
                }
                if (string.IsNullOrWhiteSpace(side))
                {
                    Console.WriteLine("请输入side");
                    return;
                }
                if (string.IsNullOrWhiteSpace(price))
                {
                    Console.WriteLine("请输入price");
                    return;
                }
                if (string.IsNullOrWhiteSpace(size))
                {
                    Console.WriteLine("请输入size");
                    return;
                }
                Dictionary<string, string> data = new Dictionary<string, string>();
                if (!string.IsNullOrWhiteSpace(client_oid))
                {
                    data.Add("client_oid", client_oid);
                }
                data.Add("instrument_id", instrument_id);
                data.Add("side", side);

                if (!string.IsNullOrWhiteSpace(order_type))
                {
                    data.Add("order_type", order_type);
                }
                data.Add("price", price);
                data.Add("size", size);
                if (!string.IsNullOrWhiteSpace(match_price))
                {
                    data.Add("match_price", match_price);
                }
                string bodyStr = JsonConvert.SerializeObject(data);
                Console.WriteLine(bodyStr);

                var res = await this.optionApi.getOrder(bodyStr);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetOrders(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                string order_data = this.option_order_data.Text;
                List<object> order_data_list = new List<object>();
                List<string> list_data = order_data.Split(',').ToList();
                list_data.ForEach(order =>
                {
                    string[] detail = order.Split('|');
                    if (detail.Length != 6)
                    {
                        Console.WriteLine("您输入的参数格式有误，请重新输入！");
                        return;
                    }
                    var data = new { instrument_id = detail[0], size = detail[1], price = detail[2], side = detail[3], order_type = detail[4], match_price = detail[5] };
                    order_data_list.Add(data);
                });
                string bodyStr = $"{{\"underlying\": \"{underlying}\",\"order_data\": {JsonConvert.SerializeObject(order_data_list)}}}";
                var res = await this.optionApi.getOrders(bodyStr);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetPosition(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                string instrument_id = this.option_instrument_id.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                var res = await this.optionApi.getPosition(underlying, instrument_id);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetTicker(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = this.option_instrument_id.Text;
                if (string.IsNullOrWhiteSpace(instrument_id))
                {
                    Console.WriteLine("请输入instrument_id");
                    return;
                }
                var res = await this.optionApi.getTicker(instrument_id);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetTrade_fee(object sender, RoutedEventArgs e)
        {
            try
            {
                var res = await this.optionApi.getTrade_fee();
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btngetUnderlying(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = this.option_underlying.Text;
                if (string.IsNullOrWhiteSpace(underlying))
                {
                    Console.WriteLine("请输入underlying");
                    return;
                }
                var res = await this.optionApi.getUnderlying(underlying);
                Console.WriteLine(res);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }
        private async void btnSpot_GetTrade_fee(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.spotApi.getTrade_fee();
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }

        }
        private async void btnFutures_GetTrade_fee(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.futureApi.getTrade_fee();
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }

        }
        private async void btnAccount_GetSub_AccountAsync(object sender, RoutedEventArgs e)
        {
            try
            {
                string sub_account = "Test";
                string content = await this.accountApi.getsub_accountAsync(sub_account);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btngetAsset_ValuationAsync(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.accountApi.getAsset_ValuationAsync();
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btnMargin_SetLeverage(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "";
                var data = new { leverage = "10" };
                string bodystr = JsonConvert.SerializeObject(data);
                string content = await this.marginApi.SetLeverage(instrument_id, bodystr);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btnMargin_Mark_price(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "BTC-USDT";
                string content = await this.marginApi.mark_price(instrument_id);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        /// <summary>
        /// 增加/减少保证金
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private async void btn_margin(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "";
                string direction = "";
                string type = "";
                string amount = "";
                string content = await this.futureApi.margin(instrument_id, direction, type, amount);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        // <summary>
        /// 设置逐仓自动增加保证金
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private async void btn_auto_margin(object sender, RoutedEventArgs e)
        {
            try
            {
                string underlying = "BTC-USDT";
                string type = "1";
                string content = await this.futureApi.auto_margin(underlying, type);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btn_close_position(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "";
                string direction = "";
                string content = await this.swapApi.close_position(instrument_id, direction);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btn_cancel_all(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "";
                string direction = "";
                string content = await this.swapApi.cancel_all(instrument_id, direction);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }

        private async void btnGetStatus(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.systemApi.status();
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btnGetConstituents(object sender, RoutedEventArgs e)
        {
            try
            {
                string instrument_id = "BTC-USDT";
                string content = await this.indexApi.constituents(instrument_id);
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcGetLong_short_radio(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.informationApi.long_short_radio("BTC", "300");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcGetVolume(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.informationApi.volume("BTC", "300");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcGetTaker(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.informationApi.taker("BTC", "300");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcGetSentiment(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.informationApi.sentiment("BTC");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcGetMargin(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.informationApi.margin("BTC");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcAmend_orderFutures(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.futureApi.amend_order( "MNBTC-USD-200731", "1",order_id: "5321141332075520", new_size:"2");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcAmend_batch_orders(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.futureApi.amend_batch_orders("BTC-USDT-200925", "0", order_id: "1111111");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcAmend_orderSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.swapApi.amend_order("XRP-USDT-SWAP", "1", order_id: "555545904511893504", new_size: "1");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcAmend_batch_ordersSwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.swapApi.amend_batch_orders("BTC-USDT-200925", "0", order_id: "1111111");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcSettleHistoryFutures(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.futureApi.getSettlementHistory("BTC-USDT-200925");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcCandleHistoryFutures(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.futureApi.getHistory_candles("BTC-USDT-200925");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
        private async void btcCandleHistorySwap(object sender, RoutedEventArgs e)
        {
            try
            {
                string content = await this.swapApi.getHistory_candles("BTC-USDT-SWAP");
                Console.WriteLine(content);
            }
            catch (Exception ex)
            {
                Console.WriteLine("错误信息" + ex.Message + "堆栈信息" + ex.StackTrace.ToString());
            }
        }
    }
}
