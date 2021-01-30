package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.result.*;
import com.okcoin.commons.okex.open.api.service.swap.SwapMarketAPIService;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SwapMarketTest extends SwapBaseTest {
    private SwapMarketAPIService swapMarketAPIService;

    @Before
    public void before() {
        config = config();
        swapMarketAPIService = new SwapMarketAPIServiceImpl(config);
    }

    /**
     * 公共-获取合约信息
     * GET /api/swap/v3/instruments
     */
    @Test
    public void getContractsApi() {
        String contractsApi = swapMarketAPIService.getContractsApi();
        if(contractsApi.startsWith("{")){
            System.out.println(contractsApi);
        }else {
            List<ApiContractVO> list = JSONArray.parseArray(contractsApi, ApiContractVO.class);
            System.out.println(contractsApi);
        }
    }

    /**
     * 公共-获取深度数据
     * GET /api/swap/v3/instruments/<instrument_id>/depth
     */
    @Test
    public void getDepthApi() {
        String depthApi = swapMarketAPIService.getDepthApi("BTC-USDT-SWAP", "1","1");
        System.out.println(depthApi);
    }

    /**
     * 公共-获取全部ticker信息
     * GET /api/swap/v3/instruments/ticker
     */
    @Test
    public void getTickersApi() {
        String tickersApi = swapMarketAPIService.getTickersApi();
        if (tickersApi.startsWith("{")) {
            System.out.println(tickersApi);
        } else {
            List<ApiTickerVO> list = JSONArray.parseArray(tickersApi, ApiTickerVO.class);
//            list.forEach(vo -> System.out.println(vo.getInstrument_id()));
            System.out.println(tickersApi);
        }
    }

    /**
     * 公共-获取某个ticker信息
     * GET /api/swap/v3/instruments/<instrument_id>/ticker
     */
    @Test
    public void getTickerApi() {
        String tickerApi = swapMarketAPIService.getTickerApi("BTC-USD-SWAP");
        ApiTickerVO apiTickerVO = JSONObject.parseObject(tickerApi, ApiTickerVO.class);
        System.out.println(tickerApi);
//        System.out.println(apiTickerVO.getInstrument_id());
    }

    /**
     * 公共-获取成交数据
     * GET /api/swap/v3/instruments/<instrument_id>/trades
     */
    @Test
    public void getTradesApi() {
        String tradesApi = swapMarketAPIService.getTradesApi("BTC-USDT-SWAP", null, null, "5");
        if (tradesApi.startsWith("{")) {
            System.out.println(tradesApi);
        } else {
            List<ApiDealVO> apiDealVOS = JSONArray.parseArray(tradesApi, ApiDealVO.class);
            System.out.println(tradesApi);
        }
    }

    /**
     * 公共-获取K线数据
     * GET /api/swap/v3/instruments/<instrument_id>/candles
     */
    @Test
    public void getCandlesApi() {
        /*String start = "2020-09-24T08:00:00.000Z";
        String end = "2020-09-24T08:30:00.000Z";*/
        String start = null;
        String end = null;
        String candlesApi = swapMarketAPIService.getCandlesApi("BTC-USD-SWAP", start, end, "60");
       String[] candleSize=candlesApi.split("],");

        System.out.println("------------------------"+candleSize.length);
        candlesApi = candlesApi.replaceAll("\\[", "\\[");
        System.out.println(candlesApi);

    }

    /**
     * 公共-获取指数信息
     * GET /api/swap/v3/instruments/<instrument_id>/index
     */
    @Test
    public void getIndexApi() {
        String indexApi = swapMarketAPIService.getIndexApi("XRP-USDT-SWAP");
        System.out.println(indexApi);
    }

    /**
     * 公共-获取法币汇率
     * GET /api/swap/v3/rate
     */
    @Test
    public void getRateApi() {
        String rateApi = swapMarketAPIService.getRateApi();
        System.out.println(rateApi);
    }

    /**
     * 公共-获取平台总持仓量
     * GET /api/swap/v3/instruments/<instrument_id>/open_interest
     */
    @Test
    public void getOpenInterestApi() {
        String openInterestApi = swapMarketAPIService.getOpenInterestApi("XRP-USDT-SWAP");
        System.out.println(openInterestApi);
    }

    /**
     * 公共-获取当前限价
     * GET /api/swap/v3/instruments/<instrument_id>/price_limit
     */
    @Test
    public void getPriceLimitApi() {
        String priceLimitApi = swapMarketAPIService.getPriceLimitApi("XRP-USDT-SWAP");
        System.out.println(priceLimitApi);
    }

    /**
     * 公共-获取强平单
     * GET /api/swap/v3/instruments/<instrument_id>/liquidation
     */
    @Test
    public void getLiquidationApi() {
        String liquidationApi = swapMarketAPIService.getLiquidationApi("XRP-USDT-SWAP", "1", null, null, null);
        if (liquidationApi.startsWith("{")) {
            System.out.println(liquidationApi);
        } else {
            List<ApiLiquidationVO> apiLiquidationVOS = JSONArray.parseArray(liquidationApi, ApiLiquidationVO.class);
        }
    }

    /**
     * 公共-获取合约资金费率
     * GET /api/swap/v3/instruments/<instrument_id>/funding_time
     */
    @Test
    public void getFundingTimeApi() {
        String fundingTimeApi = swapMarketAPIService.getFundingTimeApi("BTC-USD-SWAP");
        ApiFundingTimeVO apiFundingTimeVO = JSONObject.parseObject(fundingTimeApi, ApiFundingTimeVO.class);
    }

    /**
     * 公共-获取合约标记价格
     * GET /api/swap/v3/instruments/<instrument_id>/mark_price
     */
    @Test
    public void getMarkPriceApi() {
        String markPriceApi = swapMarketAPIService.getMarkPriceApi("BTC-USD-SWAP");
        ApiMarkPriceVO apiMarkPriceVO = JSONObject.parseObject(markPriceApi, ApiMarkPriceVO.class);
    }

    /**
     * 公共-获取合约历史资金费率
     * GET /api/swap/v3/instruments/<instrument_id>/historical_funding_rate
     */
    @Test
    public void getHistoricalFundingRateApi() {
        String historicalFundingRateApi = swapMarketAPIService.getHistoricalFundingRateApi("BTC-USDT-SWAP", "");
        if (historicalFundingRateApi.startsWith("{")) {
            System.out.println(historicalFundingRateApi);
        } else {
            List<ApiFundingRateVO> apiFundingRateVOS = JSONArray.parseArray(historicalFundingRateApi, ApiFundingRateVO.class);
        }
    }

    /**
     * 公共-获取历史K线数据
     * GET /api/swap/v3/instruments/<instrument_id>/history/candles
     */
    @Test
    public void getHistoryCandlesApi() {
        String start = "2020-09-20T09:50:00.000Z";
        String end = "2020-09-20T08:20:00.000Z";
       /* String start = null;
        String end = null;*/
        String candlesApi = swapMarketAPIService.getHistoryCandlesApi("BTC-USDT-SWAP", start, end, "60","1");
        String[] candleSize=candlesApi.split("],");
        System.out.println("数据量："+candleSize.length);
    }

}
