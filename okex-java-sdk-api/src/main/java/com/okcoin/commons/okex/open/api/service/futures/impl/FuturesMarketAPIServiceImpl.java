package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;

import java.util.List;

/**
 * Futures market api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 16:09
 */
public class FuturesMarketAPIServiceImpl implements FuturesMarketAPIService {

    private APIClient client;
    private FuturesMarketAPI api;

    public FuturesMarketAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(FuturesMarketAPI.class);
    }

    //公共-获取合约信息
    @Override
    public List<Instruments> getInstruments() {
        return this.client.executeSync(this.api.getInstruments());
    }

    //公共-获取深度数据
    @Override
    public Book getInstrumentBook(String instrument_id, String size, String depth) {
        return this.client.executeSync(this.api.getInstrumentBook(instrument_id, size, depth));
    }

    //公共-获取全部ticker信息
    @Override
    public List<Ticker> getAllInstrumentTicker() {
        return this.client.executeSync(this.api.getAllInstrumentTicker());
    }

    //公共-获取某个ticker信息
    @Override
    public Ticker getInstrumentTicker(String instrument_id) {
        return this.client.executeSync(this.api.getInstrumentTicker(instrument_id));
    }

    //公共-获取成交数据
    @Override
    public List<Trades> getInstrumentTrades(String instrument_id, String after, String before, String limit) {
        return this.client.executeSync(this.api.getInstrumentTrades(instrument_id, after, before, limit));
    }

    //公共-获取K线数据
    @Override
    public JSONArray getInstrumentCandles(String instrument_id, String start, String end, String granularity) {
        return this.client.executeSync(this.api.getInstrumentCandles(instrument_id, start, end, granularity));
    }

    //公共-获取指数信息
    @Override
    public Index getInstrumentIndex(String instrument_id) {
        return this.client.executeSync(this.api.getInstrumentIndex(instrument_id));
    }

    //公共-获取法币汇率
    @Override
    public ExchangeRate getExchangeRate() {
        return this.client.executeSync(this.api.getExchangeRate());
    }

    //公共-获取预估交割价
    @Override
    public EstimatedPrice getInstrumentEstimatedPrice(String instrument_id) {
        return this.client.executeSync(this.api.getInstrumentEstimatedPrice(instrument_id));
    }

    //公共-获取平台总持仓量
    @Override
    public Holds getInstrumentHolds(String instrument_id) {
        return this.client.executeSync(this.api.getInstrumentHolds(instrument_id));
    }

    //公共-获取当前限价
    @Override
    public PriceLimit getInstrumentPriceLimit(String instrument_id) {
        return this.client.executeSync(this.api.getInstrumentPriceLimit(instrument_id));
    }

    //公共-获取标记价格
    @Override
    public JSONObject getMarkPrice(String instrumentId){
        return this.client.executeSync(this.api.getMarkPrice(instrumentId));
    }

    //公共-获取强平单
    @Override
    public List<Liquidation> getInstrumentLiquidation(String instrument_id, String status, String limit, String from, String to) {
        return this.client.executeSync(this.api.getInstrumentLiquidation(instrument_id, status, limit,  from,  to));
    }

    //公共-获取历史结算/交割记录
    @Override
    public JSONArray getSettlementHistory(String instrument_id, String underlying, String start, String limit, String end) {
        return this.client.executeSync(this.api.getSettlementHistory(instrument_id, underlying, start, limit, end));
    }

    //公共-获取历史K线数据
    @Override
    public JSONArray getHistoryCandels(String instrument_id, String start, String end, String granularity, String limit) {
        return this.client.executeSync(this.api.getHistoryCandels(instrument_id, start, end, granularity, limit));
    }

}
