package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SwapUserAPI {

    //所有合约持仓信息
    @GET("/api/swap/v3/position")
    //Call<JSONObject> getPositions();
    Call<String> getPositions();


    @GET("/api/swap/v3/{instrument_id}/position")
    Call<String> getPosition(@Path("instrument_id") String instrument_id);

    @GET("/api/swap/v3/accounts")
    Call<String> getAccounts();

    @GET("/api/swap/v3/{instrument_id}/accounts")
    Call<String> selectAccount(@Path("instrument_id") String instrument_id);

    @GET("/api/swap/v3/accounts/{instrument_id}/settings")
    Call<String> selectContractSettings(@Path("instrument_id") String instrument_id);
    /*//设定合约杠杆倍数
    @POST("/api/swap/v3/accounts/{instrument_id}/leverage")
    Call<String> updateLevelRate(@Path("instrument_id") String instrument_id, @Body JSONObject levelRateParam);*/
    //设定合约杠杆倍数
    @POST("/api/swap/v3/accounts/{instrument_id}/leverage")
    Call<String> updateLevelRate(@Path("instrument_id") String instrument_id, @Body JSONObject levelRateParam);
    //获取所有订单列表
    @GET("/api/swap/v3/orders/{instrument_id}")
    Call<String> selectOrders(@Path("instrument_id") String instrument_id, @Query("state") String state, @Query("before") String before, @Query("after") String after, @Query("limit") String limit);
    //获取订单信息
    @GET("/api/swap/v3/orders/{instrument_id}/{order_id}")
    Call<String> selectOrderByOrderId(@Path("instrument_id") String instrument_id, @Path("order_id") String order_id);
    @GET("/api/swap/v3/orders/{instrument_id}/{client_oid}")
    Call<String> selectOrderByClientOid(@Path("instrument_id") String instrument_id, @Path("client_oid") String client_oid);

    //获取成交明细接口
    @GET("/api/swap/v3/fills")
    Call<String> selectDealDetail(@Query("instrument_id") String instrument_id, @Query("order_id") String order_id, @Query("before") String before, @Query("after") String after, @Query("limit") String limit);
    //账单流水查询
    @GET("/api/swap/v3/accounts/{instrument_id}/ledger")
    Call<String> getLedger(@Path("instrument_id") String instrument_id, @Query("before") String before, @Query("after") String after, @Query("limit") String limit,@Query("type") String type);

    @GET("/api/swap/v3/accounts/{instrument_id}/holds")
    Call<String> getHolds(@Path("instrument_id") String instrument_id);
    //当前账户交易手续等级的费率
    @GET("/api/swap/v3/trade_fee")
    Call<String> getTradeFee(@Query("category") String category);


}
