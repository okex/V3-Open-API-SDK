package com.okcoin.commons.okex.open.api.test.system;

import com.okcoin.commons.okex.open.api.service.system.SystemMarketAPIService;
import com.okcoin.commons.okex.open.api.service.system.impl.SystemMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemMarketAPITest extends SystemAPIBaseTest{
    private static final Logger LOG = LoggerFactory.getLogger(SystemMarketAPITest.class);
    private SystemMarketAPIService systemMarketAPIService;

    @Before
    public void before() {
        config = config();
        systemMarketAPIService = new SystemMarketAPIServiceImpl(config);
    }

    /**
     * 公共-获取系统升级状态
     * GET /api/system/v3/status
     */
    @Test
    public void testGetMaintenance(){
        String result = systemMarketAPIService.getMaintenance("0");
        System.out.println(result);
    }

}
