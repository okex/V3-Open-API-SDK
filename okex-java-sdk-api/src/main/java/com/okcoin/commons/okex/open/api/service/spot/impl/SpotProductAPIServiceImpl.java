package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.SpotProductAPIService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 公共数据相关接口
 *
 **/
public class SpotProductAPIServiceImpl implements SpotProductAPIService {

    private final APIClient client;
    private final SpotProductAPI spotProductAPI;

    public SpotProductAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.spotProductAPI = this.client.createService(SpotProductAPI.class);
    }

    //公共-获取币对信息
    @Override
    public List<Product> getInstruments() {
        return this.client.executeSync(this.spotProductAPI.getInstruments());
    }

    //公共-获取深度数据
    @Override
    public Book bookProductsByInstrumentId(final String instrument_id, final String size, final  String depth) {
        return this.client.executeSync(this.spotProductAPI.bookProductsByInstrumentId(instrument_id, size, depth));
    }

    //公共-获取全部ticker信息
    @Override
    public List<Ticker> getTickers() {
        return this.client.executeSync(this.spotProductAPI.getTickers());
    }

    //公共-获取某个ticker信息
    @Override
    public Ticker getTickerByInstrumentId(final String instrument_id) {
        return this.client.executeSync(this.spotProductAPI.getTickerByInstrumentId(instrument_id));
    }

    //公共-获取成交数据
    @Override
    public List<Trade> getTradesByInstrumentId(final String instrument_id,final String limit) {
        return this.client.executeSync(this.spotProductAPI.getTradesByInstrumentId(instrument_id,limit));
    }

    //公共-获取K线数据
    @Override
    public JSONArray getCandlesByInstrumentId(final String instrument_id, final String start, final String end, final String granularity) {
        return this.client.executeSync(this.spotProductAPI.getCandlesByInstrumentId(instrument_id,start, end, granularity));
    }

    //公共-获取历史K线数据
    @Override
    public JSONArray getHistoryCandlesByInstrumentId(String instrument_id, String start, String end, String granularity, String limit) {
        return this.client.executeSync(this.spotProductAPI.getHistoryCandlesByInstrumentId(instrument_id, start, end, granularity, limit));
    }

}
