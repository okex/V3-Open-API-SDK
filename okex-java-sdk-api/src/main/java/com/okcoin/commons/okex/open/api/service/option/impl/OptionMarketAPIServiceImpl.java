package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.option.OptionMarketAPIService;

public class OptionMarketAPIServiceImpl implements OptionMarketAPIService {
    private APIClient client;
    private OptionMarketAPI api;

    public OptionMarketAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(OptionMarketAPI.class);
    }

    //公共-获取标的指数
    @Override
    public JSONArray getUnderlying() {
        return this.client.executeSync(this.api.getUnderlying());
    }

    //公共-获取期权合约
    @Override
    public JSONArray getInstruments(String underlying, String delivery, String instrument_id) {
        return this.client.executeSync(this.api.getInstruments(underlying,delivery,instrument_id));
    }

    //公共-获取期权合约详细定价
    @Override
    public JSONArray getAllSummary(String underlying, String delivery) {
        return this.client.executeSync(this.api.getAllSummary(underlying,delivery));
    }

    //公共-获取单个期权合约详细定价
    @Override
    public JSONObject getDetailPrice(String underlying, String instrument_id) {
        return this.client.executeSync(this.api.getDetailPrice(underlying, instrument_id));
    }

    //公共-获取深度数据
    @Override
    public JSONObject getDepthData(String instrument_id, String size) {
        return this.client.executeSync(this.api.getDepthData(instrument_id,size));
    }

    //公共-获取成交数据
    @Override
    public JSONArray getTradeList(String instrument_id, String after, String before, String limit) {
        return this.client.executeSync(this.api.getTradeList(instrument_id,after,before,limit));
    }

    //公共-获取某个Ticker信息
    @Override
    public JSONObject getTicker(String instrument_id) {
        return this.client.executeSync(this.api.getTicker(instrument_id));
    }

    //公共-获取K线数据
    @Override
    public JSONArray getCandles(String instrument_id,String start,String end,String granularity) {
        return this.client.executeSync(this.api.getCandles(instrument_id,start,end,granularity));
    }

    //公共-获取历史结算/行权记录
    @Override
    public JSONArray getHistorySettlement(String underlying,String start,String limit,String end) {
        return this.client.executeSync(this.api.getHistorySettlement(underlying,start,limit,end));
    }


}
