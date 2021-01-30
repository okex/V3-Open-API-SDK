package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelAll;
import com.okcoin.commons.okex.open.api.bean.futures.param.*;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

import java.util.List;

/**
 * Futures trade api
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 18:52
 */
public class FuturesTradeAPIServiceImpl implements FuturesTradeAPIService {

    private APIClient client;
    private FuturesTradeAPI api;

    public FuturesTradeAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(FuturesTradeAPI.class);
    }

    //所有合约持仓信息
    @Override
    public JSONObject getPositions() {
        return this.client.executeSync(this.api.getPositions());
    }

    //单个合约持仓信息
    @Override
    public JSONObject getPositionByInstrumentId(String instrument_id) {
        return this.client.executeSync(this.api.getPositionByInstrumentId(instrument_id));
    }

    //所有币种合约账户信息
    @Override
    public JSONObject getAccounts() {
        return this.client.executeSync(this.api.getAccounts());
    }

    //单个币种合约账户信息
    @Override
    public JSONObject getAccountsByUnderlying(String underlying) {
        return this.client.executeSync(this.api.getAccountsByUnderlying(underlying));
    }

    //获取合约币种杠杆倍数
    @Override
    public JSONObject getLeverage(String underlying) {
        return this.client.executeSync(this.api.getLeverage(underlying));
    }

    //设定合约币种杠杆倍数(逐仓)
    @Override
    public JSONObject setLeverageOnFixed(String underlying, String instrument_id, String direction, String leverage) {
        JSONObject params = new JSONObject();
        params.put("instrument_id", instrument_id);
        params.put("direction", direction);
        params.put("leverage", leverage);
        return this.client.executeSync(this.api.setLeverageOnFixed(underlying, params));
    }

    //设定合约币种杠杆倍数(全仓)
    @Override
    public JSONObject setLeverageOnCross(String underlying, String leverage) {
        JSONObject params = new JSONObject();
        params.put("leverage", leverage);
        return this.client.executeSync(this.api.setLeverageOnCross(underlying, params));
    }

    //账单流水查询
    @Override
    public JSONArray getAccountsLedgerByUnderlying(String underlying,String after,String before,String limit,String type) {
        return this.client.executeSync(this.api.getAccountsLedgerByUnderlying(underlying,after,before,limit,type));
    }

    //下单
    @Override
    public OrderResult order(Order order) {
        //System.out.println(JsonUtils.convertObject(order, Order.class));
        return this.client.executeSync(this.api.order(JsonUtils.convertObject(order, Order.class)));
    }

    //批量下单
    @Override
    public JSONObject orders(Orders orders) {
        JSONObject params = new JSONObject();
        params.put("instrument_id", orders.getInstrument_id());
        params.put("orders_data", JsonUtils.convertList(orders.getOrders_data(), OrdersItem.class));
        System.out.println(params.toString());
        return this.client.executeSync(this.api.orders(params));
    }

    //撤销指定订单(通过order_id)
    @Override
    public JSONObject cancelOrderByOrderId(String instrument_id, String order_id) {
        return this.client.executeSync(this.api.cancelOrderByOrderId(instrument_id, order_id));
    }

    //撤销指定订单(通过client_oid)
    @Override
    public JSONObject cancelOrderByClientOid(String instrument_id, String client_oid) {
        return this.client.executeSync(this.api.cancelOrderByClientOid(instrument_id,client_oid));
    }

    //批量撤销订单(通过order_id)
    @Override
    public JSONObject cancelOrdersByOrderId(String instrumentId, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelOrdersByOrderId(instrumentId, JsonUtils.convertObject(cancelOrders, CancelOrders.class)));
    }

    //批量撤销订单(通过client_oid)
    @Override
    public JSONObject cancelOrdersByClientOid(String instrument_id, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelOrdersByClientOid(instrument_id, JsonUtils.convertObject(cancelOrders, CancelOrders.class)));
    }

    //修改订单(通过order_id)
    @Override
    public JSONObject amendOrderByOrderId(String instrument_id, AmendOrder amendOrder) {
        return this.client.executeSync(this.api.amendOrderByOrderId(instrument_id, amendOrder));
    }

    //修改订单（通过client_oid)
    @Override
    public JSONObject amendOrderByClientOId(String instrument_id, AmendOrder amendOrder) {
        return this.client.executeSync(this.api.amendOrderByClientOid(instrument_id, amendOrder));
    }

    //批量修改订单(通过order_id)
    @Override
    public JSONObject amendBatchOrdersByOrderId(String instrument_id, AmendDateParam amendOrder) {
        return this.client.executeSync(this.api.amendBatchOrdersByOrderId(instrument_id, amendOrder));
    }

    //批量修改订单（通过client_oid)
    @Override
    public JSONObject amendBatchOrdersByClientOid(String instrument_id, AmendDateParam amendOrder) {
        return this.client.executeSync(this.api.amendBatchOrdersByClientOid(instrument_id, amendOrder));
    }

    //获取订单列表
    @Override
    public JSONObject getOrders(String instrument_id, String state, String after, String before, String limit) {
        return this.client.executeSync(this.api.getOrders(instrument_id, state, after, before, limit));
    }

    //获取订单信息(通过order_id)
    @Override
    public JSONObject getOrderByOrderId(String instrumentId, String orderId) {
        return this.client.executeSync(this.api.getOrderByOrderId(instrumentId,orderId));
    }

    //获取订单信息(通过client_oid)
    @Override
    public JSONObject getOrderByClientOid(String instrumentId,String client_oid) {
        return this.client.executeSync(this.api.getOrderByClientOid(instrumentId,client_oid));
    }

    //获取成交明细
    @Override
    public JSONArray getFills(String instrument_id, String order_id, String before, String after, String limit) {
        return this.client.executeSync(this.api.getFills(instrument_id, String.valueOf(order_id), before, after, limit));
    }

    //设置合约币种账户模式
    @Override
    public JSONObject changeMarginMode(ChangeMarginMode changeMarginMode ) {
        return this.client.executeSync(this.api.changeMarginMode(JsonUtils.convertObject(changeMarginMode,ChangeMarginMode.class )));
    }

    //市价全平
    @Override
    public JSONObject closePositions(ClosePositions closePositions) {
        return this.client.executeSync(this.api.closePositions(JsonUtils.convertObject(closePositions,ClosePositions.class)));
    }

    //撤销所有平仓挂单
    @Override
    public JSONObject cancelAll(CancelAll cancelAll) {
        return this.client.executeSync(this.api.cancelAll(JsonUtils.convertObject(cancelAll,CancelAll.class)));
    }

    //获取合约挂单冻结数量
    @Override
    public JSONObject getAccountsHoldsByInstrumentId(String instrumentId) {
        return this.client.executeSync(this.api.getAccountsHoldsByInstrumentId(instrumentId));
    }

    //策略委托下单
    @Override
    public FuturesOrderResult futuresOrder(FuturesOrderParam futuresOrderParam) {
        System.out.println(JsonUtils.convertObject(futuresOrderParam, FuturesOrderParam.class));
        return this.client.executeSync(this.api.futuresOrder(futuresOrderParam));
    }

    //策略委托撤单
    @Override
    public CancelFuturesOrdeResult cancelFuturesOrder(CancelFuturesOrder cancelFuturesOrder) {
        System.out.println(JsonUtils.convertObject(cancelFuturesOrder, CancelFuturesOrder.class));
        return this.client.executeSync(this.api.cancelFuturesOrder(cancelFuturesOrder));
    }

    //获取委托单列表
    @Override
    public String findFuturesOrder(String instrument_id, String order_type,String status,String algo_id, String after, String before, String limit) {
        return this.client.executeSync(this.api.findFuturesOrder(instrument_id,order_type,status,algo_id,after,before,limit));
    }

    //获取当前手续费费率
    @Override
    public JSONObject getTradeFee(String category,String underlying) {
        return this.client.executeSync(this.api.getTradeFee(category,underlying));
    }

    //增加/减少保证金
    @Override
    public JSONObject modifyMargin(ModifyMarginParam modifyMarginParam) {
        return this.client.executeSync(this.api.modifyMargin(modifyMarginParam));
    }

    //设置逐仓自动追加保证金
    @Override
    public JSONObject modifyFixedMargin(ModifyFixedMargin modifyFixedMargin) {
        return this.client.executeSync(this.api.modifyFixedMargin(modifyFixedMargin));
    }

}
