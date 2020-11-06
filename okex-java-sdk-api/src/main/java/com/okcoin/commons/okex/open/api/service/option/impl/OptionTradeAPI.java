package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.option.param.AmendDateParam;
import com.okcoin.commons.okex.open.api.bean.option.param.AmentDate;
import com.okcoin.commons.okex.open.api.bean.option.param.CancelOrders;
import com.okcoin.commons.okex.open.api.bean.option.param.OrderDataParam;
import com.okcoin.commons.okex.open.api.bean.option.param.OrderParam;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OptionTradeAPI {

    //获取单个标的物账户信息
    @GET("/api/option/v3/accounts/{underlying}")
    Call<JSONObject> getAccount(@Path("underlying") String underlying);

    //修改之前下的未完成订单
    @POST("/api/option/v3/amend_order/{underlying}")
    Call<JSONObject> amendOrder(@Path("underlying") String underlying,
                                @Body AmentDate amentDate);

    //修改之前下的未完成订单，每个标的指数最多可批量修改10个单
    @POST("/api/option/v3/amend_batch_orders/{underlying}")
    Call<JSONObject> amendBatchOrders(@Path("underlying") String underlying, @Body AmendDateParam amendDateParam);


    //撤单
    @POST("/api/option/v3/cancel_order/{underlying}/{order_id}")
    Call<JSONObject> cancelOrder(@Path("underlying") String underlying,@Path("order_id") String order_id);

    //根据client_oid撤单
    @POST("/api/option/v3/cancel_order/{underlying}/{client_oid}")
    Call<JSONObject> cancelOrderByClientOid(@Path("underlying") String underlying,@Path("client_oid") String client_oid);



    //批量撤单
    @POST("/api/option/v3/cancel_batch_orders/{underlying}")
    Call<JSONObject> cancelBatchOrders(@Path("underlying") String underlying, @Body CancelOrders cancelOrders);

    //获取成交明细
    @GET("/api/option/v3/fills/{underlying}")
    Call<JSONArray> getFills(@Path("underlying") String underlying,
                             @Query("order_id") String order_id,
                             @Query("instrument_id") String instrument_id,
                             @Query("before") String before,
                             @Query("after") String after,
                             @Query("limit") String limit);



    //账单流水查询
    @GET("/api/option/v3/accounts/{underlying}/ledger")
    Call<JSONArray> getLedger(@Path("underlying") String underlying,
                              @Query("before") String before,
                              @Query("after") String after,
                              @Query("limit") String limit);

    //下单
    @POST("/api/option/v3/order")
    Call<JSONObject> getOrder(@Body OrderParam orderParam);

    //获取订单信息
    @GET("/api/option/v3/orders/{underlying}/{order_id}")
    Call<JSONObject> getOrderInfo(@Path("underlying") String underlying,
                                  @Path("order_id") String order_id);

    @GET("/api/option/v3/orders/{underlying}/{client_oid}")
    Call<JSONObject> getOrderInfoByClientOid(@Path("underlying") String underlying,
                                  @Path("client_oid") String client_oid);

    //获取订单列表
    @GET("/api/option/v3/orders/{underlying}")
    Call<JSONObject> getOrderList(@Path("underlying") String underlying,
                                  @Query("state") String state,
                                  @Query("instrument_id") String instrument_id,
                                  @Query("before") String before,
                                  @Query("after") String after,
                                  @Query("limit") String limit);

    //批量下单
    @POST("/api/option/v3/orders")
    Call<JSONObject> getOrders1(@Body OrderDataParam orderDataParam);



    //获取单个标的下的持仓信息
    @GET("/api/option/v3/{underlying}/position")
    Call<JSONObject> getPosition(@Path("underlying") String underlying,@Query("instrument_id") String instrument_id);

    //获取当前账户交易的手续费率
    @GET("/api/option/v3/trade_fee")
    Call<JSONObject> getTradeFee(@Query("category") String category);

}
