package com.okcoin.commons.okex.open.api.service.information.impl;

import com.alibaba.fastjson.JSONArray;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.information.InformationMarketAPIService;

public class InformationMarketAPIServiceImpl implements InformationMarketAPIService {
    private APIClient client;
    private InformationMarketAPI api;

    public InformationMarketAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(InformationMarketAPI.class);
    }

    //公共-多空持仓人数比
    @Override
    public JSONArray getLongShortRatio(String currency, String start, String end, String granularity) {
        return this.client.executeSync(this.api.getLongShortRatio(currency,start,end,granularity));
    }

    //公共-持仓总量及交易量
    @Override
    public JSONArray getVolume(String currency, String start, String end, String granularity) {
        return this.client.executeSync(this.api.getVolume(currency,start,end,granularity));
    }

    //公共-主动买入卖出情况
    @Override
    public JSONArray getTaker(String currency, String start, String end, String granularity) {
        return this.client.executeSync(this.api.getTaker(currency,start,end,granularity));
    }

    //公共-多空精英趋向指标
    @Override
    public JSONArray getSentiment(String currency, String start, String end, String granularity) {
        return this.client.executeSync(this.api.getSentiment(currency, start, end, granularity));
    }

    //公共-多空精英平均持仓比例
    @Override
    public JSONArray getMargin(String currency, String start, String end, String granularity) {
        return this.client.executeSync(this.api.getMargin(currency, start, end, granularity));
    }
}
