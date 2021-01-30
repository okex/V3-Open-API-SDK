package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.*;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.SpotOrderAPIServive;

import java.util.List;
import java.util.Map;

/**
 * 币币订单相关接口
 **/
public class SpotOrderApiServiceImpl implements SpotOrderAPIServive {
    private final APIClient client;
    private final SpotOrderAPI spotOrderAPI;

    public SpotOrderApiServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.spotOrderAPI = this.client.createService(SpotOrderAPI.class);
    }

    //下单
    @Override
    public OrderResult addOrder(final PlaceOrderParam order) {
        return this.client.executeSync(this.spotOrderAPI.addOrder(order));
    }

    //批量下单
    @Override
    public Map<String, List<OrderResult>> addOrders(final List<PlaceOrderParam> order) {
        return this.client.executeSync(this.spotOrderAPI.addOrders(order));
    }

    //撤销指定订单(通过order_id进行撤单)
    @Override
    public OrderResult cancleOrderByOrderId(final PlaceOrderParam order, final String order_id) {
        return this.client.executeSync(this.spotOrderAPI.cancleOrderByOrderId(order_id, order));
    }

    //撤销指定订单(通过client_oid进行撤单)
    @Override
    public OrderResult cancleOrderByClientOid(PlaceOrderParam order, String client_oid) {
        return this.client.executeSync(this.spotOrderAPI.cancleOrderByClientOid(client_oid, order));
    }

    //批量撤销订单（通过order_id）
    @Override
    public Map<String, Object> batchCancleOrdersByOrderId(final List<OrderParamDto> cancleOrders) {
        return this.client.executeSync(this.spotOrderAPI.batchCancleOrdersByOrderId(cancleOrders));
    }

    //批量撤销订单(通过client_oid)
    @Override
    public Map<String, Object> batchCancleOrdersByClientOid(List<OrderParamDto> orderParamDto) {
        return this.client.executeSync(this.spotOrderAPI.batchCancleOrdersByClientOid(orderParamDto));
    }

    //修改订单(通过order_id)
    @Override
    public JSONObject amendOrderByOrderId(String instrument_id, AmendParam amendParam) {
        return this.client.executeSync(this.spotOrderAPI.amendOrderByOrderId(instrument_id,amendParam));
    }

    //修改订单(通过client_oid)
    @Override
    public JSONObject amendOrderByClientOid(String instrument_id, AmendParam amendParam) {
        return this.client.executeSync(this.spotOrderAPI.amendOrderByClientOid(instrument_id, amendParam));
    }

    //批量修改订单(通过order_id)
    @Override
    public JSONObject batchAmendOrderByOrderId(List<AmendParam> amendParam) {
        return this.client.executeSync(this.spotOrderAPI.batchAmendOrderByOrderId(amendParam));
    }

    //批量修改订单(通过client_oid)
    @Override
    public JSONObject batchAmendOrdersByClientOid(List<AmendParam> amendParam) {
        return this.client.executeSync(this.spotOrderAPI.batchAmendOrdersByClientOid(amendParam));
    }

    //获取订单列表
    @Override
    public List<OrderInfo> getOrders(final String instrument_id, final String state, final String after, final String before, final String limit) {
        return this.client.executeSync(this.spotOrderAPI.getOrders(instrument_id, state, after, before, limit));
    }

    //获取所有未成交订单
    @Override
    public List<PendingOrdersInfo> getPendingOrders(final String instrument_id, final String after, final String before,  final String limit) {
        return this.client.executeSync(this.spotOrderAPI.getPendingOrders(instrument_id, after, before, limit));
    }

    //获取订单信息(通过order_id)
    @Override
    public JSONObject getOrderByOrderId(final String instrument_id, final String order_id) {
        return this.client.executeSync(this.spotOrderAPI.getOrderByOrderId(order_id, instrument_id));
    }

    //获取订单信息(通过client_oid)
    @Override
    public JSONObject getOrderByClientOid(String instrument_id, String client_oid) {
        return this.client.executeSync(this.spotOrderAPI.getOrderByClientOid(client_oid,instrument_id));
    }

    //获取成交明细
    @Override
    public List<Fills> getFills(final String order_id, final String instrument_id, final String after, final String before, final String limit) {
        return this.client.executeSync(this.spotOrderAPI.getFills(order_id, instrument_id, after, before, limit));
    }

    //委托策略下单
    @Override
    public OrderAlgoResult addorder_algo(OrderAlgoParam order) {
        return this.client.executeSync(this.spotOrderAPI.addorder_algo(order));
    }

    //委托策略撤单
    @Override
    public CancelAlgoResult cancelOrder_algo(CancelAlgoParam cancelAlgoParam) {
        return this.client.executeSync(this.spotOrderAPI.cancelOrder_algo(cancelAlgoParam));
    }

    //获取委托单列表
    @Override
    public String getAlgoOrder(final String instrument_id, final String order_type, final String status, final String algo_ids, final String before, final String after, final String limit) {
        return this.client.executeSync(this.spotOrderAPI.getAlgoOrder(instrument_id,order_type,status,algo_ids,before,after,limit));
    }

}
