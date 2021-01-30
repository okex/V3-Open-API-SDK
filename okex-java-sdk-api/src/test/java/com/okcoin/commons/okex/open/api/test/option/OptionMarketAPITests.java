package com.okcoin.commons.okex.open.api.test.option;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.service.option.OptionMarketAPIService;
import com.okcoin.commons.okex.open.api.service.option.impl.OptionMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionMarketAPITests extends OptionAPIBaseTests{
    private static final Logger LOG = LoggerFactory.getLogger(OptionMarketAPITests.class);

    private OptionMarketAPIService marketAPIService;

    @Before
    public void before() {
        config = config();
        marketAPIService = new OptionMarketAPIServiceImpl(config);
    }

    /**
     * 公共-获取标的指数
     * GET /api/option/v3/underlying
     */
    @Test
    public void testGetUnderlying(){
        JSONArray result = marketAPIService.getUnderlying();
        toResultString(LOG,"result",result);
    }

    /**
     * 公共-获取期权合约
     * GET /api/option/v3/instruments/<underlying>
     */
    @Test
    public void testGetInstrumrnt(){
        JSONArray result = marketAPIService.getInstruments("BTC-USD",null,null);
        toResultString(LOG,"result",result);
    }

    /**
     * 公共-获取期权合约详细定价
     * GET /api/option/v3/instruments/<underlying>/summary
     */
    @Test
    public void testGetAllSummary(){
        JSONArray result = marketAPIService.getAllSummary("BTC-USD","201225");
        toResultString(LOG,"",result);
    }

    /**
     * 公共-获取单个期权合约详细定价
     * GET /api/option/v3/instruments/<underlying>/summary/<instrument_id>
     */
    @Test
    public void testGetDetailPrice(){
        JSONObject result = marketAPIService.getDetailPrice("BTC-USD","BTC-USD-200925-7000-C");
        toResultString(LOG,"result",result);
    }

    /**
     * 公共-获取深度数据
     * GET /api/option/v3/instruments/<instrument_id>/book
     */
    @Test
    public void testGetDepthData(){
        JSONObject result = marketAPIService.getDepthData("BTC-USD-201225-7000-C","10");
        toResultString(LOG,"result",result);
    }

    /**
     * 公共-获取成交数据
     * GET /api/option/v3/instruments/<instrument_id>/trades
     */
    @Test
    public void testGetTradeList(){
        JSONArray result = marketAPIService.getTradeList("BTC-USD-201225-7000-C",null,null,"10");
        toResultString(LOG,"",result);
    }

    /**
     * 公共-获取某个Ticker信息
     * GET /api/option/v3/instruments/<instrument_id>/ticker
     */
    @Test
    public void testGetTicker(){
        JSONObject result = marketAPIService.getTicker("BTC-USD-201225-7000-C");
        toResultString(LOG,"result",result);
    }

    /**
     * 公共-获取K线数据
     * GET /api/option/v3/instruments/<instrument_id>/candles
     */
    @Test
    public void testGetCandles(){
        JSONArray result = marketAPIService.getCandles("BTC-USD-201225-7000-C",null,null,"60");
        toResultString(LOG,"result",result);
    }

    /**
     *公共-获取历史结算/行权记录
     * GET /api/option/v3/settlement/history/<underlying>
     */
    @Test
    public void testGetHistorySettlement(){
        JSONArray result = marketAPIService.getHistorySettlement("BTC-USD",null,"10",null);
        toResultString(LOG,"result",result);
    }
}
