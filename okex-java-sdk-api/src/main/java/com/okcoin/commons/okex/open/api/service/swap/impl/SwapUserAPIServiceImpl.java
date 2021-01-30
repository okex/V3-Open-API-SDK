package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.swap.SwapUserAPIServive;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

public class SwapUserAPIServiceImpl implements SwapUserAPIServive {
    private APIClient client;
    private SwapUserAPI api;

    /*public SwapUserAPIServiceImpl() {
    }*/

    public SwapUserAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(SwapUserAPI.class);
    }

    //所有合约持仓信息
    @Override
    public String getPositions() {
        return this.client.executeSync(this.api.getPositions());
    }

    //单个合约持仓信息
    @Override
    public String getPosition(String instrument_id) {
        return client.executeSync(api.getPosition(instrument_id));
    }

    //所有币种合约账户信息
    @Override
    public String getAccounts() {
        return client.executeSync(api.getAccounts());
    }

    //某个币种合约账户信息
    @Override
    public String selectAccount(String instrument_id) {
        return client.executeSync(api.selectAccount(instrument_id));
    }

    //获取某个合约的用户配置
    @Override
    public String selectContractSettings(String instrument_id) {
        return client.executeSync(api.selectContractSettings(instrument_id));
    }

    //设定某个合约的杠杆
    @Override
    public String updateLevelRate(String instrument_id, LevelRateParam levelRateParam) {
        return client.executeSync(api.updateLevelRate(instrument_id, JsonUtils.convertObject(levelRateParam, LevelRateParam.class)));
    }

    //账单流水查询
    @Override
    public String getLedger(String instrument_id, String after, String before,  String limit, String type) {
        return client.executeSync(api.getLedger(instrument_id, after, before, limit,type));
    }

    //获取所有订单列表
    @Override
    public String selectOrders(String instrument_id, String after, String before, String limit, String state) {
        return client.executeSync(api.selectOrders(instrument_id, after, before, limit, state));
    }

    //获取订单信息(通过order_id)
    @Override
    public String selectOrderByOrderId(String instrument_id, String order_id) {
        return client.executeSync(api.selectOrderByOrderId(instrument_id, order_id));
    }

    //获取订单信息(通过client_oid)
    @Override
    public String selectOrderByClientOid(String instrument_id, String client_oid) {
        return client.executeSync(api.selectOrderByClientOid(instrument_id,client_oid));
    }

    //获取成交明细
    @Override
    public String selectDealDetail(String order_id, String instrument_id, String after, String before, String limit) {
        return client.executeSync(api.selectDealDetail(order_id, instrument_id, after, before, limit));
    }

    //获取合约挂单冻结数量
    @Override
    public String getHolds(String instrument_id) {
        return client.executeSync(api.getHolds(instrument_id));
    }

    //获取账户手续费费率
    @Override
    public String getTradeFee(String category, String instrument_id) {
        return client.executeSync(api.getTradeFee(category, instrument_id));
    }
}
