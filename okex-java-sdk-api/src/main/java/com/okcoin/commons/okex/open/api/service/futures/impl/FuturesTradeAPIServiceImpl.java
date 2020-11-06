package com.okcoin.commons.okex.open.api.service.futures.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.AmendDateParam;
import com.okcoin.commons.okex.open.api.bean.futures.param.AmendOrder;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelAll;
import com.okcoin.commons.okex.open.api.bean.futures.param.CancelOrders;
import com.okcoin.commons.okex.open.api.bean.futures.param.ChangeLiquiMode;
import com.okcoin.commons.okex.open.api.bean.futures.param.ChangeMarginMode;
import com.okcoin.commons.okex.open.api.bean.futures.param.ClosePositions;
import com.okcoin.commons.okex.open.api.bean.futures.param.FuturesOrderParam;
import com.okcoin.commons.okex.open.api.bean.futures.param.ModifyFixedMargin;
import com.okcoin.commons.okex.open.api.bean.futures.param.ModifyMarginParam;
import com.okcoin.commons.okex.open.api.bean.futures.param.Order;
import com.okcoin.commons.okex.open.api.bean.futures.param.Orders;
import com.okcoin.commons.okex.open.api.bean.futures.param.OrdersItem;
import com.okcoin.commons.okex.open.api.bean.futures.result.CancelFuturesOrdeResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.CancelFuturesOrder;
import com.okcoin.commons.okex.open.api.bean.futures.result.FuturesOrderResult;
import com.okcoin.commons.okex.open.api.bean.futures.result.Holds;
import com.okcoin.commons.okex.open.api.bean.futures.result.OrderResult;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.futures.FuturesTradeAPIService;
import com.okcoin.commons.okex.open.api.utils.JsonUtils;

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

    @Override
    public JSONObject getPositions() {

        return this.client.executeSync(this.api.getPositions());
    }

    @Override
    public JSONObject getInstrumentPosition(String instrument_id) {
        return this.client.executeSync(this.api.getInstrumentPosition(instrument_id));
    }

    @Override
    public JSONObject getAccounts() {
        return this.client.executeSync(this.api.getAccounts());
    }

    @Override
    public JSONObject getAccountsByCurrency(String underlying) {
        return this.client.executeSync(this.api.getAccountsByCurrency(underlying));
    }

    @Override
    public JSONArray getAccountsLedgerByCurrency(String underlying,String after,String before,String limit,String type) {
        return this.client.executeSync(this.api.getAccountsLedgerByCurrency(underlying,after,before,limit,type));
    }

    @Override
    public JSONObject getAccountsHoldsByInstrumentId(String instrumentId) {
        return this.client.executeSync(this.api.getAccountsHoldsByInstrumentId(instrumentId));
    }

    @Override
    public OrderResult order(Order order) {
        //System.out.println(JsonUtils.convertObject(order, Order.class));
        return this.client.executeSync(this.api.order(JsonUtils.convertObject(order, Order.class)));
    }

    @Override
    public JSONObject orders(Orders orders) {
        JSONObject params = new JSONObject();
        params.put("instrument_id", orders.getInstrument_id());
        params.put("orders_data", JsonUtils.convertList(orders.getOrders_data(), OrdersItem.class));
        System.out.println(params.toString());
        return this.client.executeSync(this.api.orders(params));
    }

    @Override
    public JSONObject cancelOrderByOrderId(String instrument_id, String order_id) {
        return this.client.executeSync(this.api.cancelOrderByOrderId(instrument_id, order_id));
    }

    @Override
    public JSONObject cancelOrderByClientOid(String instrument_id, String client_oid) {
        return this.client.executeSync(this.api.cancelOrderByClientOid(instrument_id,client_oid));
    }

    @Override
    public JSONObject cancelOrders(String instrumentId, CancelOrders cancelOrders) {
        return this.client.executeSync(this.api.cancelOrders(instrumentId, JsonUtils.convertObject(cancelOrders, CancelOrders.class)));
    }

    @Override
    public JSONObject amendOrderByOrderId(String instrument_id, AmendOrder amendOrder) {
        return this.client.executeSync(this.api.amendOrderByOrderId(instrument_id, amendOrder));
    }

    @Override
    public JSONObject amendOrderByClientOId(String instrument_id, AmendOrder amendOrder) {
        return this.client.executeSync(this.api.amendOrderByClientOid(instrument_id, amendOrder));
    }

    @Override
    public JSONObject amendBatchOrdersByOrderId(String instrument_id, AmendDateParam amendOrder) {
        return this.client.executeSync(this.api.amendBatchOrdersByOrderId(instrument_id, amendOrder));
    }

    @Override
    public JSONObject amendBatchOrdersByClientOid(String instrument_id, AmendDateParam amendOrder) {
        return this.client.executeSync(this.api.amendBatchOrdersByClientOid(instrument_id, amendOrder));
    }

    @Override
    public JSONObject getOrders(String instrument_id, String state, String after, String before, String limit) {
        return this.client.executeSync(this.api.getOrders(instrument_id, state, after, before, limit));
    }

    @Override
    public JSONObject getOrderByOrderId(String instrumentId, String orderId) {
        return this.client.executeSync(this.api.getOrderByOrderId(instrumentId,orderId));
    }

    @Override
    public JSONObject getOrderByClientOid(String instrumentId,String client_oid) {
        return this.client.executeSync(this.api.getOrderByClientOid(instrumentId,client_oid));
    }

    @Override
    public JSONArray getFills(String instrument_id, String order_id, String before, String after, String limit) {
        return this.client.executeSync(this.api.getFills(instrument_id, String.valueOf(order_id), before, after, limit));
    }

    @Override
    public JSONObject getInstrumentLeverRate(String underlying) {
        return this.client.executeSync(this.api.getLeverRate(underlying));
    }

    @Override
    public JSONObject changeLeverageOnFixed(String underlying, String instrument_id, String direction, String leverage) {
        JSONObject params = new JSONObject();
        params.put("instrument_id", instrument_id);
        params.put("direction", direction);
        params.put("leverage", leverage);
        return this.client.executeSync(this.api.changeLeverageOnFixed(underlying, params));
    }

    @Override
    public JSONObject changeLeverageOnCross(String underlying, String leverage) {
        JSONObject params = new JSONObject();
        params.put("leverage", leverage);
        return this.client.executeSync(this.api.changeLeverageOnCross(underlying, params));
    }

    @Override
    public JSONObject closePositions(ClosePositions closePositions) {
        return this.client.executeSync(this.api.closePositions(JsonUtils.convertObject(closePositions,ClosePositions.class)));
    }

    @Override
    public JSONObject cancelAll(CancelAll cancelAll) {
        return this.client.executeSync(this.api.cancelAll(JsonUtils.convertObject(cancelAll,CancelAll.class)));
    }

    @Override
    public JSONObject changeMarginMode(ChangeMarginMode changeMarginMode ) {
        return this.client.executeSync(this.api.changeMarginMode(JsonUtils.convertObject(changeMarginMode,ChangeMarginMode.class )));
    }

    @Override
    public JSONObject changeLiquiMode(ChangeLiquiMode changeLiquiMode ) {
        return this.client.executeSync(this.api.changeLiquiMode(JsonUtils.convertObject(changeLiquiMode,ChangeLiquiMode.class )));
    }

    @Override
    public FuturesOrderResult futuresOrder(FuturesOrderParam futuresOrderParam) {
        System.out.println(JsonUtils.convertObject(futuresOrderParam, FuturesOrderParam.class));
        return this.client.executeSync(this.api.futuresOrder(futuresOrderParam));
    }

    @Override
    public CancelFuturesOrdeResult cancelFuturesOrder(CancelFuturesOrder cancelFuturesOrder) {
        System.out.println(JsonUtils.convertObject(cancelFuturesOrder, CancelFuturesOrder.class));
        return this.client.executeSync(this.api.cancelFuturesOrder(cancelFuturesOrder));
    }

    @Override
    public String findFuturesOrder(String instrument_id, String order_type,String status,String algo_id, String after, String before, String limit) {
        return this.client.executeSync(this.api.findFuturesOrder(instrument_id,order_type,status,algo_id,after,before,limit));
    }

    @Override
    public JSONObject getTradeFee() {
        return this.client.executeSync(this.api.getTradeFee("1"));
    }

    @Override
    public JSONObject modifyMargin(ModifyMarginParam modifyMarginParam) {
        return this.client.executeSync(this.api.modifyMargin(modifyMarginParam));
    }

    @Override
    public JSONObject modifyFixedMargin(ModifyFixedMargin modifyFixedMargin) {
        return this.client.executeSync(this.api.modifyFixedMargin(modifyFixedMargin));
    }

    @Override
    public Holds getHolds(String instrument_id) {
        return this.client.executeSync(this.api.getHolds(instrument_id));
    }

}
