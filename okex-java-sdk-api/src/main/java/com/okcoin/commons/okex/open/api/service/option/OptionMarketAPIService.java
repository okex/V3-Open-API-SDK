package com.okcoin.commons.okex.open.api.service.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import retrofit2.http.Query;

public interface OptionMarketAPIService {

    //公共-获取标的指数
    JSONArray getUnderlying();

    //公共-获取期权合约
    JSONArray getInstruments(String underlying,String delivery,String instrument_id);

    //公共-获取期权合约详细定价
    JSONArray getAllSummary(String underlying,String delivery);

    //公共-获取单个期权合约详细定价
    JSONObject getDetailPrice(String underlying,String instrument_id);

    //公共-获取深度数据
    JSONObject getDepthData(String instrument_id,String size);

    //公共-获取成交数据
    JSONArray getTradeList(String instrument_id, String after, String before, String limit);

    //公共-获取某个Ticker信息
    JSONObject getTicker(String instrument_id);

    //公共-获取K线数据
    JSONArray getCandles(String instrument_id,String start,String end,String granularity);

    //公共-获取历史结算/行权记录
    JSONArray getHistorySettlement(String underlying,String start,String limit,String end);
}
