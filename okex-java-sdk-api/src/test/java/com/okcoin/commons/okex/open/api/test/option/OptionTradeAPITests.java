package com.okcoin.commons.okex.open.api.test.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.option.param.*;
import com.okcoin.commons.okex.open.api.service.option.OptionTradeAPIService;
import com.okcoin.commons.okex.open.api.service.option.impl.OptionTradeAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class OptionTradeAPITests extends OptionAPIBaseTests{

    private static final Logger LOG = LoggerFactory.getLogger(OptionTradeAPITests.class);

    private OptionTradeAPIService tradeAPIService;

    @Before
    public void before() {
        config = config();
        tradeAPIService = new OptionTradeAPIServiceImpl(config);
    }

    /**
     * 单个标的指数持仓信息
     * GET /api/option/v3/<underlying>/position
     */
    @Test
    public void testGetPosition(){
        JSONObject result = tradeAPIService.getPosition("BTC-USD","BTC-USD-201225-7000-P");
        toResultString(LOG,"result",result);
    }

    /**
     * 单个标的物账户信息
     * GET /api/option/v3/accounts/<underlying>
     */
    @Test
    public void testGetAccount(){
        JSONObject result = tradeAPIService.getAccount("BTC-USD");
        toResultString(LOG, "Accounts", result);
    }

    /**
     * 下单
     * POST /api/option/v3/order
     */
    @Test
    public void testPlaceOrder(){
        OrderParam param = new OrderParam();
        param.setClient_oid("testoption01");
        param.setInstrument_id("BTC-USD-201225-11500-C");
        param.setSide("sell");
        param.setOrder_type("0");
        param.setPrice("0.001497");
        param.setSize("1");
        param.setMatch_price("0");

        JSONObject result = tradeAPIService.placeOrder(param);
        toResultString(LOG,"result",result);
    }

    /**
     *  批量下单
     * POST /api/option/v3/orders
     */
    @Test
    public void testPlaceOrders(){
        OrderDataParam orderDataParam = new OrderDataParam();
        orderDataParam.setUnderlying("BTC-USD");

        OrderParam orderParam = new OrderParam();
        orderParam.setClient_oid("");
        orderParam.setInstrument_id("BTC-USD-200925-7500-C");
        orderParam.setSide("buy");
        orderParam.setOrder_type("0");
        orderParam.setPrice("0.0005");
        orderParam.setSize("1");
        orderParam.setMatch_price("0");

        OrderParam orderParam1 = new OrderParam();
        orderParam1.setClient_oid("");
        orderParam1.setInstrument_id("BTC-USD-2000925-7500-C");
        orderParam1.setSide("buy");
        orderParam1.setOrder_type("0");
        orderParam1.setPrice("0.001");
        orderParam1.setSize("1");
        orderParam1.setMatch_price("0");

        List<OrderParam> list = new ArrayList();
        list.add(orderParam);
        list.add(orderParam1);
        orderDataParam.setOrderdata(list);

        JSONObject result = tradeAPIService.placeOrders(orderDataParam);
        toResultString(LOG,"result",result);

    }

    /**
     * 撤单
     * POST /api/option/v3/cancel_order/<underlying>/<order_id>
     */
    @Test
    public void testCancelOrderByOrderId(){
        JSONObject result = tradeAPIService.cancelOrderByOrderId("BTC-USD","127775390743711744");
        toResultString(LOG,"result",result);
    }

    /**
     * 撤单
     * POST /api/option/v3/cancel_order/<underlying>/<client_oid>
     */
    @Test
    public void testCancelOrderByClientOid(){
        JSONObject result = tradeAPIService.cancelOrderByClientOid("BTC-USD","option0326teset1");
        toResultString(LOG,"result",result);
    }

    /**
     * 撤销全部订单
     * POST /api/option/v3/cancel_all/<underlying>
     */
    @Test
    public void testCancalAll(){
        JSONObject result = tradeAPIService.cancelAll("BTC-USD");
        toResultString(LOG,"result",result);
    }

    /**
     * 批量撤单(通过order_id)
     * POST /api/option/v3/cancel_batch_orders/<underlying>
     */
    @Test
    public void testCancelBantchOrdersByOrderId(){
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<>();

        list.add("125243617098915840");
        list.add("125243617098915841");
        cancelOrders.setOrder_ids(list);

        JSONObject result = tradeAPIService.cancelBatchOrdersByOrderId("BTC-USD",cancelOrders);
        toResultString(LOG,"result",result);
    }

    /**
     * 批量撤单（通过client_oid）
     * POST /api/option/v3/cancel_batch_orders/<underlying>
     */
    @Test
    public void testCancelBantchOrdersByClientOid(){
        CancelOrders cancelOrders = new CancelOrders();
        List<String> list = new ArrayList<>();

        list.add("testoption1");
        list.add("testoption1");
        cancelOrders.setClient_oids(list);

        JSONObject result = tradeAPIService.cancelBatchOrdersByClientOid("BTC-USD",cancelOrders);
        toResultString(LOG,"result",result);
    }

    /**
     *修改订单(通过order_id)
     * POST /api/option/v3/amend_order/<underlying>
     */
    @Test
    public void testAmendOrderByOrderId(){
        AmentDate amentDate = new AmentDate();

        amentDate.setCancel_on_fail("");
        amentDate.setRequest_id("");
        amentDate.setOrder_id("158444945847922688");
        amentDate.setNew_size("2");
        amentDate.setNew_price("0.001");

        JSONObject result = tradeAPIService.amendOrderByOrderId("BTC-USD",amentDate);
        toResultString(LOG,"result",result);
    }

    /**
     *修改订单（通过client_oid）
     * POST /api/option/v3/amend_order/<underlying>
     */
    @Test
    public void testAmendOrderByClientOid(){
        AmentDate amentDate = new AmentDate();

        amentDate.setCancel_on_fail("");
        amentDate.setRequest_id("");
        amentDate.setClient_oid("");
        amentDate.setNew_size("2");
        amentDate.setNew_price("0.0005");

        JSONObject result = tradeAPIService.amendOrderByClientOid("BTC-USD",amentDate);
        toResultString(LOG,"result",result);
    }

    /**
     * 批量修改订单(通过order_id)
     * POST /api/option/v3/amend_batch_orders/<underlying>
     */
    @Test
    public void testAmendBatchOrdersByOrderId(){
        AmendDateParam param = new AmendDateParam();

        AmentDate amentDate = new AmentDate();

        amentDate.setCancel_on_fail("");
        amentDate.setOrder_id("");
        amentDate.setRequest_id("");
        amentDate.setNew_size("");
        amentDate.setNew_price("");

        AmentDate amentDate1 = new AmentDate();

        amentDate1.setCancel_on_fail("");
        amentDate1.setOrder_id("");
        amentDate1.setRequest_id("");
        amentDate1.setNew_size("");
        amentDate1.setNew_price("");

        List<AmentDate> list = new ArrayList<>();
        list.add(amentDate);
        list.add(amentDate1);

        param.setAmend_data(list);

        JSONObject result = tradeAPIService.amendBatchOrdersByOrderId("BTC-USD",param);
        toResultString(LOG,"result",result);
    }

    /**
     * 批量修改订单（通过client_oid）
     * POST /api/option/v3/amend_batch_orders/<underlying>
     */
    @Test
    public void testAmendBatchOrdersByClientOid(){
        AmendDateParam param = new AmendDateParam();

        AmentDate amentDate = new AmentDate();

        amentDate.setCancel_on_fail("");
        amentDate.setClient_oid("");
        amentDate.setRequest_id("");
        amentDate.setNew_size("1");
        amentDate.setNew_price("0.001");

        AmentDate amentDate1 = new AmentDate();

        amentDate1.setCancel_on_fail("");
        amentDate1.setClient_oid("");
        amentDate1.setRequest_id("");
        amentDate1.setNew_size("2");
        amentDate1.setNew_price("0.001");

        List<AmentDate> list = new ArrayList<>();
        list.add(amentDate);
        list.add(amentDate1);

        param.setAmend_data(list);
        JSONObject result = tradeAPIService.amendBatchOrdersByClientOid("BTC-USD",param);
        toResultString(LOG,"result",result);
    }

    /**
     * 获取单个订单状态(通过order_id)
     * GET /api/option/v3/orders/<underlying>/<order_id>
     */
    @Test
    public void testGetOrderInfoByOrderId(){
        JSONObject result = tradeAPIService.getOrderInfoByOrderId("BTC-USD","136805624537206784");
        toResultString(LOG,"result",result);
    }

    /**
     * 获取单个订单状态(通过client_oid)
     * GET /api/option/v3/orders/<underlying>/<client_oid>
     */
    @Test
    public void testGetOrderInfoByClientOid(){
        JSONObject result = tradeAPIService.getOrderInfoByClientOid("BTC-USD","option0212teset1");
        toResultString(LOG,"result",result);
    }

    /**
     * 获取订单列表
     * GET /api/option/v3/orders/<underlying>
     */
    @Test
    public void testGetOrderList(){
        JSONObject result = tradeAPIService.getOrderList("BTC-USD",null,null,null,null,"-1");
        toResultString(LOG,"result",result);
    }

    /**
     * 获取成交明细
     * GET /api/option/v3/fills/<underlying>
     */
    @Test
    public void testGetFills(){
        JSONArray result = tradeAPIService.getFills("BTC-USD",null,null,null,null,null);
        toResultString(LOG,"result",result);
    }

    /**
     * 获取账单流水
     * GET /api/option/v3/accounts/<underlying>/ledger
     */
    @Test
    public void testGetLedger(){
        JSONArray result = tradeAPIService.getLedger("BTC-USD",null,null,null);
        toResultString(LOG,"result",result);
    }

    /**
     * 获取手续费费率
     * GET /api/option/v3/trade_fee
     */
    @Test
    public void testGetTradeFee(){
        JSONObject result = tradeAPIService.getTradeFee(null,"BTC-USD");
        toResultString(LOG,"result",result);
    }

}
