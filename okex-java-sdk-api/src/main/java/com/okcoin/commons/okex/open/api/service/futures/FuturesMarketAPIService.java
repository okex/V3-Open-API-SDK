package com.okcoin.commons.okex.open.api.service.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;

import java.util.List;

/**
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 16:06
 */
public interface FuturesMarketAPIService {

    //公共-获取合约信息
    List<Instruments> getInstruments();

    //公共-获取深度数据
    Book getInstrumentBook(String instrument_id, String size,String depth);

    //公共-获取全部ticker信息
    List<Ticker> getAllInstrumentTicker();

    //公共-获取某个ticker信息
    Ticker getInstrumentTicker(String instrument_id);

    //公共-获取成交数据
    List<Trades> getInstrumentTrades(String instrument_id , String after, String before, String limit);

    //公共-获取K线数据
    JSONArray getInstrumentCandles(String instrument_id, String start, String end, String granularity);

    //公共-获取指数信息
    Index getInstrumentIndex(String instrument_id);

    //公共-获取法币汇率
    ExchangeRate getExchangeRate();

    //公共-获取预估交割价
    EstimatedPrice getInstrumentEstimatedPrice(String instrument_id);

    //公共-获取平台总持仓量
    Holds getInstrumentHolds(String instrument_id);

    //公共-获取当前限价
    PriceLimit getInstrumentPriceLimit(String instrument_id);

    //公共-获取标记价格
    JSONObject getMarkPrice(String instrument_id);

    //公共-获取强平单
    List<Liquidation> getInstrumentLiquidation(String instrument_id, String status, String limit, String from, String to);

    //公共-获取历史结算/交割记录
    JSONArray getSettlementHistory (String instrument_id, String underlying, String start, String limit, String end);

    //获取历史K线数据
    JSONArray getHistoryCandels(String instrument_id, String start, String end, String granularity,String limit);

}
