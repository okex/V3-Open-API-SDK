package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.*;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiCancelOrderVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.OrderCancelResult;
import com.okcoin.commons.okex.open.api.service.swap.SwapTradeAPIService;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapTradeAPIServiceImpl;
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
     * API交易提供限价单下单模式，只有当您的账户有足够的资金才能下单。
     * 一旦下单，您的账户资金将在订单生命周期内被冻结，被冻结的资金以及数量取决于订单指定的类型和参数。目前api下单只支持以美元为计价单位
     * POST /api/swap/v3/order
     * 限速规则：40次/2s
     * {"match_price":"0","size":"1","price":"55","client_oid":"2016TestOrder2","type":"2","instrument_id":"LTC-USD-SWAP","order_type":"0"}
     */
    @Test
    public void order() {
        PpOrder ppOrder = new PpOrder("testsawp073102", "1", "1", "0","0.242", "XRP-USDT-SWAP","0");
        final  Object apiOrderVO = tradeAPIService.order(ppOrder);
        this.toResultString(SwapTradeTest.LOG, "orders", apiOrderVO);
        System.out.println("jsonObject:"+apiOrderVO);

    }


    /**
     * 批量下单
     * 批量进行下单请求，每个合约可批量下10个单。
     * POST /api/swap/v3/orders
     * 限速规则：20次/2s
     */
    @Test
    public void batchOrder() {

        List<PpBatchOrder> list = new LinkedList<>();
        list.add(new PpBatchOrder("testSwap003", "1", "1", "0", "0.241","0"));
        list.add(new PpBatchOrder("testSwap004", "1", "2", "0", "0.248","0"));
        /*list.add(new PpBatchOrder(null, "30", "2", "0", null,"4"));
        list.add(new PpBatchOrder(null, "30", "1", "0", null,"4"));
*/
        PpOrders ppOrders = new PpOrders();
        ppOrders.setInstrument_id("XRP-USDT-SWAP");
        ppOrders.setOrder_data(list);
        String jsonObject = tradeAPIService.orders(ppOrders);
        //ApiOrderResultVO apiOrderResultVO = JSONObject.parseObject(jsonObject, ApiOrderResultVO.class);
        System.out.println("success");
        System.out.println(jsonObject);


    }

    /**
     * 撤单
     * 撤销之前下的未完成订单。
     * POST /api/swap/v3/cancel_order/<instrument_id>/<order_id> or <client_oid>
     * 限速规则：40次/2s
     */
    @Test
    public void cancelOrderByOrderId() {
        String jsonObject = tradeAPIService.cancelOrderByOrderId("XRP-USDT-SWAP", "555275263841845249");
        ApiCancelOrderVO apiCancelOrderVO = JSONObject.parseObject(jsonObject, ApiCancelOrderVO.class);
        System.out.println("success");
        System.out.println(apiCancelOrderVO.getOrder_id());
    }

    @Test
    public void cancelOrderByClientOid() {
        String jsonObject = tradeAPIService.cancelOrderByClientOid("XRP-USDT-SWAP", "testsawp073102");
        ApiCancelOrderVO apiCancelOrderVO = JSONObject.parseObject(jsonObject, ApiCancelOrderVO.class);
        System.out.println("success");
        System.out.println(apiCancelOrderVO.getOrder_id());
    }

    /**
     * 批量撤单
     * 撤销之前下的未完成订单，每个币对可批量撤10个单。
     * POST /api/swap/v3/cancel_batch_orders/<instrument_id>
     * 限速规则：20次/2s
     */
    @Test
    public void batchCancelOrderByOrderId() {
        //生成一个PpCancelOrderVO对象
        PpCancelOrderVO ppCancelOrderVO = new PpCancelOrderVO();

        ppCancelOrderVO.getIds().add("555291306341613568");
        ppCancelOrderVO.getIds().add("555291306350002176");

        System.out.println(JSONObject.toJSONString(ppCancelOrderVO));
        String jsonObject = tradeAPIService.cancelOrders("XRP-USDT-SWAP", ppCancelOrderVO);
        OrderCancelResult orderCancelResult = JSONObject.parseObject(jsonObject, OrderCancelResult.class);
        System.out.println("success");
        System.out.println(orderCancelResult.getInstrument_id());
    }


    @Test
    public void batchCancelOrderByClientOid() {
        PpCancelOrderVO ppCancelOrderVO = new PpCancelOrderVO();
        List<String> oidlist = new ArrayList<String>();

        oidlist.add("testSwap003");
        oidlist.add("testSwap004");
        ppCancelOrderVO.setClientOids(oidlist);

        System.out.println(JSONObject.toJSONString(ppCancelOrderVO));
        String jsonObject = tradeAPIService.cancelOrders("XRP-USDT-SWAP", ppCancelOrderVO);
        OrderCancelResult orderCancelResult = JSONObject.parseObject(jsonObject, OrderCancelResult.class);
        System.out.println("success");
        System.out.println(orderCancelResult.getInstrument_id());
    }
    //修改订单（根据order_id）
    @Test
    public void testAmendOrder(){
        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setOrder_id("555275263841845249");
        amendOrder.setRequest_id("");
        amendOrder.setNew_price("0.241");
        amendOrder.setNew_size("1");

        String result = tradeAPIService.amendOrder("XRP-USDT-SWAP",amendOrder);
        System.out.println("success");
        System.out.println(result);

    }
    //修改订单（根据client_oid）
    @Test
    public void testAmentOrderByClientOid(){
        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setClient_oid("testsawp073101");
        amendOrder.setRequest_id("request01");
        amendOrder.setNew_price("0.242");
//        amendOrder.setNew_size("1");

        String result = tradeAPIService.amendOrderByClientOid("XRP-USDT-SWAP",amendOrder);
        System.out.println("success");
        System.out.println(result);
    }

   /* 批量修改订单（根据order_id）*/
    @Test
    public void testAmentBatchOrderByOrderId(){
        AmendOrderParam amendOrderParam = new AmendOrderParam();
        List<AmendOrder> list = new ArrayList<>();

        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setOrder_id("555291306341613568");
        amendOrder.setRequest_id("");
        amendOrder.setNew_price("0.243");
        amendOrder.setNew_size("1");

        AmendOrder amendOrder1 = new AmendOrder();
        amendOrder1.setCancel_on_fail("0");
        amendOrder1.setOrder_id("555291306350002176");
        amendOrder1.setRequest_id("");
        amendOrder1.setNew_price("0.289");
        amendOrder1.setNew_size("1");

        list.add(amendOrder);
        list.add(amendOrder1);
        amendOrderParam.setAmend_data(list);

        String result = tradeAPIService.amendBatchOrderByOrderId("XRP-USDT-SWAP",amendOrderParam);
        System.out.println("success");
        System.out.println(result);
    }


    /*批量修改订单（根据client_oid）*/
    @Test
    public void testAmentBatchOrderByClientOid(){
        AmendOrderParam amendOrderParam = new AmendOrderParam();
        List<AmendOrder> list = new ArrayList<>();

        AmendOrder amendOrder = new AmendOrder();
        amendOrder.setCancel_on_fail("0");
        amendOrder.setClient_oid("testSwap001");
        amendOrder.setRequest_id("");
        amendOrder.setNew_price("0.24");
        amendOrder.setNew_size("2");

        AmendOrder amendOrder1 = new AmendOrder();
        amendOrder1.setCancel_on_fail("0");
        amendOrder1.setClient_oid("testSwap002");
        amendOrder1.setRequest_id("");
        amendOrder1.setNew_price("0.29");
        amendOrder1.setNew_size("2");


        list.add(amendOrder);
        list.add(amendOrder1);

        amendOrderParam.setAmend_data(list);

        String result = tradeAPIService.amendBatchOrderByClientOid("XRP-USDT-SWAP",amendOrderParam);
        System.out.println("success");
        System.out.println(result);
    }



    /**
     * 委托策略下单
     * 提供止盈止损、跟踪委托、冰山委托和时间加权委托策略
     * POST /api/swap/v3/order_algo
     * 限速规则：40次/2s
     */
    @Test
    public void testSwapOrderAlgo(){
        SwapOrderParam swapOrderParam=new SwapOrderParam();
        //公共参数
        swapOrderParam.setInstrument_id("XRP-USDT-SWAP");
        swapOrderParam.setType("1");
        swapOrderParam.setOrder_type("1");
        swapOrderParam.setSize("1");

        //止盈止损
        swapOrderParam.setTrigger_price("0.242");
        swapOrderParam.setAlgo_price("0.241");
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

        String jsonObject = tradeAPIService.swapOrderAlgo(swapOrderParam);
        System.out.println("---------success--------");
        System.out.println(jsonObject);
    }

    /**
     * 委托策略撤单
     * 根据指定的algo_id撤销某个合约的未完成订单，每次最多可撤6（冰山/时间）/10（计划/跟踪）个。
     * POST /api/swap/v3/cancel_algos
     * 限速规则：20 次/2s
     */
    @Test
    public void testCancelOrderAlgo(){
        CancelOrderAlgo cancelOrderAlgo=new CancelOrderAlgo();
        List<String>  list = new ArrayList<>();
        list.add("555301223790723072");
       // list.add("");

        cancelOrderAlgo.setAlgo_ids(list);
        cancelOrderAlgo.setInstrument_id("XRP-USDT-SWAP");
        cancelOrderAlgo.setOrder_type("1");

        String jsonObject = tradeAPIService.cancelOrderAlgo(cancelOrderAlgo);
        System.out.println("---------success--------");
        System.out.println(jsonObject);
    }

    /**
     * 获取委托单列表
     * 列出您当前所有的订单信息。
     * GET /api/swap/v3/order_algo/<instrument_id>
     * 限速规则：20次/2s
     */
    @Test
    public void testGetSwapAlgOrders(){
//        System.out.println("begin to show the swapAlgpOrders");
        String jsonObject = tradeAPIService.getSwapAlgoOrders("XRP-USDT-SWAP",
                                                            "1",
                                                            null,"555301223790723072",null,null,null);
        System.out.println(jsonObject);
    }

    /**
     * 市价全平
     * 市价全平接口，其中BTC合约持仓小于或等于999张时才能调用，否则报错；类似的，其他币种合约的持仓应该小于或等于9999张
     * POST/api/swap/v3/close_position
     * 限速规则：2次/2s
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
     * 此接口，仅支持撤销平仓的所有挂单。不包括开仓的挂单。
     * POST /api/swap/v3/cancel_all
     * 限速规则：5次/2s （根据underlying，分别限速）
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
