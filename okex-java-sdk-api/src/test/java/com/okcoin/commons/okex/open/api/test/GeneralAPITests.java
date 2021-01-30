package com.okcoin.commons.okex.open.api.test;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.result.ExchangeRate;
import com.okcoin.commons.okex.open.api.bean.futures.result.ServerTime;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.GeneralAPIService;
import com.okcoin.commons.okex.open.api.service.futures.impl.GeneralAPIServiceImpl;
import com.okcoin.commons.okex.open.api.test.futures.FuturesAPIBaseTests;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * General API Tests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/12 14:34
 */
public class GeneralAPITests extends FuturesAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralAPITests.class);

    private GeneralAPIService generalAPIService;
    APIConfiguration config = new APIConfiguration();

    @Before
    public void before() {
        config = config();
        generalAPIService = new GeneralAPIServiceImpl(config);
    }

    //获取系统时间戳接口
    @Test
    public void testServerTime() {
         ServerTime time = generalAPIService.getServerTime();
        toResultString(LOG, "ServerTime", time);
    }

    //获取法币汇率接口
    @Test
    public void testExchangeRate() {
        ExchangeRate exchangeRates = generalAPIService.getExchangeRate();
        toResultString(LOG, "ExchangeRate", exchangeRates);
    }
}
