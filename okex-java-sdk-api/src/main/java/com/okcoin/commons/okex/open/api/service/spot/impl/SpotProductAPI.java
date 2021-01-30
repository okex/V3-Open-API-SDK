package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SpotProductAPI {

    //公共-获取币对信息
    @GET("/api/spot/v3/instruments")
    Call<List<Product>> getInstruments();

    //公共-获取深度数据
    @GET("/api/spot/v3/instruments/{instrument_id}/book")
    Call<Book> bookProductsByInstrumentId(@Path("instrument_id") String instrument_id,
                                          @Query("size") String size,
                                          @Query("depth") String depth);

    //公共-获取全部ticker信息
    @GET("/api/spot/v3/instruments/ticker")
    Call<List<Ticker>> getTickers();

    //公共-获取某个ticker信息
    @GET("/api/spot/v3/instruments/{instrument_id}/ticker")
    Call<Ticker> getTickerByInstrumentId(@Path("instrument_id") String instrument_id);

    //公共-获取成交数据
    @GET("/api/spot/v3/instruments/{instrument_id}/trades")
    Call<List<Trade>> getTradesByInstrumentId(@Path("instrument_id") String instrument_id,
                                @Query("limit") String limit);

    //公共-获取K线数据
    @GET("/api/spot/v3/instruments/{instrument_id}/candles")
    Call<JSONArray> getCandlesByInstrumentId(@Path("instrument_id") String instrument_id,
                               @Query("start") String start,
                               @Query("end") String end,
                               @Query("granularity") String granularity);

    //公共-获取历史K线数据
    @GET("/api/spot/v3/instruments/{instrument_id}/history/candles")
    Call<JSONArray> getHistoryCandlesByInstrumentId(@Path("instrument_id") String instrument_id,
                                      @Query("start") String start,
                                      @Query("end") String end,
                                      @Query("granularity") String granularity,
                                      @Query("limit") String limit);

}
