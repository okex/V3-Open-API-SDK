package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.*;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface SpotOrderAPI {

    //下单
    @POST("api/spot/v3/orders")
    Call<OrderResult> addOrder(@Body PlaceOrderParam order);

    //批量下单
    @POST("api/spot/v3/batch_orders")
    Call<Map<String, List<OrderResult>>> addOrders(@Body List<PlaceOrderParam> param);

    //撤销指定订单(通过order_id进行撤单)
    @HTTP(method = "POST", path = "api/spot/v3/cancel_orders/{order_id}", hasBody = true)
    Call<OrderResult> cancleOrderByOrderId(@Path("order_id") String order_id, @Body PlaceOrderParam order);

    //撤销指定订单(通过client_oid进行撤单)
    @HTTP(method = "POST", path = "api/spot/v3/cancel_orders/{client_oid}", hasBody = true)
    Call<OrderResult> cancleOrderByClientOid(@Path("client_oid") String client_oid, @Body PlaceOrderParam order);

    //批量撤销订单（通过order_id）
    @HTTP(method = "POST", path = "api/spot/v3/cancel_batch_orders", hasBody = true)
    Call<Map<String, Object>> batchCancleOrdersByOrderId(@Body List<OrderParamDto> cancleOrders);

    //批量撤销订单(通过client_oid)
    @HTTP(method = "POST", path = "api/spot/v3/cancel_batch_orders", hasBody = true)
    Call<Map<String, Object>> batchCancleOrdersByClientOid(@Body List<OrderParamDto> orderParamDto);

    //修改订单(通过order_id)
    @HTTP(method = "POST", path = "/api/spot/v3/amend_order/{instrument_id}", hasBody = true)
    Call<JSONObject> amendOrderByOrderId(@Path("instrument_id") String instrument_id, @Body AmendParam amendParam);

    //修改订单(通过client_oid )
    @HTTP(method = "POST", path = "/api/spot/v3/amend_order/{instrument_id}", hasBody = true)
    Call<JSONObject> amendOrderByClientOid(@Path("instrument_id") String instrument_id, @Body AmendParam amendParam);

    //批量修改订单(通过order_id )
    @HTTP(method = "POST", path = "/api/spot/v3/amend_batch_orders", hasBody = true)
    Call<JSONObject> batchAmendOrderByOrderId(@Body List<AmendParam> amendParam);

    //批量修改订单(通过client_oid )
    @HTTP(method = "POST", path = "/api/spot/v3/amend_batch_orders", hasBody = true)
    Call<JSONObject> batchAmendOrdersByClientOid(@Body List<AmendParam> AmendParam);

    //获取订单列表
    @GET("api/spot/v3/orders")
    Call<List<OrderInfo>> getOrders(@Query("instrument_id") String instrument_id,
                                    @Query("state") String state,
                                    @Query("after") String after,
                                    @Query("before") String before,
                                    @Query("limit") String limit
                                   );

    //获取所有未成交订单
    @GET("api/spot/v3/orders_pending")
    Call<List<PendingOrdersInfo>> getPendingOrders(@Query("instrument_id") String instrument_id,
                                                   @Query("after") String after,
                                                   @Query("before") String before,
                                                   @Query("limit") String limit);

    //获取订单信息(通过order_id)
    @GET("api/spot/v3/orders/{order_id}")
    Call<JSONObject> getOrderByOrderId(@Path("order_id") String order_id,
                                       @Query("instrument_id") String instrument_id);

    //获取订单信息(通过client_oid)
    @GET("api/spot/v3/orders/{client_oid}")
    Call<JSONObject> getOrderByClientOid(@Path("client_oid") String client_oid,
                                         @Query("instrument_id") String instrument_id);

    //获取成交明细
    @GET("api/spot/v3/fills")
    Call<List<Fills>> getFills(@Query("order_id") String order_id,
                               @Query("instrument_id") String instrument_id,
                               @Query("after") String after,
                               @Query("before") String before,
                               @Query("limit") String limit);

    //委托策略下单
    @POST("api/spot/v3/order_algo")
    Call<OrderAlgoResult> addorder_algo(@Body OrderAlgoParam order);

    //委托策略撤单
    @POST("api/spot/v3/cancel_batch_algos")
    Call<CancelAlgoResult> cancelOrder_algo(@Body CancelAlgoParam cancelAlgoParam);

    //获取委托单列表
    @GET("/api/spot/v3/algo")
    Call <String> getAlgoOrder(@Query("instrument_id") String instrument_id,
                               @Query("order_type") String order_type,
                               @Query("status") String status,
                               @Query("algo_ids") String algo_ids,
                               @Query("before") String before,
                               @Query("after") String after,
                               @Query("limit") String limit);

}
