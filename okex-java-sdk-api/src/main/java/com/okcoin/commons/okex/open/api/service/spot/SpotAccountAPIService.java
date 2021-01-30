package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.bean.spot.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.spot.result.ServerTimeDto;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * 币币资产相关接口
 */
public interface SpotAccountAPIService {

    //币币账户信息
    List<Account> getAccounts();

    //单一币种账户信息
    Account getAccountByCurrency(final String currency);

    //账单流水查询
    JSONArray getLedgersByCurrency(String currency, String before, String after, String limit,String type);

    //获取当前账户费率
    JSONObject getTradeFee(String category, String instrument_id);

}
