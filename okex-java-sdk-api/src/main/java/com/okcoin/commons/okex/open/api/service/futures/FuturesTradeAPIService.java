package com.okcoin.commons.okex.open.api.service.futures;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.okcoin.commons.okex.open.api.bean.futures.param.*;
import com.okcoin.commons.okex.open.api.bean.futures.result.*;
import retrofit2.http.Body;
import retrofit2.http.Query;

import java.util.List;

/**
 * Futures Trade API Service
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/9 18:52
 */
public interface FuturesTradeAPIService {

    //所有合约持仓信息
    JSONObject getPositions();

    //单个合约持仓信息
    JSONObject getPositionByInstrumentId(String instrument_id);

    //所有币种合约账户信息
    JSONObject getAccounts();

    //单个币种合约账户信息
    JSONObject getAccountsByUnderlying(String underlying);

    //获取合约币种杠杆倍数
    JSONObject getLeverage(String underlying);

    //设定合约币种杠杆倍数(逐仓)
    JSONObject setLeverageOnFixed(String underlying,String instrument_id,String direction, String leverage);

    //设定合约币种杠杆倍数(全仓)
    JSONObject setLeverageOnCross(String underlying,String leverage);

    //账单流水查询
    JSONArray getAccountsLedgerByUnderlying(String underlying,String after,String before,String limit,String type);

    //下单
    OrderResult order(Order order);

    //批量下单
    JSONObject orders(Orders orders);

    //撤销指定订单(通过order_id)
    JSONObject cancelOrderByOrderId(String instrument_id, String order_id);

    //撤销指定订单(通过client_oid)
    JSONObject cancelOrderByClientOid(String instrument_id, String client_oid);

    //批量撤销订单(通过order_id)
    JSONObject cancelOrdersByOrderId(String instrument_id, CancelOrders cancelOrders);

    //批量撤销订单(通过client_oid)
    JSONObject cancelOrdersByClientOid(String instrument_id, CancelOrders cancelOrders);

    //修改订单(通过order_id)
    JSONObject amendOrderByOrderId(String instrument_id,AmendOrder amendOrder);

    //修改订单（通过client_oid)
    JSONObject amendOrderByClientOId(String instrument_id,AmendOrder amendOrder);

    //批量修改订单(通过order_id)
    JSONObject amendBatchOrdersByOrderId(String instrument_id, AmendDateParam amendOrder);

    //批量修改订单（通过client_oid)
    JSONObject amendBatchOrdersByClientOid(String instrument_id, AmendDateParam amendOrder);

    //获取订单列表
    JSONObject getOrders(String instrument_id, String state, String after, String before, String limit);

    //获取订单信息(通过order_id)
    JSONObject getOrderByOrderId(String instrument_id,String order_id);

    //获取订单信息(通过client_oid)
    JSONObject getOrderByClientOid(String instrument_id,String client_oid);

    //获取成交明细
    JSONArray getFills(String order_id, String instrument_id, String after, String before, String limit);

    //设置合约币种账户模式
    JSONObject changeMarginMode(ChangeMarginMode changeMarginMode);

    //市价全平
    JSONObject closePositions(ClosePositions closePositions);

    //撤销所有平仓挂单
    JSONObject cancelAll(CancelAll cancelAll);

    //获取合约挂单冻结数量
    JSONObject getAccountsHoldsByInstrumentId(String instrumentId);

    //策略委托下单
    FuturesOrderResult futuresOrder(@Body FuturesOrderParam futuresOrderParam);

    //策略委托撤单
    CancelFuturesOrdeResult cancelFuturesOrder(@Body CancelFuturesOrder cancelFuturesOrder);

    //获取委托单列表
    String findFuturesOrder( String instrument_id,
                             String order_type,
                             String status,
                             String algo_id,
                             String before,
                             String after,
                             String limit);

    //获取当前手续费费率
    JSONObject getTradeFee(String category,String underlying);

    //增加/减少保证金
    JSONObject modifyMargin(ModifyMarginParam modifyMarginParam);

    //设置逐仓自动追加保证金
    JSONObject modifyFixedMargin(ModifyFixedMargin modifyFixedMargin);

}
