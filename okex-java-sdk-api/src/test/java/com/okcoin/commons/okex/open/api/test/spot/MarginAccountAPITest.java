package com.okcoin.commons.okex.open.api.test.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.MarginLeverage;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.service.spot.MarginAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.MarginAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MarginAccountAPITest extends SpotAPIBaseTests {
    private static final Logger LOG = LoggerFactory.getLogger(MarginAccountAPITest.class);

    private MarginAccountAPIService marginAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.marginAccountAPIService = new MarginAccountAPIServiceImpl(this.config);
    }

    /**
     * 币币杠杆账户信息
     * GET /api/margin/v3/accounts
     */
    @Test
    public void getAccounts() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAccounts();
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 单一币对账户信息
     * GET /api/margin/v3/accounts/<instrument_id>
     */
    @Test
    public void getAccountsByInstrumentId() {
        final Map<String, Object> result = this.marginAccountAPIService.getAccountsByInstrumentId("BTC-USDT");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 账单流水查询
     * GET /api/margin/v3/accounts/<instrument_id>/ledger
     */
    @Test
    public void getLedger() {
        final List<UserMarginBillDto> result = this.marginAccountAPIService.getLedger(
                "OKB-USDT", null,null, null, null);
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 杠杆配置信息
     * GET /api/margin/v3/accounts/availability
     */
    @Test
    public void getAvailability() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAvailability();
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 某个杠杆配置信息
     * GET /api/margin/v3/accounts/<instrument_id>/availability
     */
    @Test
    public void getAvailabilityByInstrumentId() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAvailabilityByInstrumentId("BTC-USDT");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 获取借币记录
     * GET /api/margin/v3/accounts/borrowed
     */
    @Test
    public void getBorrowedAccounts() {
        final List<MarginBorrowOrderDto> result = this.marginAccountAPIService.getBorrowedAccounts("1", "", "", "5");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 某币对借币记录
     * GET /api/margin/v3/accounts/<instrument_id>/borrowed
     */
    @Test
    public void getBorrowedAccountsByInstrumentId() {
        final List<MarginBorrowOrderDto> result = this.marginAccountAPIService.getBorrowedAccountsByInstrumentId("XRP-USDT", "1", null, null, "10");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 借币
     * POST /api/margin/v3/accounts/borrow
     */
    @Test
    public void borrow() {
        final BorrowRequestDto dto = new BorrowRequestDto();
        dto.setInstrument_id("BTC-USDT");
        dto.setClient_oid("jiebi02");
        dto.setCurrency("USDT");
        dto.setAmount("3");
        final BorrowResult result = this.marginAccountAPIService.borrow(dto);
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 还币
     * POST /api/margin/v3/accounts/repayment
     */
    @Test
    public void repayment() {
        final RepaymentRequestDto dto = new RepaymentRequestDto();
        dto.setBorrow_id("185778");
        dto.setClient_oid("");
        dto.setInstrument_id("XRP-USDT");
        dto.setCurrency("XRP");
        dto.setAmount("1");

        final RepaymentResult result = this.marginAccountAPIService.repayment(dto);
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 获取杠杆倍数
     * GET/api/margin/v3/accounts/<instrument_id>/leverage
     */
    @Test
    public void testGetLeverage(){
        JSONObject result = marginAccountAPIService.getLeverage("BTC-USDT");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);

    }

    /**
     * 设置杠杆倍数
     * POST /api/margin/v3/accounts/<instrument_id>/leverage
     */
    @Test
    public void testSetLeverage(){
        MarginLeverage leverage = new MarginLeverage();
        leverage.setLeverage("4");
        JSONObject result = marginAccountAPIService.setLeverage("XRP-USDT",leverage);
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

}
