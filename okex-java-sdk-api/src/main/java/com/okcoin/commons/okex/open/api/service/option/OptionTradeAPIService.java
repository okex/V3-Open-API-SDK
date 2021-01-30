package com.okcoin.commons.okex.open.api.service.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.okcoin.commons.okex.open.api.bean.option.param.*;
import retrofit2.http.Query;

import java.util.List;

public interface OptionTradeAPIService {

    //单个标的指数持仓信息
    JSONObject getPosition(String underlying,String instrument_id);

    //单个标的物账户信息
    JSONObject getAccount(String underlying);

    //下单
    JSONObject placeOrder(OrderParam orderParam);

    //批量下单
    JSONObject placeOrders(OrderDataParam orderDataParam);

    //撤单（通过order_id）
    JSONObject cancelOrderByOrderId(String underlying,String order_id);

    //撤单（通过client_oid）
    JSONObject cancelOrderByClientOid(String underlying,String client_oid);

    //撤销全部订单
    JSONObject cancelAll(String underlying);

    //批量撤单（通过order_id）
    JSONObject cancelBatchOrdersByOrderId(String underlying, CancelOrders cancelOrders);

    //批量撤单（通过client_oid）
    JSONObject cancelBatchOrdersByClientOid(String underlying, CancelOrders cancelOrders);

    //修改订单(通过order_id)
    JSONObject amendOrderByOrderId(String underlying, AmentDate amentDate);

    //修改订单（通过client_oid）
    JSONObject amendOrderByClientOid(String underlying, AmentDate amentDate);

    //批量修改订单
    JSONObject amendBatchOrdersByOrderId(String underlying, AmendDateParam amendDateParam);

    //批量修改订单
    JSONObject amendBatchOrdersByClientOid(String underlying, AmendDateParam amendDateParam);

    //获取单个订单状态(通过order_id)
    JSONObject getOrderInfoByOrderId(String underlying,String order_id);

    //获取单个订单状态(通过client_oid)
    JSONObject getOrderInfoByClientOid(String underlying,String client_oid);

    //获取订单列表
    JSONObject getOrderList(String underlying, String instrument_id, String after, String before, String limit, String state);

    //获取成交明细
    JSONArray getFills(String underlying, String order_id, String instrument_id, String after, String before, String limit);

    //获取账单流水
    JSONArray getLedger(String underlying,String after, String brfore, String limit);

    //获取手续费费率
    JSONObject getTradeFee(String category, String underlying);

}
