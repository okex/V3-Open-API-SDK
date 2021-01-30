package com.okcoin.commons.okex.open.api.service.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;
import retrofit2.http.Query;

public interface SwapUserAPIServive {

    //所有合约持仓信息
    String getPositions();

    //单个合约持仓信息
    String getPosition(String instrument_id);

    //所有币种合约账户信息
    String getAccounts();

    //某个币种合约账户信息
    String selectAccount(String instrument_id);

    //获取某个合约的用户配置
    String selectContractSettings(String instrument_id);

    //设定某个合约的杠杆
    String updateLevelRate(String instrument_id, LevelRateParam levelRateParam);

    //账单流水查询
    String getLedger(String instrument_id, String after, String before, String limit,String type);

    //获取所有订单列表
    String selectOrders(String instrument_id, String after, String before, String limit,String state);

    //获取订单信息(通过order_id)
    String selectOrderByOrderId(String instrument_id, String order_id);

    //获取订单信息(通过client_oid)
    String selectOrderByClientOid(String instrument_id, String client_oid);

    //获取成交明细
    String selectDealDetail(String instrument_id, String order_id, String before, String after, String limit);

    //获取合约挂单冻结数量
    String getHolds(String instrument_id);

    //当前账户交易手续等级的费率
    String getTradeFee(String category, String instrument_id);

}
