package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.MarginLeverage;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.MarginAccountAPIService;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class MarginAccountAPIServiceImpl implements MarginAccountAPIService {

    private final APIClient client;
    private final MarginAccountAPI api;

    public MarginAccountAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = this.client.createService(MarginAccountAPI.class);
    }

    //币币杠杆账户信息
    @Override
    public List<Map<String, Object>> getAccounts() {
        return this.client.executeSync(this.api.getAccounts());
    }

    //单一币对账户信息
    @Override
    public Map<String, Object> getAccountsByInstrumentId(final String instrument_id) {
        return this.client.executeSync(this.api.getAccountsByInstrumentId(instrument_id));
    }

    //账单流水查询
    @Override
    public List<UserMarginBillDto> getLedger(final String instrument_id, final String type, final String before, final String after, final String limit) {
        return this.client.executeSync(this.api.getLedger(instrument_id, type, before, after, limit));
    }

    //杠杆配置信息
    @Override
    public List<Map<String, Object>> getAvailability() {
        return this.client.executeSync(this.api.getAvailability());
    }

    //某个杠杆配置信息
    @Override
    public List<Map<String, Object>> getAvailabilityByInstrumentId(final String instrument_id) {
        return this.client.executeSync(this.api.getAvailabilityByInstrumentId(instrument_id));
    }

    //获取借币记录
    @Override
    public List<MarginBorrowOrderDto> getBorrowedAccounts(final String status, final String before, final String after, final String limit) {
        return this.client.executeSync(this.api.getBorrowedAccounts(status, before, after, limit));
    }

    //某币对借币记录
    @Override
    public List<MarginBorrowOrderDto> getBorrowedAccountsByInstrumentId(final String instrument_id,final String status, final String after, final String before, final String limit) {
        return this.client.executeSync(this.api.getBorrowedAccountsByInstrumentId(instrument_id, status, after, before,  limit));
    }

    //借币
    @Override
    public BorrowResult borrow(final BorrowRequestDto order) {
        return this.client.executeSync(this.api.borrow(order));
    }

    //还币
    @Override
    public RepaymentResult repayment(final RepaymentRequestDto order) {
        return this.client.executeSync(this.api.repayment(order));
    }

    //获取杠杆倍数
    @Override
    public JSONObject getLeverage(String leverage) {
        return this.client.executeSync(this.api.getLeverage(leverage));
    }

    //设置杠杆倍数
    @Override
    public JSONObject setLeverage(String instrument_id, MarginLeverage leverage) {
        return this.client.executeSync(this.api.setLeverage(instrument_id,leverage));
    }

}
