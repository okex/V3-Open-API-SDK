package com.okcoin.commons.okex.open.api.service.information;

import com.alibaba.fastjson.JSONArray;
import retrofit2.Call;

public interface InformationMarketAPIService {

    //公共-多空持仓人数比
    JSONArray getLongShortRatio(String currency, String start, String end, String granularity);

    //公共-持仓总量及交易量
    JSONArray getVolume(String currency, String start, String end, String granularity);

    //公共-主动买入卖出情况
    JSONArray getTaker(String currency, String start, String end, String granularity);

    //公共-多空精英趋向指标
    JSONArray getSentiment(String currency, String start, String end, String granularity);

    //公共-多空精英平均持仓比例
    JSONArray getMargin(String currency, String start, String end, String granularity);

}
