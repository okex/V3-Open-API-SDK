package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.http.*;

public interface SwapUserAPI {

    //所有合约持仓信息
    @GET("/api/swap/v3/position")
    Call<String> getPositions();

    //单个合约持仓信息
    @GET("/api/swap/v3/{instrument_id}/position")
    Call<String> getPosition(@Path("instrument_id") String instrument_id);

    //所有币种合约账户信息
    @GET("/api/swap/v3/accounts")
    Call<String> getAccounts();

    //单个币种合约账户信息
    @GET("/api/swap/v3/{instrument_id}/accounts")
    Call<String> selectAccount(@Path("instrument_id") String instrument_id);

    //获取某个合约的用户配置
    @GET("/api/swap/v3/accounts/{instrument_id}/settings")
    Call<String> selectContractSettings(@Path("instrument_id") String instrument_id);

    //设定某个合约的杠杆
    @POST("/api/swap/v3/accounts/{instrument_id}/leverage")
    Call<String> updateLevelRate(@Path("instrument_id") String instrument_id, @Body JSONObject levelRateParam);

    //账单流水查询
    @GET("/api/swap/v3/accounts/{instrument_id}/ledger")
    Call<String> getLedger(@Path("instrument_id") String instrument_id, @Query("before") String before, @Query("after") String after, @Query("limit") String limit,@Query("type") String type);

    //获取所有订单列表
    @GET("/api/swap/v3/orders/{instrument_id}")
    Call<String> selectOrders(@Path("instrument_id") String instrument_id, @Query("after") String after, @Query("before") String before, @Query("limit") String limit,@Query("state") String state);

    //获取订单信息(通过order_id)
    @GET("/api/swap/v3/orders/{instrument_id}/{order_id}")
    Call<String> selectOrderByOrderId(@Path("instrument_id") String instrument_id, @Path("order_id") String order_id);

    //获取订单信息(通过client_oid)
    @GET("/api/swap/v3/orders/{instrument_id}/{client_oid}")
    Call<String> selectOrderByClientOid(@Path("instrument_id") String instrument_id, @Path("client_oid") String client_oid);

    //获取成交明细
    @GET("/api/swap/v3/fills")
    Call<String> selectDealDetail(@Query("instrument_id") String instrument_id, @Query("order_id") String order_id, @Query("before") String before, @Query("after") String after, @Query("limit") String limit);

    //获取合约挂单冻结数量
    @GET("/api/swap/v3/accounts/{instrument_id}/holds")
    Call<String> getHolds(@Path("instrument_id") String instrument_id);

    //当前账户交易手续等级的费率
    @GET("/api/swap/v3/trade_fee")
    Call<String> getTradeFee(@Query("category") String category, @Query("instrument_id") String instrument_id);

}
