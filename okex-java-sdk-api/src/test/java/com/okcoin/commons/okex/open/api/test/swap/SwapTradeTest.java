package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelAll;
import com.okcoin.commons.okex.open.api.bean.swap.param.*;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiCancelOrderVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderResultVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.OrderCancelResult;
import com.okcoin.commons.okex.open.api.service.swap.SwapTradeAPIService;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapTradeAPIServiceImpl;
import com.okcoin.commons.okex.open.api.test.spot.SpotOrderAPITest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SwapTradeTest extends SwapBaseTest {
    private SwapTradeAPIService tradeAPIService;
    private static final Logger LOG = LoggerFactory.getLogger(SwapTradeTest.class);


    @Before
    public void before() {
        config = config();
        tradeAPIService = new SwapTradeAPIServiceImpl(config);
    }

    /**
     * 下单
     * POST /api/swap/v3/order
     */
    @Test
    public void order() {
        PpOrder ppOrder = new PpOrder("1026testswap01", "1", "2", "0","4.5", "DOT-USDT-SWAP","0");
        final  Object apiOrderVO = tradeAPIService.order(ppOrder);
        this.toResultString(SwapTradeTest.LOG, "orders", apiOrderVO);
        System.out.println("jsonObject:"+apiOrderVO);

    }

    /**
     * 批量下单
     * POST /api/swap/v3/orders
     */
    @Test
    public void batchOrder() {

        List<PpBatchOrder> list = new LinkedList<>();
        list.add(new PpBatchOrder("1026testswap07", "1", "2", "0", "4.51","0"));
        list.add(new PpBatchOrder("1026testswap08", "1", "2", "0", "4.54","0"));

        PpOrders ppOrders = new PpOrders();
        ppOrders.setInstrument_id("DOT-USDT-SWAP");
        ppOrders.setOrder_data(list);
        String jsonObject = tradeAPIService.orders(ppOrders);
        //ApiOrderResultVO apiOrderResultVO = JSONObject.parseObject(jsonObject, ApiOrderResultVO.class);
        System.out.println("success");
        System.out.println(jsonObject);
    }

    /**
     * 撤单
     * POST /api/swap/v3/cancel_order/<instrument_id>/<order_id> or <client_oid>
     */
    @Test
    public void cancelOrderByOrderId() {
        String jsonObject = tradeAPIService.cancelOrderByOrderId("DOT-USDT-SWAP", "618617501149466625");
        ApiCancelOrderVO apiCancelOrderVO = JSONObject.parseObject(jsonObject, ApiCancelOrderVO.class);
        System.out.println("success");
        System.out.println(apiCancelOrderVO.getOrder_id());
    }

    /**
     * 撤单
     * POST /api/swap/v3/cancel_order/<instrument_id>/<client_oid>
     */
    @Test
    public void cancelOrderByClientOid() {
        String jsonObject = tradeAPIService.cancelOrderByClientOid("DOT-USDT-SWAP", "1026testswap03");
        ApiCancelOrderVO apiCancelOrderVO = JSONObject.parseObject(jsonObject, ApiCancelOrderVO.class);
        System.out.println("success");
        System.out.println(apiCancelOrderVO.getOrder_id());
    }

    /**
     * 批量撤单
     * POST /api/swap/v3/cancel_batch_orders/<instrument_id>
     */
    @Test
    public void batchCancelOrderByOrderId() {
        //生成一个PpCancelOrderVO对象
        PpCancelOrderVO ppCancelOrderVO = new PpCancelOrderVO();

        ppCancelOrderVO.getIds().add("618619002810961920");
        ppCancelOrderVO.getIds().add("618619002810961921");

        System.out.println(JSONObject.toJSONString(ppCancelOrderVO));
        String jsonObject = tradeAPIService.cancelOrdersByOrderIds("DOT-USDT-SWAP", ppCancelOrderVO);
        OrderCancelResult orderCancelResult = JSONObject.parseObject(jsonObject, OrderCancelResult.class);
        System.out.println("success");
        System.out.println(orderCancelResult.getInstrument_id());
    }

    /**
     * 批量撤单
     * POST /api/swap/v3/cancel_batch_orders/<instrument_id>
     */
    @Test
    public void batchCancelOrderByClientOid() {
        PpCancelOrderVO ppCancelOrderVO = new PpCancelOrderVO();
        List<String> oidlist = new ArrayList<String>();

        oidlist.add("1026testswap07");
        oidlist.add("1026testswap08");
        ppCancelOrderVO.setClientOids(oidlist);

        System.out.println(JSONObject.toJSONString(ppCancelOrderVO));
        String jsonObject = tradeAPIService.cancelOrdersByClientOids("DOT-USDT-SWAP", ppCancelOrderVO);
        OrderCancelResult orderCancelResult = JSONObject.parseObject(jsonObject, OrderCancelResult.class);
        System.out.println("success");
        System.out.println(orderCancelResult.getInstrument_id());
    }

    /**
     * 修改订单(通过order_id)
     * POST /api/swap/v3/amend_order/<instrument_id>
     */
    @Test
    public void testAmendOrderByOrderId(){
        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setOrder_id("618614099317264384");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_price("4.52");
        amendOrder.setNew_size("2");

        String result = tradeAPIService.amendOrderByOrderId("DOT-USDT-SWAP",amendOrder);
        System.out.println("success");
        System.out.println(result);

    }

    /**
     * 修改订单(通过client_oid)
     * POST /api/swap/v3/amend_order/<instrument_id>
     */
    @Test
    public void testAmentOrderByClientOid(){
        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setClient_oid("1026testswap01");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_price("4.53");
        amendOrder.setNew_size("3");

        String result = tradeAPIService.amendOrderByClientOid("DOT-USDT-SWAP",amendOrder);
        System.out.println("success");
        System.out.println(result);
    }

    /**
     * 批量修改订单(通过order_id)
     * POST /api/swap/v3/amend_batch_orders/<instrument_id>
     */
    @Test
    public void testAmentBatchOrderByOrderId(){
        AmendOrderParam amendOrderParam = new AmendOrderParam();
        List<AmendOrder> list = new ArrayList<>();

        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setOrder_id("618619002810961920");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_price("4.54");
        amendOrder.setNew_size("2");

        AmendOrder amendOrder1 = new AmendOrder();
        amendOrder1.setCancel_on_fail("0");
        amendOrder1.setOrder_id("618619002810961921");
        amendOrder1.setRequest_id(null);
        amendOrder1.setNew_price("4.51");
        amendOrder1.setNew_size("3");

        list.add(amendOrder);
        list.add(amendOrder1);
        amendOrderParam.setAmend_data(list);

        String result = tradeAPIService.amendBatchOrderByOrderId("DOT-USDT-SWAP",amendOrderParam);
        System.out.println("success");
        System.out.println(result);
    }

    /**
     * 批量修改订单(通过client_oid)
     * POST /api/swap/v3/amend_batch_orders/<instrument_id>
     */
    @Test
    public void testAmentBatchOrderByClientOid(){
        AmendOrderParam amendOrderParam = new AmendOrderParam();
        List<AmendOrder> list = new ArrayList<>();

        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setClient_oid("1026testswap03");
        amendOrder.setRequest_id(null);
        amendOrder.setNew_price("4.52");
        amendOrder.setNew_size("1");

        AmendOrder amendOrder1 = new AmendOrder();
        amendOrder1.setCancel_on_fail("0");
        amendOrder1.setClient_oid("1026testswap04");
        amendOrder1.setRequest_id(null);
        amendOrder1.setNew_price("4.56");
        amendOrder1.setNew_size("1");


        list.add(amendOrder);
        list.add(amendOrder1);

        amendOrderParam.setAmend_data(list);

        String result = tradeAPIService.amendBatchOrderByClientOid("DOT-USDT-SWAP",amendOrderParam);
        System.out.println("success");
        System.out.println(result);
    }

    /**
     * 委托策略下单
     * POST /api/swap/v3/order_algo
     */
    @Test
    public void testSwapOrderAlgo(){
        SwapOrderParam swapOrderParam=new SwapOrderParam();
        //公共参数
        swapOrderParam.setInstrument_id("DOT-USDT-SWAP");
        swapOrderParam.setType("1");
        swapOrderParam.setOrder_type("1");
        swapOrderParam.setSize("1");

        //计划委托
        swapOrderParam.setTrigger_price("4.1");
        swapOrderParam.setAlgo_price("3.5");
        swapOrderParam.setAlgo_type("1");

        //跟踪委托
       /* swapOrderParam.setCallback_rate("");
        swapOrderParam.setTrigger_price("");*/

        //冰山委托
        /*swapOrderParam.setAlgo_variance("0.0015");
        swapOrderParam.setAvg_amount("1");
        swapOrderParam.setPrice_limit("0.2009");*/

        //时间加权
        /*swapOrderParam.setSweep_range("");
        swapOrderParam.setSweep_ratio("");
        swapOrderParam.setSingle_limit("");
        swapOrderParam.setPrice_limit("");
        swapOrderParam.setTime_interval("");*/

        //止盈止损
        /*swapOrderParam.setTp_trigger_price("0.26");
        swapOrderParam.setTp_price("0.255");
        swapOrderParam.setTp_trigger_type("1");*/
      /*  swapOrderParam.setSl_trigger_price("11700.2");
        swapOrderParam.setSl_price("11750.3");
        swapOrderParam.setSl_trigger_type("1");*/

        String jsonObject = tradeAPIService.swapOrderAlgo(swapOrderParam);
        System.out.println(jsonObject);
    }

    /**
     * 委托策略撤单
     * POST /api/swap/v3/cancel_algos
     */
    @Test
    public void testCancelOrderAlgo(){
        CancelOrderAlgo cancelOrderAlgo=new CancelOrderAlgo();
        List<String>  list = new ArrayList<>();
        list.add("618627634680205312");

        cancelOrderAlgo.setAlgo_ids(list);
        cancelOrderAlgo.setInstrument_id("DOT-USDT-SWAP");
        cancelOrderAlgo.setOrder_type("1");

        String jsonObject = tradeAPIService.cancelOrderAlgo(cancelOrderAlgo);
        System.out.println(jsonObject);
    }

    /**
     * 获取委托单列表
     * GET /api/swap/v3/order_algo/<instrument_id>
     */

    @Test
    public void testGetSwapAlgOrders(){

        String jsonObject = tradeAPIService.getSwapOrders("DOT-USDT-SWAP", "1", null,"618627634680205312",null,null,"10");
        System.out.println(jsonObject);
    }

    /**
     * 市价全平
     * POST/api/swap/v3/close_position
     */
    @Test
    public void testClosePosition(){
        ClosePosition closePosition = new ClosePosition();
        closePosition.setInstrument_id("XRP-USDT-SWAP");
        closePosition.setDirection("long");
        String result = tradeAPIService.closePosition(closePosition);
        System.out.println(result);

    }

    /**
     * 撤销所有平仓挂单
     * POST /api/swap/v3/cancel_all
     */
    @Test
    public void testCancelAll(){
        CancelAllParam cancelAllParam = new CancelAllParam();
        cancelAllParam.setInstrument_id("XRP-USDT-SWAP");
        cancelAllParam.setDirection("long");
        String result = tradeAPIService.CancelAll(cancelAllParam);
        System.out.println(result);

    }

}
