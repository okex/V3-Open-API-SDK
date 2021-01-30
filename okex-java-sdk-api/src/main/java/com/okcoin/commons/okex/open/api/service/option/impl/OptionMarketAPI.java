package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OptionMarketAPI {

    //公共-获取标的指数
    @GET("/api/option/v3/underlying")
    Call<JSONArray> getUnderlying();

    //公共-获取期权合约
    @GET("/api/option/v3/instruments/{underlying}")
    Call<JSONArray> getInstruments(@Path("underlying") String underlying,
                                   @Query("delivery") String delivery,
                                   @Query("instrument_id") String instrument_id);

    //公共-获取期权合约详细定价
    @GET("/api/option/v3/instruments/{underlying}/summary")
    Call<JSONArray> getAllSummary(@Path("underlying") String underlying,
                                  @Query("delivery") String delivery);

    //公共-获取单个期权合约详细定价
    @GET("/api/option/v3/instruments/{underlying}/summary/{instrument_id}")
    Call<JSONObject> getDetailPrice(@Path("underlying") String underlying,
                                    @Path("instrument_id") String instrument_id);

    //公共-获取深度数据
    @GET("/api/option/v3/instruments/{instrument_id}/book")
    Call<JSONObject> getDepthData(@Path("instrument_id") String instrument_id, @Query("size") String size);

    //公共-获取成交数据
    @GET("/api/option/v3/instruments/{instrument_id}/trades")
    Call<JSONArray> getTradeList(@Path("instrument_id") String instrument_id,
                                 @Query("after") String after,
                                 @Query("before") String before,
                                 @Query("limit") String limit);

    //公共-获取某个Ticker信息
    @GET("/api/option/v3/instruments/{instrument_id}/ticker")
    Call<JSONObject> getTicker(@Path("instrument_id") String instrument_id);

    //公共-获取K线数据
    @GET("/api/option/v3/instruments/{instrument_id}/candles")
    Call<JSONArray> getCandles(@Path("instrument_id") String instrument_id,
                               @Query("start") String start,
                               @Query("end") String end,
                               @Query("granularity") String granularity);

    //公共-获取历史结算/行权记录
    @GET("/api/option/v3/settlement/history/{underlying}")
    Call<JSONArray> getHistorySettlement(@Path("underlying") String underlying,
                                         @Query("start") String start,
                                         @Query("limit") String limit,
                                         @Query("end") String end);

}
