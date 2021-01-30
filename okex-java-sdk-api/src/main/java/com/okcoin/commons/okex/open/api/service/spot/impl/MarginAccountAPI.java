package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.MarginLeverage;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * 杠杆账号测试
 */
public interface MarginAccountAPI {

    //币币杠杆账户信息
    @GET("/api/margin/v3/accounts")
    Call<List<Map<String, Object>>> getAccounts();

    //单一币对账户信息
    @GET("/api/margin/v3/accounts/{instrument_id}")
    Call<Map<String, Object>> getAccountsByInstrumentId(@Path("instrument_id") final String instrument_id);

    //账单流水查询
    @GET("/api/margin/v3/accounts/{instrument_id}/ledger")
    Call<List<UserMarginBillDto>> getLedger(@Path("instrument_id") final String instrument_id,
                                            @Query("after") final String after,
                                            @Query("before") final String before,
                                            @Query("limit") String limit,
                                            @Query("type") final String type);

    //杠杆配置信息
    @GET("/api/margin/v3/accounts/availability")
    Call<List<Map<String, Object>>> getAvailability();

    //某个杠杆配置信息
    @GET("/api/margin/v3/accounts/{instrument_id}/availability")
    Call<List<Map<String, Object>>> getAvailabilityByInstrumentId(@Path("instrument_id") final String instrument_id);

    //获取借币记录
    @GET("/api/margin/v3/accounts/borrowed")
    Call<List<MarginBorrowOrderDto>> getBorrowedAccounts(@Query("status") final String status,
                                            @Query("after") final String after,
                                            @Query("before") final String before,
                                            @Query("limit") String limit);

    //某币对借币记录
    @GET("/api/margin/v3/accounts/{instrument_id}/borrowed")
    Call<List<MarginBorrowOrderDto>> getBorrowedAccountsByInstrumentId(@Path("instrument_id") final String instrument_id,
                                                                    @Query("status") final String status,
                                                                    @Query("after") final String after,
                                                                    @Query("before") final String before,
                                                                    @Query("limit") final String limit);

    //借币
    @POST("/api/margin/v3/accounts/borrow")
    Call<BorrowResult> borrow(@Body BorrowRequestDto param);

    //还币
    @POST("/api/margin/v3/accounts/repayment")
    Call<RepaymentResult> repayment(@Body RepaymentRequestDto param);

    //获取杠杆倍数
    @GET("/api/margin/v3/accounts/{instrument_id}/leverage")
    Call<JSONObject> getLeverage(@Path("instrument_id") String instrument_id);

    //设置杠杆倍数
    @POST("/api/margin/v3/accounts/{instrument_id}/leverage")
    Call<JSONObject> setLeverage(@Path("instrument_id") String instrument_id, @Body MarginLeverage marginLeverage);

}
