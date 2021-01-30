package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.*;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiOrderVO;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SwapTradeAPI {

    //下单
    @POST("/api/swap/v3/order")
    Call<Object> order(@Body PpOrder ppOrder);

    //批量下单
    @POST("/api/swap/v3/orders")
    Call<String> orders(@Body JSONObject ppOrders);

    //撤单(通过order_id)
    @POST("/api/swap/v3/cancel_order/{instrument_id}/{order_id}")
    Call<String> cancelOrderByOrderId(@Path("instrument_id") String instrument_id, @Path("order_id") String order_id);

        //撤单(通过client_oid)
    @POST("/api/swap/v3/cancel_order/{instrument_id}/{client_oid}")
    Call<String> cancelOrderByClientOid(@Path("instrument_id") String instrument_id, @Path("client_oid") String client_oid);

    //批量撤单
    @POST("/api/swap/v3/cancel_batch_orders/{instrument_id}")
    Call<String> cancelOrdersByOrderIds(@Path("instrument_id") String instrument_id, @Body JSONObject ppOrders);

    //批量撤单
    @POST("/api/swap/v3/cancel_batch_orders/{instrument_id}")
    Call<String> cancelOrdersByClientOids(@Path("instrument_id") String instrument_id, @Body JSONObject ppOrders);

    //修改订单(通过order_id)
    @POST("/api/swap/v3/amend_order/{instrument_id}")
    Call<String> amendOrderByOrderId(@Path("instrument_id") String instrument_id, @Body AmendOrder amendOrder);

    //修改订单(通过client_oid)
    @POST("/api/swap/v3/amend_order/{instrument_id}")
    Call<String> amendOrderByClientOid(@Path("instrument_id") String instrument_id, @Body AmendOrder amendOrder);

    //批量修改订单(通过order_id)
    @POST("/api/swap/v3/amend_batch_orders/{instrument_id}")
    Call<String> amendBatchOrderByOrderId(@Path("instrument_id") String instrument_id, @Body AmendOrderParam amendOrder);

    //批量修改订单(通过client_oid)
    @POST("/api/swap/v3/amend_batch_orders/{instrument_id}")
    Call<String> amendBatchOrderByClientOid(@Path("instrument_id") String instrument_id, @Body AmendOrderParam amendOrder);

    //委托策略下单
    @POST("/api/swap/v3/order_algo")
    Call<String> swapOrderAlgo(@Body SwapOrderParam swapOrderParam);

    //委托策略撤单
    @POST("/api/swap/v3/cancel_algos")
    Call<String> cancelOrderAlgo(@Body CancelOrderAlgo cancelOrderAlgo);

    //获取委托单列表
    @GET("/api/swap/v3/order_algo/{instrument_id}")
    Call<String> getSwapOrders(@Path("instrument_id") String instrument_id,
                               @Query("order_type") String order_type,
                               @Query("status") String status,
                               @Query("algo_id") String algo_id,
                               @Query("before") String before,
                               @Query("after") String after,
                               @Query("limit") String limit);

    //市价全平
    @POST("/api/swap/v3/close_position")
    Call<String> closePosition(@Body ClosePosition closePosition);

    //撤销所有平仓挂单
    @POST("/api/swap/v3/cancel_all")
    Call<String> CancelAll(@Body CancelAllParam cancelAllParam);

}
