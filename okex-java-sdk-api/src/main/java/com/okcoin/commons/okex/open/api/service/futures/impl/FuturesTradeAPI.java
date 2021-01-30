package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.*;
import com.okcoin.commons.okex.open.api.bean.futures.result.CancelFuturesOrder;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


interface FuturesTradeAPI {

    //所有合约持仓信息
    @GET("/api/futures/v3/position")
    Call<JSONObject> getPositions();

    //单个合约持仓信息
    @GET("/api/futures/v3/{instrument_id}/position")
    Call<JSONObject> getPositionByInstrumentId(@Path("instrument_id") String instrument_id);

    //所有币种合约账户信息
    @GET("/api/futures/v3/accounts")
    Call<JSONObject> getAccounts();

    //单个币种合约账户信息
    @GET("/api/futures/v3/accounts/{underlying}")
    Call<JSONObject> getAccountsByUnderlying(@Path("underlying") String underlying);

    //获取合约币种杠杆倍数
    @GET("/api/futures/v3/accounts/{underlying}/leverage")
    Call<JSONObject> getLeverage(@Path("underlying") String underlying);

    //设定合约币种杠杆倍数(逐仓)
    @POST("/api/futures/v3/accounts/{underlying}/leverage")
    Call<JSONObject> setLeverageOnFixed(@Path("underlying") String underlying,
                                           @Body JSONObject changeLeverage);

    //设定合约币种杠杆倍数(全仓)
    @POST("/api/futures/v3/accounts/{underlying}/leverage")
    Call<JSONObject> setLeverageOnCross(@Path("underlying") String underlying,
                                           @Body JSONObject changeLeverage);

    //账单流水查询
    @GET("/api/futures/v3/accounts/{underlying}/ledger")
    Call<JSONArray> getAccountsLedgerByUnderlying(@Path("underlying") String underlying,
                                                @Query("after") String after,
                                                @Query("before") String before,
                                                @Query("limit") String limit,
                                                @Query("type") String type);

    //下单
    @POST("/api/futures/v3/order")
    Call<OrderResult> order(@Body JSONObject order);

    //批量下单
    @POST("/api/futures/v3/orders")
    Call<JSONObject> orders(@Body JSONObject orders);

    //撤销指定订单（通过order_id）
    @POST("/api/futures/v3/cancel_order/{instrument_id}/{order_id}")
    Call<JSONObject> cancelOrderByOrderId(@Path("instrument_id") String instrument_id, @Path("order_id") String order_id);

    //撤销指定订单（通过client_oid）
    @POST("/api/futures/v3/cancel_order/{instrument_id}/{client_oid}")
    Call<JSONObject> cancelOrderByClientOid(@Path("instrument_id") String instrument_id, @Path("client_oid") String client_oid);

    //批量撤销订单(通过order_id)
    @POST("/api/futures/v3/cancel_batch_orders/{instrument_id}")
    Call<JSONObject> cancelOrdersByOrderId(@Path("instrument_id") String instrumentId, @Body JSONObject order_ids);

    //批量撤销订单(通过client_oid)
    @POST("/api/futures/v3/cancel_batch_orders/{instrument_id}")
    Call<JSONObject> cancelOrdersByClientOid(@Path("instrument_id") String instrumentId, @Body JSONObject client_oids);

    //修改订单(通过order_id)
    @POST("/api/futures/v3/amend_order/{instrument_id}")
    Call<JSONObject> amendOrderByOrderId(@Path("instrument_id") String instrument_id, @Body AmendOrder amendorder);

    //修改订单(通过client_oid)
    @POST("/api/futures/v3/amend_order/{instrument_id}")
    Call<JSONObject> amendOrderByClientOid(@Path("instrument_id") String instrument_id, @Body AmendOrder amendorder);

    //批量修改订单(通过order_id)
    @POST("/api/futures/v3/amend_batch_orders/{instrument_id}")
    Call<JSONObject> amendBatchOrdersByOrderId(@Path("instrument_id") String instrument_id, @Body AmendDateParam amendOrder);

    //批量修改订单(通过client_oid)
    @POST("/api/futures/v3/amend_batch_orders/{instrument_id}")
    Call<JSONObject> amendBatchOrdersByClientOid(@Path("instrument_id") String instrument_id, @Body AmendDateParam amendOrder);

    //获取订单列表
    @GET("/api/futures/v3/orders/{instrument_id}")
    Call<JSONObject> getOrders(@Path("instrument_id") String instrumentId, @Query("state") String state,
                               @Query("after") String after, @Query("before") String before, @Query("limit") String limit);
    //获取订单信息（通过orderId）
    @GET("/api/futures/v3/orders/{instrument_id}/{order_id}")
    Call<JSONObject> getOrderByOrderId(@Path("instrument_id") String instrument_id, @Path("order_id") String order_id);

    //获取订单信息（通过client_oid）
    @GET("/api/futures/v3/orders/{instrument_id}/{client_oid}")
    Call<JSONObject> getOrderByClientOid(@Path("instrument_id") String instrument_id, @Path("client_oid") String client_oid);

    //获取成交明细
    @GET("/api/futures/v3/fills")
    Call<JSONArray> getFills( @Query("order_id") String order_id, @Query("instrument_id") String instrument_id,
                              @Query("after") String after, @Query("before") String before,  @Query("limit") String limit);

    //设置合约币种账户模式
    @POST("/api/futures/v3/accounts/margin_mode")
    Call<JSONObject> changeMarginMode(@Body JSONObject changeMarginMode);

    //市价全平
    @POST("/api/futures/v3/close_position")
    Call<JSONObject> closePositions(@Body JSONObject closePositions);

    //撤销所有平仓挂单
    @POST("/api/futures/v3/cancel_all")
    Call<JSONObject> cancelAll(@Body JSONObject cancelAll);

    //获取合约挂单冻结数量
    @GET("/api/futures/v3/accounts/{instrument_id}/holds")
    Call<JSONObject> getAccountsHoldsByInstrumentId(@Path("instrument_id") String instrumentId);

    //委托策略下单
    @POST("api/futures/v3/order_algo")
    Call<FuturesOrderResult> futuresOrder(@Body FuturesOrderParam futuresOrderParam);

    //委托策略撤单
    @POST("api/futures/v3/cancel_algos")
    Call<CancelFuturesOrdeResult> cancelFuturesOrder(@Body CancelFuturesOrder cancelFuturesOrder);

    //获取委托单列表
    @GET("api/futures/v3/order_algo/{instrument_id}")
    Call<String> findFuturesOrder(@Path("instrument_id") String instrument_id,
                                  @Query("order_type") String order_type,
                                  @Query("status") String status,
                                  @Query("algo_id") String algo_id,
                                  @Query("before") String before,
                                  @Query("after") String after,
                                  @Query("limit") String limit);

    //获取当前手续费费率
    @GET("/api/futures/v3/trade_fee")
    Call<JSONObject> getTradeFee(@Query("category") String category,
                                 @Query("underlying") String underlying);

    //增加/减少保证金
    @POST("/api/futures/v3/position/margin")
    Call<JSONObject> modifyMargin(@Body ModifyMarginParam modifyMarginParam);

    //设置逐仓自动追加保证金
    @POST("/api/futures/v3/accounts/auto_margin")
    Call<JSONObject> modifyFixedMargin(@Body ModifyFixedMargin modifyFixedMargin);

}