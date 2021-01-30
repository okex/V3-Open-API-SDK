package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.option.param.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface OptionTradeAPI {

    //单个标的指数持仓信息
    @GET("/api/option/v3/{underlying}/position")
    Call<JSONObject> getPosition(@Path("underlying") String underlying,@Query("instrument_id") String instrument_id);

    //单个标的物账户信息
    @GET("/api/option/v3/accounts/{underlying}")
    Call<JSONObject> getAccount(@Path("underlying") String underlying);

    //下单
    @POST("/api/option/v3/order")
    Call<JSONObject> placeOrder(@Body OrderParam orderParam);

    //批量下单
    @POST("/api/option/v3/orders")
    Call<JSONObject> placeOrders(@Body OrderDataParam orderDataParam);

    //撤单（通过order_id）
    @POST("/api/option/v3/cancel_order/{underlying}/{order_id}")
    Call<JSONObject> cancelOrderByOrderId(@Path("underlying") String underlying,@Path("order_id") String order_id);

    //撤单（通过client_oid）
    @POST("/api/option/v3/cancel_order/{underlying}/{client_oid}")
    Call<JSONObject> cancelOrderByClientOid(@Path("underlying") String underlying, @Path("client_oid") String client_oid);

    //撤销全部订单
    @POST("/api/option/v3/cancel_all/{underlying}")
    Call<JSONObject> cancelAll(@Path("underlying") String underlying);

    //批量撤单（通过order_id）
    @POST("/api/option/v3/cancel_batch_orders/{underlying}")
    Call<JSONObject> cancelBatchOrdersByOrderId(@Path("underlying") String underlying, @Body CancelOrders cancelOrders);

    //批量撤单（通过client_oid）
    @POST("/api/option/v3/cancel_batch_orders/{underlying}")
    Call<JSONObject> cancelBatchOrdersByClientOid(@Path("underlying") String underlying, @Body CancelOrders cancelOrders);

    //修改订单(通过order_id)
    @POST("/api/option/v3/amend_order/{underlying}")
    Call<JSONObject> amendOrderByOrderId(@Path("underlying") String underlying,
                                @Body AmentDate amentDate);

    //修改订单(通过client_oid)
    @POST("/api/option/v3/amend_order/{underlying}")
    Call<JSONObject> amendOrderByClientOid(@Path("underlying") String underlying,
                                @Body AmentDate amentDate);

    //批量修改订单(通过order_id)
    @POST("/api/option/v3/amend_batch_orders/{underlying}")
    Call<JSONObject> amendBatchOrdersByOrderId(@Path("underlying") String underlying, @Body AmendDateParam amendDateParam);

    //批量修改订单(通过client_oid)
    @POST("/api/option/v3/amend_batch_orders/{underlying}")
    Call<JSONObject> amendBatchOrdersByClientOid(@Path("underlying") String underlying, @Body AmendDateParam amendDateParam);

    //获取单个订单状态(通过order_id)
    @GET("/api/option/v3/orders/{underlying}/{order_id}")
    Call<JSONObject> getOrderInfoByOrderId(@Path("underlying") String underlying,
                                  @Path("order_id") String order_id);

    //获取单个订单状态(通过client_oid)
    @GET("/api/option/v3/orders/{underlying}/{client_oid}")
    Call<JSONObject> getOrderInfoByClientOid(@Path("underlying") String underlying,
                                             @Path("client_oid") String client_oid);

    //获取订单列表
    @GET("/api/option/v3/orders/{underlying}")
    Call<JSONObject> getOrderList(@Path("underlying") String underlying,
                                  @Query("instrument_id") String instrument_id,
                                  @Query("after") String after,
                                  @Query("before") String before,
                                  @Query("limit") String limit,
                                  @Query("state") String state);

    //获取成交明细
    @GET("/api/option/v3/fills/{underlying}")
    Call<JSONArray> getFills(@Path("underlying") String underlying,
                             @Query("order_id") String order_id,
                             @Query("instrument_id") String instrument_id,
                             @Query("after") String after,
                             @Query("before") String before,
                             @Query("limit") String limit);

    //账单流水查询
    @GET("/api/option/v3/accounts/{underlying}/ledger")
    Call<JSONArray> getLedger(@Path("underlying") String underlying,
                              @Query("after") String after,
                              @Query("before") String before,
                              @Query("limit") String limit);

    //获取当前账户交易的手续费率
    @GET("/api/option/v3/trade_fee")
    Call<JSONObject> getTradeFee(@Query("category") String category,
                                 @Query("underlying") String underlying);

}
