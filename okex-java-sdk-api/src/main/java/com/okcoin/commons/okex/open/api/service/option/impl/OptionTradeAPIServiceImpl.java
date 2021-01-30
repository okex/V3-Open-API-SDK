package com.okcoin.commons.okex.open.api.service.option.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.option.param.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;

import com.okcoin.commons.okex.open.api.service.option.OptionTradeAPIService;

import java.util.List;

public class OptionTradeAPIServiceImpl implements OptionTradeAPIService {
    private APIClient client;
    private OptionTradeAPI api;

    public OptionTradeAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(OptionTradeAPI.class);
    }

    //单个标的指数持仓信息
    @Override
    public JSONObject getPosition(String underlying, String instrument_id) {
        return this.client.executeSync(this.api.getPosition(underlying,instrument_id));
    }

    //单个标的物账户信息
    @Override
    public JSONObject getAccount(String underLying) {
        return this.client.executeSync(this.api.getAccount(underLying));
    }

    //下单
    @Override
    public JSONObject placeOrder(OrderParam orderParam) {
        return this.client.executeSync(this.api.placeOrder(orderParam));
    }

    //批量下单
    @Override
    public JSONObject placeOrders(OrderDataParam orderDataParam) {
        return this.client.executeSync(this.api.placeOrders(orderDataParam));
    }

    //撤单（通过order_id）
    @Override
    public JSONObject cancelOrderByOrderId(String underlying, String order_id) {
        return this.client.executeSync(this.api.cancelOrderByOrderId(underlying,order_id));
    }

    //撤单（通过client_oid）
    @Override
    public JSONObject cancelOrderByClientOid(String underlying, String client_oid) {
        return this.client.executeSync(this.api.cancelOrderByClientOid(underlying,client_oid));
    }

    //撤销全部订单
    @Override
    public JSONObject cancelAll(String underlying) {
        return this.client.executeSync(this.api.cancelAll(underlying));
    }

    //批量撤单（通过order_id）
    @Override
    public JSONObject cancelBatchOrdersByOrderId(String underlying, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelBatchOrdersByOrderId(underlying,cancelOrders));
    }

    //批量撤单（通过client_oid）
    @Override
    public JSONObject cancelBatchOrdersByClientOid(String underlying, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelBatchOrdersByClientOid(underlying,cancelOrders));
    }

    //修改订单（通过order_id）
    @Override
    public JSONObject amendOrderByOrderId(String underlying, AmentDate amentDate) {
        return this.client.executeSync(this.api.amendOrderByOrderId(underlying,amentDate));
    }

    //修改订单（通过client_oid）
    @Override
    public JSONObject amendOrderByClientOid(String underlying, AmentDate amentDate) {
        return this.client.executeSync(this.api.amendOrderByClientOid(underlying,amentDate));
    }

    //批量修改订单（通过order_id）
    @Override
    public JSONObject amendBatchOrdersByOrderId(String underlying, AmendDateParam amendDateParam) {
        return this.client.executeSync(this.api.amendBatchOrdersByOrderId(underlying,amendDateParam));
    }

    //批量修改订单（通过client_oid）
    @Override
    public JSONObject amendBatchOrdersByClientOid(String underlying, AmendDateParam amendDateParam) {
        return this.client.executeSync(this.api.amendBatchOrdersByClientOid(underlying,amendDateParam));
    }

    //获取单个订单状态(通过order_id)
    @Override
    public JSONObject getOrderInfoByOrderId(String underlying, String order_id) {
        return this.client.executeSync(this.api.getOrderInfoByOrderId(underlying,order_id));
    }

    //获取单个订单状态(通过client_oid)
    @Override
    public JSONObject getOrderInfoByClientOid(String underlying, String client_oid) {
        return this.client.executeSync(this.api.getOrderInfoByClientOid(underlying,client_oid));
    }

    //获取订单列表
    @Override
    public JSONObject getOrderList(String underlying, String instrument_id, String after, String before, String limit, String state) {
        return this.client.executeSync(this.api.getOrderList(underlying, instrument_id, after, before, limit, state));
    }

    //获取成交明细
    @Override
    public JSONArray getFills(String underlying, String order_id, String instrument_id, String after, String before, String limit) {
        return this.client.executeSync(this.api.getFills(underlying, order_id, instrument_id, after, before,limit));
    }

    //获取账单流水
    @Override
    public JSONArray getLedger(String underlying,String after, String before, String limit) {
        return this.client.executeSync(this.api.getLedger(underlying, after, before, limit));
    }

    //获取手续费费率
    @Override
    public JSONObject getTradeFee(String category, String underlying) {
        return this.client.executeSync(this.api.getTradeFee(category,underlying));
    }

}
