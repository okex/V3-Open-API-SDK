package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.swap.SwapUserAPIServive;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

public class SwapUserAPIServiceImpl implements SwapUserAPIServive {
    private APIClient client;
    private SwapUserAPI api;

    public SwapUserAPIServiceImpl() {
    }

    public SwapUserAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(SwapUserAPI.class);
    }
    /**
     * 获取所有合约持仓信息
     *
     * @param
     * @return
     */

    @Override
    public String getPositions() {
        return this.client.executeSync(this.api.getPositions());
    }

    /**
     * 获取单个合约持仓信息
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getPosition(String instrument_id) {
        return client.executeSync(api.getPosition(instrument_id));
    }

    /**
     * 获取所有币种合约的账户信息
     *
     * @return
     */
    @Override
    public String getAccounts() {
        return client.executeSync(api.getAccounts());
    }

    /**
     * 获取某个币种合约的账户信息
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String selectAccount(String instrument_id) {
        return client.executeSync(api.selectAccount(instrument_id));
    }

    /**
     * 获取某个合约的用户配置
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String selectContractSettings(String instrument_id) {
        return client.executeSync(api.selectContractSettings(instrument_id));
    }

    /**
     * 设定某个合约的杠杆

     * @return
     */
    @Override
    public String updateLevelRate(String instrument_id, LevelRateParam levelRateParam) {
        return client.executeSync(api.updateLevelRate(instrument_id, JsonUtils.convertObject(levelRateParam, LevelRateParam.class)));
    }

   /* @Override*/
   /* public String updateLevelRate(String instrument_id, LevelRateParam levelRateParam) {
        System.out.println("instrumentId"+instrument_id);
       // return client.executeSync(api.updateLevelRate(instrumentId, JsonUtils.convertObject(levelRateParam, LevelRateParam.class)));
        return client.executeSync(api.updateLevelRate(instrument_id, levelRateParam));
    }*/


    /**
     * 获取所有订单列表
     *
     * @param instrument_id
     * @param state
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @Override
    public String selectOrders(String instrument_id, String state, String before, String after, String limit) {
        return client.executeSync(api.selectOrders(instrument_id, state, before, after, limit));
    }

    /**
     * 通过订单id获取单个订单信息
     *
     * @param instrument_id
     * @param order_id
     * @return
     */
    @Override
    public String selectOrderByOrderId(String instrument_id, String order_id) {
        return client.executeSync(api.selectOrderByOrderId(instrument_id, order_id));
    }

    @Override
    public String selectOrderByClientOid(String instrument_id, String client_oid) {
        return client.executeSync(api.selectOrderByClientOid(instrument_id,client_oid));
    }

    /**
     * 获取最近的成交明细列表
     *
     * @param instrument_id
     * @param order_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @Override
    public String selectDealDetail(String instrument_id, String order_id, String before, String after, String limit) {
        return client.executeSync(api.selectDealDetail(instrument_id, order_id, before, after, limit));
    }

    /**
     * 列出账户资产流水，账户资产流水是指导致账户余额增加或减少的行为。
     * 流水会分页，每页100条数据，并且按照时间倒序排序和存储，最新的排在最前面。
     *
     * @param instrument_id
     * @param before
     * @param after
     * @param limit
     * @return
     */
    @Override
    public String getLedger(String instrument_id, String before, String after, String limit,String type) {
        return client.executeSync(api.getLedger(instrument_id, before, after, limit,type));
    }

    /**
     * 获取合约挂单冻结数量
     *
     * @param instrument_id
     * @return
     */
    @Override
    public String getHolds(String instrument_id) {
        return client.executeSync(api.getHolds(instrument_id));
    }

    @Override
    public String getTradeFee() {
        return client.executeSync(api.getTradeFee("1"));
    }
}
