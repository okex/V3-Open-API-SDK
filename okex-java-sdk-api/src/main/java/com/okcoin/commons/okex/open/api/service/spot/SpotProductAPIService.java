package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SpotProductAPIService {

    //公共-获取币对信息
    List<Product> getInstruments();

    //公共-获取深度数据
    Book bookProductsByInstrumentId(String instrument_id, String size, String depth);

    //公共-获取全部ticker信息
    List<Ticker> getTickers();

    //公共-获取某个ticker信息
    Ticker getTickerByInstrumentId(String instrument_id);

    //公共-获取成交数据
    List<Trade> getTradesByInstrumentId(String instrument_id, String limit);

    //公共-获取K线数据
    JSONArray getCandlesByInstrumentId(String instrument_id, String start, String end, String granularity);

    //公共-获取历史K线数据
    JSONArray getHistoryCandlesByInstrumentId(String instrument_id, String start, String end, String granularity, String limit);

}
