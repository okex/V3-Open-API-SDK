package com.okcoin.commons.okex.open.api.test.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import com.okcoin.commons.okex.open.api.service.futures.FuturesMarketAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.FuturesMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Futures market api tests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/12 14:54
 */
public class FuturesMarketAPITests extends FuturesAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(FuturesMarketAPITests.class);

    private FuturesMarketAPIService marketAPIService;

    @Before
    public void before() {
        config = config();
        marketAPIService = new FuturesMarketAPIServiceImpl(config);
    }

    /**
     * 公共-获取合约信息
     * GET /api/futures/v3/instruments
     */
    @Test
    public void testGetInstruments() {
        List<Instruments> instruments = marketAPIService.getInstruments();
        toResultString(LOG, "Instruments", instruments);
    }

    /**
     * 公共-获取深度数据
     * GET /api/futures/v3/instruments/<instrument_id>/book
     */
    @Test
    public void testGetInstrumentBook() {
        Book book = marketAPIService.getInstrumentBook("BTC-USD-210326", "50","0.1");
        toResultString(LOG, "Instrument-Book", book);
    }

    /**
     * 公共-获取全部ticker信息
     * GET /api/futures/v3/instruments/ticker
     */
    @Test
    public void testGetAllInstrumentTicker() {
        List<Ticker> tickers = marketAPIService.getAllInstrumentTicker();
        toResultString(LOG, "Instrument-Ticker", tickers);
    }

    /**
     * 公共-获取某个ticker信息
     * GET /api/futures/v3/instruments/<instrument_id>/ticker
     */
    @Test
    public void testGetInstrumentTicker() {
        Ticker ticker = marketAPIService.getInstrumentTicker("BTC-USD-210326");
        toResultString(LOG, "Instrument-Ticker", ticker);
    }

    /**
     * 公共-获取成交数据
     * GET /api/futures/v3/instruments/<instrument_id>/trades
     */
    @Test
    public void testGetInstrumentTrades() {
        List<Trades> trades = marketAPIService.getInstrumentTrades("BTC-USDT-210326", null, null, "20");
        toResultString(LOG, "Instrument-Trades", trades);
    }

    /**
     * 公共-获取K线数据
     * GET /api/futures/v3/instruments/<instrument-id>/candles
     */
    @Test
    public void testGetInstrumentCandles() {
        /*String start = "2020-09-10T21:00:00.000Z";
        String end = "2020-09-10T23:00:00.000Z";*/
        String start = null;
        String end = null;
        JSONArray array = marketAPIService.getInstrumentCandles("BTC-USDT-210326", start, end,"60");
        toResultString(LOG, "Instrument-Candles", array);
    }

    /**
     *  公共-获取指数信息
     *  GET/api/futures/v3/instruments/<instrument_id>/index
     */
    @Test
    public void testGetInstrumentIndex() {
        Index index = marketAPIService.getInstrumentIndex("BTC-USD-210326");
        toResultString(LOG, "Instrument-Book", index);
    }

    /**
     * 公共-获取法币汇率
     * GET /api/futures/v3/rate
     */
    @Test
    public void testExchangeRate() {
        ExchangeRate exchangeRate = marketAPIService.getExchangeRate();
        toResultString(LOG, "ExchangeRate", exchangeRate);
    }

    /**
     * 公共-获取预估交割价
     * GET /api/futures/v3/instruments/<instrument_id>/estimated_price
     */
    @Test
    public void testGetInstrumentEstimatedPrice() {
        EstimatedPrice estimatedPrice = marketAPIService.getInstrumentEstimatedPrice("BTC-USD-210326");
        toResultString(LOG, "Instrument-Estimated-Price", estimatedPrice);
    }

    /**
     * 公共-获取平台总持仓量
     * GET /api/futures/v3/instruments/<instrument_id>/open_interest
     */
    @Test
    public void testGetInstrumentHolds() {
        Holds holds = marketAPIService.getInstrumentHolds("BTC-USD-210326");
        toResultString(LOG, "Instrument-Holds", holds);
    }

    /**
     *  公共-获取当前限价
     *  GET /api/futures/v3/instruments/<instrument_id>/price_limit
     */
    @Test
    public void testGetInstrumentPriceLimit() {
        PriceLimit priceLimit = marketAPIService.getInstrumentPriceLimit("BTC-USD-210326");
        toResultString(LOG, "Instrument-Price-Limit", priceLimit);
    }

    /**
     * 公共-获取标记价格
     * GET/api/futures/v3/instruments/<instrument_id>/mark_price
     */
    @Test
    public void testGetMarkPrice() {
        JSONObject jsonObject = marketAPIService.getMarkPrice("BTC-USD-210326");
        toResultString(LOG, "MarkPrice", jsonObject);
    }

    /**
     * 公共-获取强平单
     * GET /api/futures/v3/instruments/<instrument_id>/liquidation
     */
    @Test
    public void testGetInstrumentLiquidation() {
        List<Liquidation> liquidations = marketAPIService.getInstrumentLiquidation("BTC-USD-210326", "1", "10", null, null);
        toResultString(LOG, "Instrument-Liquidation", liquidations);
    }

    /**
     * 公共-获取历史结算/交割记录
     * GET/api/futures/v3/settlement/history
     */
    @Test
    public void testGetSettlementHistory(){
        JSONArray result = marketAPIService.getSettlementHistory("BTC-USDT-210326",null,null,"10",null);
        toResultString(LOG,"result",result);
    }

    /**
     * 公共-获取历史K线数据
     * GET /api/spot/v3/instruments/<instrument_id>/history/candles
     */
    @Test
    public void testGetInstrumentHistoryCandles() {
       /* String start = "2020-06-26T09:00:00.000Z";
        String end = "2020-06-26T08:00:00.000Z";*/
        String start = null;
        String end = null;
        JSONArray array = marketAPIService.getHistoryCandels("BTC-USDT-201225",start,end, "900",null);
        toResultString(LOG, "History-Candles", array);
    }

}
