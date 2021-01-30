package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;

import java.util.List;
import java.util.Map;

/**
 * 杠杆订单相关接口
 */
public interface MarginOrderAPIService {

    //下单
    OrderResult addOrder(PlaceOrderParam order);

    //批量下单
    Map<String, List<OrderResult>> batchOrders(List<PlaceOrderParam> order);

    //撤销指定订单(通过order_id)
    OrderResult cancleOrdersByOrderId(final PlaceOrderParam order, String order_id);

    //撤销指定订单(通过client_oid)
    OrderResult cancleOrdersByClientOid(final PlaceOrderParam order, String client_oid);

    //批量撤销订单(通过order_id)
    Map<String, Object> batchCancleOrdersByOrderId(final List<OrderParamDto> cancleOrders);

    //批量撤销订单(通过client_oid)
    Map<String, Object> batchCancleOrdersByClientOid(final List<OrderParamDto> cancleOrders);

    //订单列表
    List<OrderInfo> getOrders(String instrument_id, String state, String after, String before, String limit);

    //获取订单信息(通过order_id)
    OrderInfo getOrderByOrderId(String instrument_id, String order_id);

    //获取订单信息(通过order_id)
    OrderInfo getOrderByClientOid(String client_oid,String instrument_id);

    //获取所有未成交订单
    List<PendingOrdersInfo> getPendingOrders(String instrument_id, String after, String before, String limit);

    //获取成交明细
    List<MarginFills> getFills(String order_id, String instrument_id, String after, String before, String limit);

}
