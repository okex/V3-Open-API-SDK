package com.okcoin.commons.okex.open.api.test.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.*;
import com.okcoin.commons.okex.open.api.bean.futures.result.CancelFuturesOrdeResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.FuturesOrderResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.Holds;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;
import com.okcoin.commons.okex.open.api.enums.FuturesTransactionTypeEnum;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesTradeAPIServiceImpl;
import com.okcoin.commons.okex.open.api.utils.OrderIdUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Futures trade api tests
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/13 18:21
 */
public class FuturesTradeAPITests extends FuturesAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(FuturesTradeAPITests.class);

    private FuturesTradeAPIService tradeAPIService;


    @Before
    public void before() {
        config = config();
        tradeAPIService = new FuturesTradeAPIServiceImpl(config);
    }

    /**
     * 所有合约持仓信息
     * GET /api/futures/v3/position
     * 获取账户所有合约的持仓信息。请求此接口，会在数据库遍历所有币对下的持仓数据，
     * 有大量的性能消耗,请求频率较低。建议用户传合约ID获取持仓信息。
     * 限速规则：5次/2s（根据userid限速）
     */
    @Test
    public void testGetPositions() {
        JSONObject positions = tradeAPIService.getPositions();
        toResultString(LOG, "Positions", positions);
    }

    /**
     * 单个合约持仓信息
     * GET /api/futures/v3/<instrument_id>/position
     * 获取某个合约的持仓信息
     * 限速规则：20次/2s（根据underlying，分别限速
     */
    @Test
    public void testGetInstrumentPosition() {
        Long time1 = new Date().getTime();
        JSONObject positions1 = tradeAPIService.getInstrumentPosition("XRP-USDT-200327");
        toResultString(LOG, "instrument-Position", positions1);
        Long time2 = new Date().getTime();
        System.out.println(time2-time1);
        }
    /**
     * 所有币种合约账户信息
     * GET /api/futures/v3/accounts
     * 获取合约账户所有币种的账户信息。请求此接口，会在数据库遍历所有币对下的账户数据，
     * 有大量的性能消耗,请求频率较低。建议用户传币种获取账户信息信息。
     * 限速规则：1次/10s（根据userid限速）
     */
    @Test
    public void testGetAccounts() {
        JSONObject accounts = tradeAPIService.getAccounts();
        toResultString(LOG, "Accounts", accounts);
    }

    /**
     * 单个币种合约账户信息
     * GET /api/futures/v3/accounts/{currency}
     * 获取单个币种的合约账户信息
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetAccountsByCurrency() {
        JSONObject accountsByCurrency = tradeAPIService.getAccountsByCurrency("BSV-USDT");
        toResultString(LOG, "Accounts-Currency", accountsByCurrency);
    }

    /**
     * 获取合约币种杠杆倍数
     * GET /api/futures/v3/accounts/<currency>/leverage
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetLeverage() {
        JSONObject jsonObject = tradeAPIService.getInstrumentLeverRate("BTC-USD");
        toResultString(LOG, "Get-Leverage", jsonObject);
    }
    /**
     * 设定逐仓合约币种杠杆倍数
     * POST /api/futures/v3/accounts/BTC-USD/leverage{"instrument_id":"BTC-USD-180213","direction":"long","leverage":"10"}（逐仓示例）
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testChangeLeverageOnFixed() {
        //JSONObject jsonObject = tradeAPIService.changeLeverageOnFixed(currency, instrument_id, direction, leverage);
        JSONObject jsonObject = tradeAPIService.changeLeverageOnFixed("BTC-USD", "BTC-USD-200327", "long", "15");
        toResultString(LOG, "Change-fixed-Leverage", jsonObject);
    }

    /**
     * 设定全仓合约币种杠杆倍数
     * POST /api/futures/v3/accounts/BTC-USD/leverage{"leverage":"10"}（全仓示例）
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testChangeLeverageOnCross() {
        JSONObject jsonObject = tradeAPIService.changeLeverageOnCross("BTC-USD", "15");
        toResultString(LOG, "Change-cross-Leverage", jsonObject);
    }

    /**
     * 账单流水查询
     * GET /api/futures/v3/accounts/eos-usd/ledger?after=2510946217009854&limit=3
     * 列出帐户资产流水。帐户资产流水是指导致帐户余额增加或减少的行为
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetAccountsLedgerByCurrency() {
        JSONArray ledger = tradeAPIService.getAccountsLedgerByCurrency("XRP-USDT","","","","13");
        toResultString(LOG, "Ledger", ledger);
    }

    /**
     * 获取合约挂单冻结数量
     * GET /api/futures/v3/accounts/BTC-USD-181228/holds
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetAccountsHoldsByinstrument_id() {
        JSONObject ledger = tradeAPIService.getAccountsHoldsByInstrumentId("XRP-USDT-200214");
        toResultString(LOG, "Ledger", ledger);
    }

    /**
     * 下单
     * OKEx合约交易提供了限价单下单模式。只有当您的账户有足够的资金才能下单。
     * 一旦下单，您的账户资金将在订单生命周期内被冻结。被冻结的资金以及数量取决于订单指定的类型和参数。
     * POST /api/futures/v3/order
     * 限速规则：40次/2s （根据underlying，分别限速）
     */
    @Test
    public void testOrder() {
        Order order = new Order();
        order.setinstrument_id("XRP-USDT-200327");
        order.setClient_oid("ctt0206test02");
        order.setType("2");
        order.setPrice("0.4");
        order.setSize("1");
        order.setMatch_price("0");
        order.setOrder_type("0");
        OrderResult result = tradeAPIService.order(order);
        toResultString(LOG, "New-Order", result);
    }

    /**
     * 批量下单
     * 批量进行合约下单操作。每个合约可批量下10个单。
     * POST /api/futures/v3/orders
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testOrders() {
        Orders orders = new Orders();
        //设置instrument_id
        orders.setInstrument_id("XRP-USDT-200327");
        List<OrdersItem> orders_data = new ArrayList<OrdersItem>();
        OrdersItem item1 = new OrdersItem();
        item1.setClient_oid("ctt0327orders01");
        item1.setOrder_type("0");
        item1.setType("1");
        item1.setPrice("0.1800");
        item1.setSize("1");
        item1.setMatch_price("0");

        OrdersItem item2 = new OrdersItem();
        item2.setClient_oid("ctt0327orders02");
        item1.setOrder_type("0");
        item2.setType("1");
        item2.setPrice("0.1818");
        item2.setSize("1");
        item2.setMatch_price("0");

        orders_data.add(item1);
        orders_data.add(item2);
        orders.setOrders_data(orders_data);

        JSONObject result = tradeAPIService.orders(orders);
        toResultString(LOG, "Batch-Orders", result);
    }

    /**
     * 获取订单列表
     * GET /api/futures/v3/orders/<instrument_id>
     * 列出您当前所有的订单信息。本接口能查询最近7天的数据。
     * 这个请求支持分页，并且按委托时间倒序排序和存储，最新的排在最前面。
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetOrders() {
        JSONObject result = tradeAPIService.getOrders("XRP-USDT-200214", "0", "", "", "");
        toResultString(LOG, "Get-Orders", result);
    }

    /**
     * 获取订单信息
     * 通过订单ID获取单个订单信息。已撤销的未成交单只保留2个小时。
     * GET /api/futures/v3/orders/<instrument_id>/<order_id>或
     * GET /api/futures/v3/orders/<instrument_id>/<client_oid>
     * 限速规则：40次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetOrderByOrderId() {
    //3932093177765889
        JSONObject result = tradeAPIService.getOrderByOrderId("XRP-USDT-200327", "4374329825008641");
        toResultString(LOG, "Get-Order", result);
    }

    @Test
    public void testGetOrderByClientOid() {
        //3932093177765889
        JSONObject result = tradeAPIService.getOrderByClientOid("XRP-USDT-200327", "ctt0104test01");
        toResultString(LOG, "Get-Order", result);
    }

    /**
     * 撤销指定订单
     * 撤销之前下的未完成订单。
     * POST /api/futures/v3/cancel_order/<instrument_id>/<order_id>或
     * POST /api/futures/v3/cancel_order/<instrument_id>/<client_oid>
     * 限速规则：40次/2s （根据underlying，分别限速）
     * 4052548984443905  4052548984443907  ctt1217orders02
     */
    @Test
    public void testCancelOrderByOrderId() {

        JSONObject result = tradeAPIService.cancelOrderByOrderId("XRP-USDT-200214", "4374319621159937");
        toResultString(LOG, "Cancel-Instrument-Order", result);
    }
    @Test
    public void testCancelOrderByClientOid() {
        JSONObject result = tradeAPIService.cancelOrderByClientOid("XRP-USDT-200327","ctt0206test02");
        toResultString(LOG, "Cancel-Instrument-Order", result);
    }

    /**
     * 批量撤销订单
     * POST /api/futures/v3/cancel_batch_orders/<instrument_id>
     * 根据指定的order_id撤销某个合约的未完成订单，每次最多可撤10个单。
     * 限速规则：20 次/2s （根据underlying，分别限速）
     */
    @Test
    public void testCancelOrdersByOrderId() {
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<String>();
        //通过订单号撤销订单
        list.add("4238577093921793");
        list.add("4238577093987329");
        cancelOrders.setOrder_ids(list);
        JSONObject result = tradeAPIService.cancelOrders("XRP-USDT-200327", cancelOrders);
        toResultString(LOG, "Cancel-Instrument-Orders", result);
    }
    @Test
    public void testCancelOrdersByClientOid() {
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<String>();
        //通过client_oid撤销订单
        list.add("");
        list.add("");
        cancelOrders.setClient_oids(list);
        JSONObject result = tradeAPIService.cancelOrders("XRP-USDT-191227", cancelOrders);
        toResultString(LOG, "Cancel-Instrument-Orders", result);
    }

    /**
     * 获取成交明细
     * GET /api/futures/v3/fills
     * 获取最近的成交明细列表，本接口能查询最近7天的数据。
     * 限速规则：20 次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetFills() {

        JSONArray result = tradeAPIService.getFills("XRP-USDT-200327", "4055058885740547", "", "", "");
        toResultString(LOG, "Get-Fills", result);
    }


    /**
     * 设置合约币种账户模式
     * POST /api/futures/v3/accounts/margin_mode
     * 设置合约币种账户模式，注意当前仓位有持仓或者挂单禁止切换账户模式
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testChangeMarginMode() {
        ChangeMarginMode changeMarginMode = new ChangeMarginMode();
        changeMarginMode.setUnderlying("BTC-USD");
        changeMarginMode.setMargin_mode("fixed");
        JSONObject jsonObject = tradeAPIService.changeMarginMode(changeMarginMode);
        toResultString(LOG, "MarginMode", jsonObject);
    }

    /**
     * 市价全平
     * 市价全平接口，其中BTC合约持仓小于或等于999张时才能调用，
     * 否则报错；类似的，其他币种合约的持仓应该小于或等于9999张。
     * POST /api/futures/v3/close_position
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testClosePositions() {
        ClosePositions closePositions = new ClosePositions();
        closePositions.setInstrument_id("BTC-USD-200327");
        closePositions.setDirection("long");
        JSONObject jsonObject = tradeAPIService.closePositions(closePositions);
        toResultString(LOG, "closePositions", jsonObject);
    }

    /**
     * 撤销所有平仓挂单
     * 此接口，仅支持撤销平仓的所有挂单。不包括开仓的挂单。
     * POST /api/futures/v3/cancel_all
     * 限速规则：5次/2s （根据underlying，分别限速）
     */
    @Test
    public void testcancelAll() {
        CancelAll  cancelAll = new CancelAll();
        cancelAll.setInstrument_id("XRP-USDT-200327");
        cancelAll.setDirection("long");
        JSONObject jsonObject = tradeAPIService.cancelAll(cancelAll);
        toResultString(LOG, "cancelAll", jsonObject);
    }

    /**
     * 获取合约挂单冻结数量
     * GET/api/futures/v3/accounts/<instrument_id>/hold
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testGetHolds() {
        Holds holds = tradeAPIService.getHolds("BTC-USD-200327");
        toResultString(LOG, "Instrument-Holds", holds);
    }

    /**
     * 策略委托下单
     * 提供止盈止损、跟踪委托、冰山委托和时间加权委托策略
     * POST /api/futures/v3/order_algo
     * 限速规则：40次/2s （根据underlying，分别限速）
     */
    @Test
    public void testFuturesOrder(){
        FuturesOrderParam futuresOrderParam=new FuturesOrderParam();
        //公共参数
        futuresOrderParam.setInstrument_id("XRP-USDT-200327");
        futuresOrderParam.setType("3");
        futuresOrderParam.setOrder_type("1");
        futuresOrderParam.setSize("1");

        //止盈止损
        futuresOrderParam.setTrigger_price("0.285");
        futuresOrderParam.setAlgo_price("0.283");

        //跟踪委托
       /* futuresOrderParam.setCallback_rate("");
        futuresOrderParam.setTrigger_price("");*/

        //冰山委托
        /*futuresOrderParam.setAlgo_variance("");
        futuresOrderParam.setAvg_amount("");
        futuresOrderParam.setPrice_limit("");*/

        //时间加权
        /*futuresOrderParam.setSweep_range("");
        futuresOrderParam.setSweep_ratio("");
        futuresOrderParam.setSingle_limit("");
        futuresOrderParam.setPrice_limit("");
        futuresOrderParam.setTime_interval("");*/

        FuturesOrderResult futuresOrderResult=tradeAPIService.futuresOrder(futuresOrderParam);
        toResultString(LOG, "futuresOrderResult", futuresOrderResult);
    }

    /**
     * 策略委托撤单
     * 根据指定的algo_id撤销某个合约的未完成订单，每次最多可撤6（冰山/时间）/10（计划/跟踪）个。
     * POST /api/futures/v3/cancel_algos
     * 限速规则：20 次/2s （根据underlying，分别限速）
     */
    @Test
    public void testCancelFuturesOrder(){
        CancelFuturesOrder cancelFuturesOrder=new CancelFuturesOrder();
        cancelFuturesOrder.setInstrument_id("XRP-USDT-200327");
        cancelFuturesOrder.setOrder_type("1");
        List<String> algo_ids=new ArrayList<String>();
        algo_ids.add("1923724");
        cancelFuturesOrder.setAlgo_ids(algo_ids);

        CancelFuturesOrdeResult cancelFuturesOrdeResult=tradeAPIService.cancelFuturesOrder(cancelFuturesOrder);
        toResultString(LOG, "cancelFuturesOrdeResult", cancelFuturesOrdeResult);
    }

    /**
     * 获取委托单列表
     * 列出您当前所有的订单信息。
     * GET /api/futures/v3/order_algo/<instrument_id>
     * 限速规则：20次/2s （根据underlying，分别限速）
     */
    @Test
    public void testFindFuturesOrder(){

        String result = tradeAPIService.findFuturesOrder("XRP-USD-200327", "1", "1", "", "","","20");
        toResultString(LOG, "Get-FuturesOrders", result);

    }
    //当前账户交易手续等级的费率
    @Test
    public void testGetTradeFee(){
        JSONObject result = tradeAPIService.getTradeFee();
        toResultString(LOG, "result", result);

    }



}
