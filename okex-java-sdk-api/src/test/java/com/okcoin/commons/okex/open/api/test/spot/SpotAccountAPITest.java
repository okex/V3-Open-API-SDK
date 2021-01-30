package com.okcoin.commons.okex.open.api.test.spot;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.bean.spot.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.spot.result.ServerTimeDto;
import com.okcoin.commons.okex.open.api.service.spot.SpotAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SpotAccountAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpotAccountAPITest.class);

    private SpotAccountAPIService spotAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.spotAccountAPIService = new SpotAccountAPIServiceImpl(this.config);
    }

    /**
     * 币币账户信息
     * GET /api/spot/v3/accounts
     */
    @Test
    public void getAccounts() {
        final List<Account> accounts = this.spotAccountAPIService.getAccounts();
        this.toResultString(SpotAccountAPITest.LOG, "accounts", accounts);
    }

    /**
     * 单一币种账户信息
     * GET /api/spot/v3/accounts/<currency>
     */
    @Test
    public void getAccountByCurrency() {
        final Account account = this.spotAccountAPIService.getAccountByCurrency("EOS");
        this.toResultString(SpotAccountAPITest.LOG, "account", account);
    }

    /**
     * 账单流水查询
     * GET /api/spot/v3/accounts/<currency>/ledger
     */
    @Test
    public void getLedgersByCurrency() {
        final Object ledgers = this.spotAccountAPIService.getLedgersByCurrency("OKB", null, null, null,null);
        this.toResultString(SpotAccountAPITest.LOG, "ledges", ledgers);
    }

    /**
     * 获取当前账户费率
     * GET /api/spot/v3/trade_fee
     * **/
    @Test
    public void testGetTradefee(){
        JSONObject result = spotAccountAPIService.getTradeFee("3",null);
        this.toResultString(SpotAccountAPITest.LOG, "result", result);

    }

}
