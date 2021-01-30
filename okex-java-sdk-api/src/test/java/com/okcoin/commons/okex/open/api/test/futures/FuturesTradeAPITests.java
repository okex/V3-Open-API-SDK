package com.okcoin.commons.okex.open.api.test.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.*;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesTradeAPIServiceImpl;
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
     */
    @Test
    public void testGetPositions() {
        JSONObject positions = tradeAPIService.getPositions();
        toResultString(LOG, "Positions", positions);
    }

    /**
     * 单个合约持仓信息
     * GET /api/futures/v3/<instrument_id>/position
     */
    @Test
    public void testGetPositionByInstrumentId() {
        JSONObject positions = tradeAPIService.getPositionByInstrumentId("BTC-USDT-210326");
        toResultString(LOG, "instrument-Position", positions);

    }

    /**
     * 所有币种合约账户信息
     * GET /api/futures/v3/accounts
     */
    @Test
    public void testGetAccounts() {
        JSONObject accounts = tradeAPIService.getAccounts();
        toResultString(LOG, "Accounts", accounts);
    }

    /**
     * 单个币种合约账户信息
     * GET /api/futures/v3/accounts/{underlying}
     */
    @Test
    public void testGetAccountsByUnderlying() {
        JSONObject accountsByCurrency = tradeAPIService.getAccountsByUnderlying("BTC-USDT");
        toResultString(LOG, "Accounts-Currency", accountsByCurrency);
    }

    /**
     * 获取合约币种杠杆倍数
     * GET /api/futures/v3/accounts/<currency>/leverage
     */
    @Test
    public void testGetLeverage() {
        JSONObject jsonObject = tradeAPIService.getLeverage("BTC-USD");
        toResultString(LOG, "Get-Leverage", jsonObject);
    }

    /**
     * 设定逐仓合约币种杠杆倍数
     * POST /api/futures/v3/accounts/BTC-USD/leverage{"instrument_id":"BTC-USD-180213","direction":"long","leverage":"10"}（逐仓示例)
     */
    @Test
    public void testSetLeverageOnFixed() {
        JSONObject jsonObject = tradeAPIService.setLeverageOnFixed("BTC-USDT", "BTC-USDT-201225", "long", "15.5");
        toResultString(LOG, "Change-fixed-Leverage", jsonObject);
    }

    /**
     * 设定全仓合约币种杠杆倍数
     * POST /api/futures/v3/accounts/BTC-USD/leverage{"leverage":"10"}（全仓示例）
     */
    @Test
    public void testSetLeverageOnCross() {
        JSONObject jsonObject = tradeAPIService.setLeverageOnCross("BTC-USDT", "5");
        toResultString(LOG, "Change-cross-Leverage", jsonObject);
    }

    /**
     * 账单流水查询
     * GET /api/futures/v3/accounts/eos-usd/ledger?after=2510946217009854&limit=3
     */
    @Test
    public void testGetAccountsLedgerByUnderlying() {
        JSONArray ledger = tradeAPIService.getAccountsLedgerByUnderlying("BTC-USDT",null,null,null,null);
        toResultString(LOG, "Ledger", ledger);
    }

    /**
     * 下单
     * POST /api/futures/v3/order
     */
    @Test
    public void testOrder() {
        Order order = new Order();
        order.setClient_oid("test1024funtures01");
        order.setinstrument_id("XRP-USDT-210326");
        order.setType("2");
        order.setOrder_type("0");
        order.setPrice("0.27");
        order.setSize("1");
        order.setMatch_price("0");

        OrderResult result = tradeAPIService.order(order);
        toResultString(LOG, "New-Order", result);
    }

    /**
     * 批量下单
     * POST /api/futures/v3/orders
     */
    @Test
    public void testOrders() {

        Orders orders = new Orders();
        //设置instrument_id
        orders.setInstrument_id("DOT-USDT-210326");
        List<OrdersItem> orders_data = new ArrayList<OrdersItem>();
        OrdersItem item1 = new OrdersItem();
        item1.setOrder_type("0");
        item1.setPrice("4.8");
        item1.setSize("1");
        item1.setType("2");
        item1.setMatch_price("0");

        item1.setClient_oid("1025futures01");


        OrdersItem item2 = new OrdersItem();
        item2.setOrder_type("0");
        item2.setPrice("4.9");
        item2.setSize("1");
        item2.setType("2");
        item2.setMatch_price("0");

        item2.setClient_oid("1025futures02");

        orders_data.add(item1);
        orders_data.add(item2);
        orders.setOrders_data(orders_data);

        JSONObject result = tradeAPIService.orders(orders);
        toResultString(LOG, "Batch-Orders", result);
    }

    /**
     * 撤销指定订单(通过order_id)
     * POST /api/futures/v3/cancel_order/<instrument_id>/<order_id>
     */
    @Test
    public void testCancelOrderByOrderId() {

        JSONObject result = tradeAPIService.cancelOrderByOrderId("DOT-USDT-210326", "5820406210945026");
        toResultString(LOG, "Cancel-Instrument-Order", result);
    }

    /**
     * 撤销指定订单(通过client_oid)
     * POST /api/futures/v3/cancel_order/<instrument_id>/<client_oid>
     */
    @Test
    public void testCancelOrderByClientOid() {
        JSONObject result = tradeAPIService.cancelOrderByClientOid("DOT-USDT-210326","1024funtures02");
        toResultString(LOG, "Cancel-Instrument-Order", result);
    }

    /**
     * 批量撤销订单(通过order_id)
     * POST /api/futures/v3/cancel_batch_orders/<instrument_id>
     */
    @Test
    public void testCancelOrdersByOrderId() {
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<String>();
        //通过订单号撤销订单
        list.add("5820414716996622");
        list.add("5820414716996623");

        cancelOrders.setOrder_ids(list);
        JSONObject result = tradeAPIService.cancelOrdersByOrderId("DOT-USDT-210326", cancelOrders);
        toResultString(LOG, "Cancel-Instrument-Orders", result);
    }

    /**
     * 批量撤销订单(通过client_oid)
     * POST /api/futures/v3/cancel_batch_orders/<instrument_id>
     */
    @Test
    public void testCancelOrdersByClientOid() {
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<String>();
        //通过client_oid撤销订单
        list.add("1024funtures05");
        list.add("1024funtures06");
        cancelOrders.setClient_oids(list);
        JSONObject result = tradeAPIService.cancelOrdersByClientOid("DOT-USDT-210326", cancelOrders);
        toResultString(LOG, "Cancel-Instrument-Orders", result);
    }

    /**
     * 修改订单(通过order_id)
     * POST  /api/futures/v3/amend_order/<instrument_id>
     */
    @Test
    public void testAmendOrderByOrderId(){
        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setOrder_id("5820428012414978");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_size("2");
        amendOrder.setNew_price("5");

        JSONObject result = tradeAPIService.amendOrderByOrderId("DOT-USDT-210326",amendOrder);
        toResultString(LOG, "amend-Instrument-Orders", result);
    }

    /**
     * 修改订单(通过client_oid)
     * POST  /api/futures/v3/amend_order/<instrument_id>
     */
    @Test
    public void testAmendOrderByClientOid(){
        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setClient_oid("1024funtures07");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_size("2");
        amendOrder.setNew_price("4.83");

        JSONObject result = tradeAPIService.amendOrderByClientOId("DOT-USDT-210326",amendOrder);
        toResultString(LOG, "amend-Instrument-Orders", result);
    }

    /**
     * 批量修改订单(通过order_id)
     * POST /api/futures/v3/amend_batch_orders/<instrument_id>
     */
    @Test
    public void testAmendBatchOrderByOrderId(){
        List<AmendOrder> list = new ArrayList<>();

        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setOrder_id("5824708575156226");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_size("2");
        amendOrder.setNew_price("4.85");

        AmendOrder amendOrder1 = new AmendOrder();
        amendOrder1.setCancel_on_fail("0");
        amendOrder1.setOrder_id("5824708575156227");
        amendOrder1.setRequest_id(null);
        amendOrder1.setNew_size("3");
        amendOrder1.setNew_price("4.86");


        AmendDateParam amendDateParam = new AmendDateParam();
        list.add(amendOrder);
        list.add(amendOrder1);
        amendDateParam.setAmend_data(list);


        JSONObject result = tradeAPIService.amendBatchOrdersByOrderId("DOT-USDT-210326",amendDateParam);
        toResultString(LOG, "amend-Instrument-Orders", result);
    }

    /**
     * 批量修改订单(通过client_oid)
     * POST /api/futures/v3/amend_batch_orders/<instrument_id>
     */
    @Test
    public void testAmendBatchOrderByClientOid(){
        List<AmendOrder> list = new ArrayList<>();

        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setClient_oid("1025futures01");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_size("1");
        amendOrder.setNew_price("4.85");

        AmendOrder amendOrder1 = new AmendOrder();
        amendOrder1.setCancel_on_fail("0");
        amendOrder1.setClient_oid("1025futures02");
        amendOrder1.setRequest_id(null);
        amendOrder1.setNew_size("1");
        amendOrder1.setNew_price("4.834");


        list.add(amendOrder);
        list.add(amendOrder1);
        AmendDateParam amendDateParam = new AmendDateParam();
        amendDateParam.setAmend_data(list);

        JSONObject result = tradeAPIService.amendBatchOrdersByClientOid("DOT-USDT-210326",amendDateParam);
        toResultString(LOG, "amend-Instrument-Orders", result);
    }

    /**
     * 获取订单列表
     * GET /api/futures/v3/orders/<instrument_id>
     */
    @Test
    public void testGetOrders() {
        JSONObject result = tradeAPIService.getOrders("DOT-USDT-210326", "0", null, null, "100");
        toResultString(LOG, "Get-Orders", result);
    }

    /**
     * 获取订单信息
     * GET /api/futures/v3/orders/<instrument_id>/<order_id>
     */
    @Test
    public void testGetOrderByOrderId() {
        JSONObject result = tradeAPIService.getOrderByOrderId("DOT-USDT-210326", "5824708575156226");
        toResultString(LOG, "Get-Order", result);
    }

    /**
     * 获取订单信息
     * GET /api/futures/v3/orders/<instrument_id>/<client_oid>
     */
    @Test
    public void testGetOrderByClientOid() {
        JSONObject result = tradeAPIService.getOrderByClientOid("DOT-USDT-210326", "1025futures01");
        toResultString(LOG, "Get-Order", result);
    }


    /**
     * 获取成交明细
     * GET /api/futures/v3/fills
     */
    @Test
    public void testGetFills() {
        JSONArray result = tradeAPIService.getFills("", "BTC-USDT-201225", null, null, "100");
        toResultString(LOG, "Get-Fills", result);
    }

    /**
     * 设置合约币种账户模式
     * POST /api/futures/v3/accounts/margin_mode
     */
    @Test
    public void testChangeMarginMode() {
        ChangeMarginMode changeMarginMode = new ChangeMarginMode();
        changeMarginMode.setUnderlying("LTC-USDT");
        changeMarginMode.setMargin_mode("crossed");
        JSONObject jsonObject = tradeAPIService.changeMarginMode(changeMarginMode);
        toResultString(LOG, "MarginMode", jsonObject);
    }

    /**
     * 市价全平
     * POST /api/futures/v3/close_position
     */
    @Test
    public void testClosePositions() {
        ClosePositions closePositions = new ClosePositions();
        closePositions.setInstrument_id("BTC-USDT-201225");
        closePositions.setDirection("short");
        JSONObject jsonObject = tradeAPIService.closePositions(closePositions);
        toResultString(LOG, "closePositions", jsonObject);
    }

    /**
     * 撤销所有平仓挂单
     * POST /api/futures/v3/cancel_all
     */
    @Test
    public void testcancelAll() {
        CancelAll  cancelAll = new CancelAll();
        cancelAll.setInstrument_id("LTC-USDT-201225");
        cancelAll.setDirection("short");
        JSONObject jsonObject = tradeAPIService.cancelAll(cancelAll);
        toResultString(LOG, "cancelAll", jsonObject);
    }

    /**
     * 获取合约挂单冻结数量
     * GET /api/futures/v3/accounts/BTC-USD-181228/holds
     */
    @Test
    public void testGetAccountsHoldsByinstrument_id() {
        JSONObject ledger = tradeAPIService.getAccountsHoldsByInstrumentId("DOT-USDT-210326");
        toResultString(LOG, "Ledger", ledger);
    }

    /**
     * 策略委托下单
     * POST /api/futures/v3/order_algo
     */
    @Test
    public void testFuturesOrder(){
        FuturesOrderParam futuresOrderParam=new FuturesOrderParam();
        //公共参数
        futuresOrderParam.setInstrument_id("DOT-USDT-210326");
        futuresOrderParam.setType("1");
        futuresOrderParam.setOrder_type("1");
        futuresOrderParam.setSize("1");

        //计划委托
        futuresOrderParam.setTrigger_price("4.2");
        futuresOrderParam.setAlgo_price("4.05");
        futuresOrderParam.setAlgo_type("1");

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

        //止盈止损
/*        futuresOrderParam.setTp_trigger_price("0.27");
        futuresOrderParam.setTp_price("0.26");
        futuresOrderParam.setTp_trigger_type("1");
        futuresOrderParam.setSl_trigger_price("0.29");
        futuresOrderParam.setSl_price("0.295");
        futuresOrderParam.setSl_trigger_type("1");*/

        FuturesOrderResult futuresOrderResult=tradeAPIService.futuresOrder(futuresOrderParam);
        toResultString(LOG, "futuresOrderResult", futuresOrderResult);
    }

    /**
     * 策略委托撤单
     * POST /api/futures/v3/cancel_algos
     */
    @Test
    public void testCancelFuturesOrder(){
        CancelFuturesOrder cancelFuturesOrder=new CancelFuturesOrder();
        cancelFuturesOrder.setInstrument_id("DOT-USDT-210326");
        cancelFuturesOrder.setOrder_type("1");
        List<String> algo_ids=new ArrayList<String>();
        algo_ids.add("5824794206389248");
        cancelFuturesOrder.setAlgo_ids(algo_ids);

        CancelFuturesOrdeResult cancelFuturesOrdeResult=tradeAPIService.cancelFuturesOrder(cancelFuturesOrder);
        toResultString(LOG, "cancelFuturesOrdeResult", cancelFuturesOrdeResult);
    }

    /**
     * 获取委托单列表
     * GET /api/futures/v3/order_algo/<instrument_id>
     */
    @Test
    public void testFindFuturesOrder(){

        String result = tradeAPIService.findFuturesOrder("DOT-USDT-210326", "1", "3",null, null, null,"10");
        toResultString(LOG, "Get-FuturesOrders", result);

    }

    /**
     * 当前账户交易手续的费率
     * GET/api/futures/v3/trade_fee
     */
    @Test
    public void testGetTradeFee(){
        JSONObject result = tradeAPIService.getTradeFee("3",null);
        toResultString(LOG, "result", result);
    }

    /**
     * 增加/减少保证金
     * POST/api/futures/v3/position/margin
     */
    @Test
    public void testModifyMargin(){
        ModifyMarginParam modifyMarginParam = new ModifyMarginParam();
        modifyMarginParam.setInstrument_id("XRP-USDT-201225");
        modifyMarginParam.setDirection("long");
        modifyMarginParam.setAmount("0.5");
        modifyMarginParam.setType("1");

        JSONObject result = tradeAPIService.modifyMargin(modifyMarginParam);
        toResultString(LOG, "result", result);
    }

    /**
     * 设置逐仓自动增加保证金
     * POST /api/futures/v3/accounts/auto_margin
     */
    @Test
    public void testModifyFixedMargin(){
        ModifyFixedMargin modifyFixedMargin = new ModifyFixedMargin();
        modifyFixedMargin.setUnderlying("BTC-USD");
        modifyFixedMargin.setType("1");

        JSONObject result = tradeAPIService.modifyFixedMargin(modifyFixedMargin);
        toResultString(LOG, "result", result);
    }

}
