package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.option.param.AmendDateParam;
import com.okcoin.commons.okex.open.api.bean.option.param.AmentDate;
import com.okcoin.commons.okex.open.api.bean.option.param.CancelOrders;
import com.okcoin.commons.okex.open.api.bean.option.param.OrderDataParam;
import com.okcoin.commons.okex.open.api.bean.option.param.OrderParam;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.option.OptionTradeAPIService;

public class OptionTradeAPIServiceImpl implements OptionTradeAPIService {
    private APIClient client;
    private OptionTradeAPI api;

    public OptionTradeAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(OptionTradeAPI.class);
    }

    @Override
    public JSONObject getAccount(String underLying) {
        return this.client.executeSync(this.api.getAccount(underLying));
    }

    @Override
    public JSONObject amendOrder(String underlying, AmentDate amentDate) {
        return this.client.executeSync(this.api.amendOrder(underlying,amentDate));
    }

    @Override
    public JSONObject amendBatchOrders(String underlying, AmendDateParam amendDateParam) {
        return this.client.executeSync(this.api.amendBatchOrders(underlying,amendDateParam));
    }



    @Override
    public JSONObject cancelOrders(String underlying, String order_id) {
        return this.client.executeSync(this.api.cancelOrder(underlying,order_id));
    }

    @Override
    public JSONObject cancelOrderByClientOid(String underlying, String client_oid) {
        return this.client.executeSync(this.api.cancelOrderByClientOid(underlying,client_oid));
    }

    @Override
    public JSONObject cancelBatchOrders(String underlying, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelBatchOrders(underlying,cancelOrders));
    }

    @Override
    public JSONArray getFills(String underlying, String order_id, String instrument_id, String before, String after, String limit) {
        return this.client.executeSync(this.api.getFills(underlying,order_id,instrument_id,before,after,limit));
    }



    @Override
    public JSONArray getLedger(String underlying,String before, String after, String limit) {
        return this.client.executeSync(this.api.getLedger(underlying,before,after,limit));
    }

    @Override
    public JSONObject getOrder(OrderParam orderParam) {
        return this.client.executeSync(this.api.getOrder(orderParam));
    }

    @Override
    public JSONObject getOrders1(OrderDataParam orderDataParam) {
        return this.client.executeSync(this.api.getOrders1(orderDataParam));
    }

    @Override
    public JSONObject getOrderInfo(String underlying, String order_id) {
        return this.client.executeSync(this.api.getOrderInfo(underlying,order_id));
    }

    @Override
    public JSONObject getOrderInfoByClientOid(String underlying, String client_oid) {
        return this.client.executeSync(this.api.getOrderInfoByClientOid(underlying,client_oid));
    }

    @Override
    public JSONObject getOrderList(String underlying,String state,String instrument_id,String before,String after,String limit) {
        return this.client.executeSync(this.api.getOrderList(underlying,state,instrument_id,before,after,limit));
    }



    @Override
    public JSONObject getPosition(String underlying, String instrument_id) {
        return this.client.executeSync(this.api.getPosition(underlying,instrument_id));
    }

    @Override
    public JSONObject getTradeFee() {
        return this.client.executeSync(this.api.getTradeFee("1"));
    }

}
