package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.*;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.Call;
import retrofit2.http.Body;

import java.util.List;
import java.util.Map;

public interface SpotOrderAPIServive {

    //下单
    OrderResult addOrder(PlaceOrderParam order);

    //批量下单
    Map<String, List<OrderResult>> addOrders(List<PlaceOrderParam> order);

    //撤销指定订单(通过order_id进行撤单)
    OrderResult cancleOrderByOrderId(final PlaceOrderParam order, String order_id);

    //撤销指定订单(通过client_oid进行撤单)
    OrderResult cancleOrderByClientOid(final PlaceOrderParam order, String client_oid);

    //批量撤销订单(通过order_id)
    Map<String, Object> batchCancleOrdersByOrderId(final List<OrderParamDto> cancleOrders);

    //批量撤销订单(通过client_oid)
    Map<String, Object> batchCancleOrdersByClientOid(List<OrderParamDto> orderParamDto);

    //修改订单(通过order_id)
    JSONObject amendOrderByOrderId(String instrument_id,  AmendParam amendParam);

    //修改订单(通过Client_oid)
    JSONObject amendOrderByClientOid(String instrument_id,  AmendParam amendParam);

    //批量修改订单(通过order_id)
    JSONObject batchAmendOrderByOrderId(List<AmendParam> amendParam);

    //批量修改订单(通过Client_oid)
    JSONObject batchAmendOrdersByClientOid(List<AmendParam> amendParam);

    //获取订单列表
    List<OrderInfo> getOrders(String instrument_id, String state, String after, String before, String limit);

    //获取所有未成交订单
    List<PendingOrdersInfo> getPendingOrders(String instrument_id, String after, String before, String limit);

    //获取订单信息(通过order_id)
    JSONObject getOrderByOrderId(String instrument_id, String order_id);

    //获取订单信息(通过client_oid)
    JSONObject getOrderByClientOid(String instrument_id, String client_oid);

    //获取成交明细
    List<Fills> getFills(String order_id, String instrument_id, String after, String before, String limit);

    //委托策略下单
    OrderAlgoResult addorder_algo(@Body OrderAlgoParam order);

    //委托策略撤单
    CancelAlgoResult cancelOrder_algo(@Body CancelAlgoParam cancelAlgoParam);

    //获取委托单列表
    String  getAlgoOrder(String instrument_id, String order_type, String status, String algo_ids, String before, String after, String limit);

}
