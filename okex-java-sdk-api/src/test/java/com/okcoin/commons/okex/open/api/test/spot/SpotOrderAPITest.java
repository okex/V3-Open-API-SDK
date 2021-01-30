package com.okcoin.commons.okex.open.api.test.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.*;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.service.spot.SpotOrderAPIServive;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotOrderApiServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpotOrderAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpotOrderAPITest.class);

    private SpotOrderAPIServive spotOrderAPIServive;

    @Before
    public void before() {
        this.config = this.config();
        this.spotOrderAPIServive = new SpotOrderApiServiceImpl(this.config);
    }

    /**
     * 下单
     * POST /api/spot/v3/orders
     */
    @Test
    public void addOrder() {
        final PlaceOrderParam order = new PlaceOrderParam();

        order.setClient_oid("1022testspot9");
        order.setType("limit");
        order.setSide("sell");
        order.setInstrument_id("EOS-USDT");
        order.setOrder_type("0");

        //限价单特殊参数

        order.setPrice("2.7");
        order.setSize("1");

        //市价单特殊参数  买入必填notional（买入金额），卖出必填size（卖出数量）
        //order.setSize("0.01");
        order.setNotional("");


        final OrderResult orderResult = this.spotOrderAPIServive.addOrder(order);
        this.toResultString(SpotOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 批量下单
     * POST /api/spot/v3/batch_orders
     */
    @Test
    public void batchAddOrder() {
        final List<PlaceOrderParam> list = new ArrayList<>();

        final PlaceOrderParam order = new PlaceOrderParam();
        order.setClient_oid("1028testspot5");
        order.setType("limit");
        order.setSide("sell");
        order.setInstrument_id("EOS-USDT");
        order.setOrder_type("0");

        //限价单特殊参数
        order.setPrice("3.5");
        order.setSize("1");

        //市价单特殊参数
        /*order.setSize("0.001");
        order.setNotional("0.25");*/

        list.add(order);


        final PlaceOrderParam order1 = new PlaceOrderParam();
        order1.setClient_oid("1028testspot6");
        order1.setType("limit");
        order1.setSide("sell");
        order1.setInstrument_id("EOS-USDT");
        order1.setOrder_type("0");

        //限价单特殊参数
        order1.setPrice("3.4");
        order1.setSize("1");


        //市价单特殊参数
        /*order1.setSize("0.001");
        order.setNotional("0.75");*/

        list.add(order1);

       /* final PlaceOrderParam order2 = new PlaceOrderParam();

        order2.setClient_oid("CTTXRP1226TEST01");
        order2.setType("limit");
        order2.setSide("sell");
        order2.setInstrument_id("XRP-USDT");
        order2.setOrder_type("0");

        //限价单特殊参数
        order2.setPrice("0.21");
        order2.setSize("1");

        //市价单特殊参数
        order2.setSize("1");
        order2.setNotional("0.75");

        list.add(order2);

        final PlaceOrderParam order3 = new PlaceOrderParam();

        order3.setClient_oid("CTTXRP1226TEST02");
        order3.setType("limit");
        order3.setSide("sell");
        order3.setInstrument_id("XRP-USDT");
        order3.setOrder_type("0");

        //限价单特殊参数
        order3.setPrice("0.23");
        order3.setSize("1");

        //市价单特殊参数
        order3.setSize("1");
        order3.setNotional("0.75");

        list.add(order3);*/

        final Map<String, List<OrderResult>> orderResult = this.spotOrderAPIServive.addOrders(list);
        this.toResultString(SpotOrderAPITest.LOG, "orders", orderResult);
    }

    /**
     * 撤销指定订单(通过order_id进行撤单)
     * POST /api/spot/v3/cancel_orders/<order_id>
     */
    @Test
    public void cancleOrderByOrderId() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("EOS-USDT");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByOrderId(order, "5802604883829760");
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 撤销指定订单(通过client_oid进行撤单)
     * POST /api/spot/v3/cancel_orders/<client_oid>
     */
    @Test
    public void cancleOrderByClientOid() {
        final PlaceOrderParam order = new PlaceOrderParam();
        order.setInstrument_id("EOS-USDT");
        final OrderResult orderResult = this.spotOrderAPIServive.cancleOrderByClientOid(order, "1022testspot2");
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrder", orderResult);
    }

    /**
     * 批量撤单(根据order_id进行撤单)
     * POST /api/spot/v3/cancel_batch_orders
     */
    @Test
    public void batchCancleOrdersByOrderId() {
        final List<OrderParamDto> cancleOrders = new ArrayList<>();

        final OrderParamDto dto = new OrderParamDto();

        dto.setInstrument_id("EOS-USDT");

        final List<String> order_ids = new ArrayList<>();
        order_ids.add("5802601851736064");
        order_ids.add("5802601851736065");

        dto.setOrder_ids(order_ids);
        cancleOrders.add(dto);

        /*final OrderParamDto dto1 = new OrderParamDto();

        dto1.setInstrument_id("XRP-USDT");

        final List<String> order_ids1 = new ArrayList<>();
        order_ids1.add("4096139176250370");
        order_ids1.add("4096139176250371");

        dto1.setOrder_ids(order_ids1);
        cancleOrders.add(dto1);*/

        final Map<String, Object> orderResult = this.spotOrderAPIServive.batchCancleOrdersByOrderId(cancleOrders);
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 批量撤单(根据client_oid进行撤单)
     * POST /api/spot/v3/cancel_batch_orders
     */
    @Test
    public void batchCancleOrdersByClientOid() {
        List<OrderParamDto> list = new ArrayList<>();

        OrderParamDto param1 = new OrderParamDto();

        param1.setInstrument_id("EOS-USDT");
        List<String> client_oid = new ArrayList<>();
        client_oid.add("1022testspot3");
        client_oid.add("1022testspot4");

        param1.setClient_oids(client_oid);
        list.add(param1);

        /*OrderParamDto param2 = new OrderParamDto();

        param2.setInstrument_id("XRP-USDT");

        List<String> client_oid1 = new ArrayList<>();
        client_oid1.add("CTTXRP1226TEST01");
        client_oid1.add("CTTXRP1226TEST02");

        param2.setClient_oids(client_oid1);

        list.add(param2);*/

        final Map<String, Object> orderResult = this.spotOrderAPIServive.batchCancleOrdersByClientOid(list);
        this.toResultString(SpotOrderAPITest.LOG, "cancleOrders", orderResult);
    }

    /**
     * 修改订单(通过order_id)
     * POST/api/spot/v3/amend_order/<instrument_id>
     */
    @Test
    public void testAmendOrderByOrderId(){
        AmendParam amendParam = new AmendParam();
        amendParam.setOrder_id("5801704821383168");
        amendParam.setCancel_on_fail("0");
        amendParam.setRequest_id(null);
        amendParam.setNew_size("1.5");
        amendParam.setNew_price("2.71");

        JSONObject result = this.spotOrderAPIServive.amendOrderByOrderId("EOS-USDT",amendParam);
        this.toResultString(SpotOrderAPITest.LOG, "amendOrder", result);

    }

    /**
     * 修改订单(通过client_oid)
     * POST/api/spot/v3/amend_order/<instrument_id>
     */
    @Test
    public void testAmendOrderByClientOid(){
        AmendParam amendParam = new AmendParam();
        amendParam.setClient_oid("1021testspot8");
        amendParam.setCancel_on_fail("0");
        amendParam.setRequest_id(null);
        amendParam.setNew_size("1.2");
        amendParam.setNew_price("2.72");

        JSONObject result = this.spotOrderAPIServive.amendOrderByClientOid("EOS-USDT",amendParam);
        this.toResultString(SpotOrderAPITest.LOG, "amendOrder", result);

    }

    /**
     * 批量修改订单(通过order_id)
    * POST/api/spot/v3/amend_batch_orders
     */
    @Test
    public void testBatchAmendOrdersByOrderId(){
        List<AmendParam> list = new ArrayList<>();
        AmendParam amendParam = new AmendParam();
        amendParam.setInstrument_id("EOS-USDT");
        amendParam.setOrder_id("5801704821383168");
        amendParam.setCancel_on_fail("0");
        amendParam.setRequest_id(null);
        amendParam.setNew_size("1.1");
        amendParam.setNew_price("2.732");

        AmendParam amendParam1 = new AmendParam();
        amendParam1.setInstrument_id("EOS-USDT");
        amendParam1.setOrder_id("5801704821383169");
        amendParam1.setCancel_on_fail("0");
        amendParam1.setRequest_id(null);
        amendParam1.setNew_size("1.3");
        amendParam1.setNew_price("2.733");


        list.add(amendParam);
        list.add(amendParam1);

        JSONObject result = this.spotOrderAPIServive.batchAmendOrderByOrderId(list);
        this.toResultString(SpotOrderAPITest.LOG, "batchAmendOrder", result);

    }

    /**
     * 批量修改订单(通过client_oid)
     * POST/api/spot/v3/amend_batch_orders
     */
    @Test
    public void testBatchAmendOrdersByClientOid(){
        List<AmendParam> list = new ArrayList<>();
        AmendParam amendParam = new AmendParam();
        amendParam.setInstrument_id("EOS-USDT");
        amendParam.setClient_oid("1021testspot8");
        amendParam.setCancel_on_fail("0");
        amendParam.setRequest_id(null);
        amendParam.setNew_size("1.2");
        amendParam.setNew_price("2.74");

        AmendParam amendParam1 = new AmendParam();
        amendParam1.setInstrument_id("EOS-USDT");
        amendParam1.setClient_oid("1021testspot9");
        amendParam1.setCancel_on_fail("0");
        amendParam1.setRequest_id(null);
        amendParam1.setNew_size("1.3");
        amendParam1.setNew_price("2.74");


        AmendDataParam amendDataParam = new AmendDataParam();
        list.add(amendParam);
        list.add(amendParam1);
        amendDataParam.setAmend_data(list);

        JSONObject result = this.spotOrderAPIServive.batchAmendOrdersByClientOid(list);
        this.toResultString(SpotOrderAPITest.LOG, "batchAmendOrder", result);

    }

    /**
     * 获取所有订单列表
     * GET /api/spot/v3/orders
     */
    @Test
    public void getOrders() {
        final List<OrderInfo> orderInfoList = this.spotOrderAPIServive.getOrders("OKB-USDT", "2", null, null, "10");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 获取所有未成交订单
     * GET /api/spot/v3/orders_pending
     */
    @Test
    public void getPendingOrders() {
        final List<PendingOrdersInfo> orderInfoList = this.spotOrderAPIServive.getPendingOrders("OKB-USDT", null, null, "10");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfoList", orderInfoList);
    }

    /**
     * 获取订单信息(通过order_id)
     * GET /api/spot/v3/orders/<order_id>
     */
    @Test
    public void getOrderByOrderId() {
        final JSONObject orderInfo = this.spotOrderAPIServive.getOrderByOrderId("XRP-USDT", "5836570486258688");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    /**
     * 获取订单信息(通过client_oid)
     * GET /api/spot/v3/orders/<order_id>
     */
    @Test
    public void getOrderByClientOid() {
        final JSONObject orderInfo = this.spotOrderAPIServive.getOrderByClientOid("EOS-USDT","1021testspot8");
        this.toResultString(SpotOrderAPITest.LOG, "orderInfo", orderInfo);
    }

    /**
     * 获取成交明细
     * GET/api/spot/v3/fills
     */
    @Test
    public void getFills() {
        final List<Fills> fillsList = this.spotOrderAPIServive.getFills(null, "OKB-USDT", null, null, null);
        this.toResultString(SpotOrderAPITest.LOG, "fillsList", fillsList);
    }

    /**
     * 策略委托下单
     * POST /api/spot/v3/order_algo
     */
    @Test
    public void addorder_algo(){
        final OrderAlgoParam order = new OrderAlgoParam();
       //公共参数
        order.setInstrument_id("EOS-USDT");
        order.setMode("1");
        order.setOrder_type("1");
        order.setSize("1");
        order.setSide("buy");

        //计划委托参数
        order.setTrigger_price("2.45");
        order.setAlgo_price("2.32");
        order.setAlgo_type("1");

       //跟踪委托
        /*order.setCallback_rate("0.01");
        order.setTrigger_price("10700.9");*/

        //冰山委托
        /*order.setAlgo_variance("0.01");
        order.setAvg_amount("10");
        order.setLimit_price("10500");*/

        //时间加权委托
        /*order.setSweep_range("0.01");
        order.setSweep_ratio("0.5");
        order.setSingle_limit("20");
        order.setLimit_price("10800");
        order.setTime_interval("10");*/

        //止盈止损参数
     /* order.setTp_trigger_price("0.26");
        order.setTp_price("0.255");
        order.setTp_trigger_type("1");
        order.setSl_trigger_price("0.29");
        order.setSl_price("0.295");
        order.setSl_trigger_type("1");*/

        final OrderAlgoResult orderAlgoResult = this.spotOrderAPIServive.addorder_algo(order);
        this.toResultString(SpotOrderAPITest.LOG, "orders", orderAlgoResult);

    }

    /**
     * 策略委托撤单
     * POST /api/spot/v3/cancel_batch_algos
     */
    @Test
    public void cancelOrder_algo(){
        final CancelAlgoParam cancelAlgoParam = new CancelAlgoParam();
        List<String> ids = new ArrayList<>();
        ids.add("3302565");
        ids.add("3302570");
        cancelAlgoParam.setInstrument_id("EOS-USDT");
        cancelAlgoParam.setOrder_type("1");
        cancelAlgoParam.setAlgo_ids(ids);
        final CancelAlgoResult cancelAlgoResult = this.spotOrderAPIServive.cancelOrder_algo(cancelAlgoParam);
        this.toResultString(SpotOrderAPITest.LOG, "cancleorder", cancelAlgoResult);

    }

    /**
     * 获取委托单列表
     * GET /api/spot/v3/algo
     */
    @Test
    public void getAlgOrder(){
        final String findAlgOrderResults=this.spotOrderAPIServive.getAlgoOrder("EOS-USDT","1","3",
                null,null,null,null);
                this.toResultString(SpotOrderAPITest.LOG, "findAlgOrderResults", findAlgOrderResults);
    }

}
