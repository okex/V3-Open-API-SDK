package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;


interface FuturesMarketAPI {

    //公共-获取合约信息
    @GET("/api/futures/v3/instruments")
    Call<List<Instruments>> getInstruments();

    //公共-获取深度数据
    @GET("/api/futures/v3/instruments/{instrument_id}/book")
    Call<Book> getInstrumentBook(@Path("instrument_id") String instrument_id, @Query("size") String size,@Query("depth") String depth);

    //公共-获取全部ticker信息
    @GET("/api/futures/v3/instruments/ticker")
    Call<List<Ticker>> getAllInstrumentTicker();

    //公共-获取某个ticker信息
    @GET("/api/futures/v3/instruments/{instrument_id}/ticker")
    Call<Ticker> getInstrumentTicker(@Path("instrument_id") String instrument_id);

    //公共-获取成交数据
    @GET("/api/futures/v3/instruments/{instrument_id}/trades")
    Call<List<Trades>> getInstrumentTrades(@Path("instrument_id") String instrument_id, @Query("after") String after, @Query("before") String before, @Query("limit") String limit);

    //公共-获取K线数据
    @GET("/api/futures/v3/instruments/{instrument_id}/candles")
    Call<JSONArray> getInstrumentCandles(@Path("instrument_id") String instrument_id, @Query("start") String start, @Query("end") String end, @Query("granularity") String granularity);

    //公共-获取指数信息
    @GET("/api/futures/v3/instruments/{instrument_id}/index")
    Call<Index> getInstrumentIndex(@Path("instrument_id") String instrument_id);

    //公共-获取法币汇率
    @GET("/api/futures/v3/rate")
    Call<ExchangeRate> getExchangeRate();

    //公共-获取预估交割价
    @GET("/api/futures/v3/instruments/{instrument_id}/estimated_price")
    Call<EstimatedPrice> getInstrumentEstimatedPrice(@Path("instrument_id") String instrument_id);

    //公共-获取平台总持仓量
    @GET("/api/futures/v3/instruments/{instrument_id}/open_interest")
    Call<Holds> getInstrumentHolds(@Path("instrument_id") String instrument_id);

    //公共-获取当前限价
    @GET("/api/futures/v3/instruments/{instrument_id}/price_limit")
    Call<PriceLimit> getInstrumentPriceLimit(@Path("instrument_id") String instrument_id);

    //公共-获取标记价格
    @GET("/api/futures/v3/instruments/{instrument_id}/mark_price")
    Call<JSONObject> getMarkPrice(@Path("instrument_id") String instrument_id);

    //公共-获取强平单
    @GET("/api/futures/v3/instruments/{instrument_id}/liquidation")
    Call<List<Liquidation>> getInstrumentLiquidation(@Path("instrument_id") String instrument_id,
                                                     @Query("status") String status,
                                                     @Query("limit") String limit,
                                                     @Query("from") String from,
                                                     @Query("to") String to);

    //公共-获取历史结算/交割记录
    @GET("/api/futures/v3/settlement/history")
    Call<JSONArray> getSettlementHistory(@Query("instrument_id") String instrument_id,
                                         @Query("underlying") String underlying,
                                         @Query("start") String start,
                                         @Query("limit") String limit,
                                         @Query("end") String end);

    //公共-获取历史K线数据
    @GET("/api/futures/v3/instruments/{instrument_id}/history/candles")
    Call<JSONArray> getHistoryCandels(@Path("instrument_id") String instrument_id, @Query("start") String start, @Query("end") String end, @Query("granularity") String granularity, @Query("limit") String limit);








    //获取服务时间
    @GET("/api/general/v3/time")
    Call<ServerTime> getServerTime();




  /*  @GET("/api/futures/v3/instruments/currencies")
    Call<List<Currencies>> getCurrencies();*/




















}
