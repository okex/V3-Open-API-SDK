package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.OrderParamDto;
import com.okcoin.commons.okex.open.api.bean.spot.param.PlaceOrderParam;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.MarginOrderAPIService;

import java.util.List;
import java.util.Map;

public class MarginOrderAPIServiceImpl implements MarginOrderAPIService {
    private final APIClient client;
    private final MarginOrderAPI marginOrderAPI;

    public MarginOrderAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.marginOrderAPI = this.client.createService(MarginOrderAPI.class);
    }

    //下单
    @Override
    public OrderResult addOrder(final PlaceOrderParam order) {
        return this.client.executeSync(this.marginOrderAPI.addOrder(order));
    }

    //批量下单
    @Override
    public Map<String, List<OrderResult>> batchOrders(final List<PlaceOrderParam> order) {
        return this.client.executeSync(this.marginOrderAPI.batchOrders(order));
    }

    //撤销指定订单(通过order_id)
    @Override
    public OrderResult cancleOrdersByOrderId(final PlaceOrderParam order, final String order_id) {
        return this.client.executeSync(this.marginOrderAPI.cancleOrdersByOrderId(order_id, order));
    }

    //撤销指定订单(通过client_oid)
    @Override
    public OrderResult cancleOrdersByClientOid(final PlaceOrderParam order, final String client_oid) {
        return this.client.executeSync(this.marginOrderAPI.cancleOrdersByClientOid(client_oid, order));
    }

    //批量撤销订单(通过order_id)
    @Override
    public Map<String, Object> batchCancleOrdersByOrderId(final List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.marginOrderAPI.batchCancleOrdersByOrderId(cancleOrders));
    }

    //批量撤销订单(通过client_oid)
    @Override
    public Map<String, Object> batchCancleOrdersByClientOid(List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.marginOrderAPI.batchCancleOrdersByClientOid(cancleOrders));
    }

    //获取订单列表
    @Override
    public List<OrderInfo> getOrders(final String instrument_id, final String state, final String after, final String before, final String limit) {
        return this.client.executeSync(this.marginOrderAPI.getOrders(instrument_id, state, after, before, limit));
    }

    //获取订单信息(通过order_id)
    @Override
    public OrderInfo getOrderByOrderId(final String instrument_id, final String order_id) {
        return this.client.executeSync(this.marginOrderAPI.getOrderByOrderId(order_id, instrument_id));
    }

    //获取订单信息(通过order_id)
    @Override
    public OrderInfo getOrderByClientOid(String client_oid,String instrument_id) {
        return client.executeSync(this.marginOrderAPI.getOrderByClientOid(client_oid,instrument_id));
    }

    //获取所有未成交订单
    @Override
    public List<PendingOrdersInfo> getPendingOrders(final String instrument_id, final String after, final String before, final String limit) {
        return this.client.executeSync(this.marginOrderAPI.getPendingOrders(instrument_id, after, before, limit));
    }

    //获取成交明细
    @Override
    public List<MarginFills> getFills(final String order_id, final String instrument_id, final String after, final String before, final String limit) {
        return this.client.executeSync(this.marginOrderAPI.getFills(order_id, instrument_id, after, before, limit));
    }

}
