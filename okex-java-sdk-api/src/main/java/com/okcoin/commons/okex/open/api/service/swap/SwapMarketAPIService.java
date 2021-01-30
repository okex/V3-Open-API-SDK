package com.okcoin.commons.okex.open.api.service.swap;


public interface SwapMarketAPIService {

    //公共-获取合约信息
    String getContractsApi();

    //公共-获取深度数据
    String getDepthApi(String instrument_id, String depth, String size);

    //公共-获取全部ticker信息
    String getTickersApi();

    //公共-获取某个ticker信息
    String getTickerApi(String instrument_id);

    //公共-获取成交数据
    String getTradesApi(String instrument_id, String after, String before, String limit);

    //公共-获取K线数据
    String getCandlesApi(String instrument_id, String start, String end, String granularity);

    //公共-获取指数信息
    String getIndexApi(String instrument_id);

    //公共-获取法币汇率
    String getRateApi();

    //公共-获取平台总持仓量
    String getOpenInterestApi(String instrument_id);

    //公共-获取当前限价
    String getPriceLimitApi(String instrument_id);

    //公共-获取强平单
    String getLiquidationApi(String instrument_id, String status, String limit, String from, String to);

    //公共-获取合约资金费率
    String getFundingTimeApi(String instrument_id);

    //公共-获取合约标记价格
    String getMarkPriceApi(String instrument_id);

    //公共-获取合约历史资金费率
    String getHistoricalFundingRateApi(String instrument_id,String limit);

    //公共-获取历史K线数据
    String getHistoryCandlesApi(String instrument_id, String start, String end, String granularity, String limit);

}
