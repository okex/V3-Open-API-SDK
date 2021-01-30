package com.okcoin.commons.okex.open.api.service.swap;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.*;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderVO;
import retrofit2.http.Body;

import java.util.List;

public interface SwapTradeAPIService {

    //下单
    Object order(PpOrder ppOrder);

    //批量下单
    String orders(PpOrders ppOrders);

    //撤单(通过order_id)
    String cancelOrderByOrderId(String instrument_id, String order_id);

    //撤单(通过client_oid)
    String cancelOrderByClientOid(String instrument_id, String client_oid);

    //批量撤单(通过order_id)
    String cancelOrdersByOrderIds(String instrument_id, PpCancelOrderVO ppCancelOrderVO);

    //批量撤单(通过client_oid)
    String cancelOrdersByClientOids(String instrument_id, PpCancelOrderVO ppCancelOrderVO);

    //修改订单(通过order_id)
    String amendOrderByOrderId(String instrument_id,AmendOrder amendOrder);

    //修改订单(通过client_oid)
    String amendOrderByClientOid(String instrument_id,AmendOrder amendOrder);

    //批量修改订单(通过order_id)
    String amendBatchOrderByOrderId(String instrument_id, AmendOrderParam amendOrder);

    //批量修改订单(通过client_oid)
    String amendBatchOrderByClientOid(String instrument_id, AmendOrderParam amendOrder);

    //策略委托下单
    String swapOrderAlgo(SwapOrderParam swapOrderParam);

    //策略委托撤单
    String cancelOrderAlgo(CancelOrderAlgo cancelOrderAlgo);

    //获取委托单列表
    String getSwapOrders(String instrument_id,
                         String order_type,
                         String status,
                         String algo_id,
                         String before,
                         String after,
                         String limit);

    //市价全平
    String closePosition(ClosePosition closePosition);

    //撤销所有平仓挂单
    String CancelAll(CancelAllParam cancelAllParam);
}
