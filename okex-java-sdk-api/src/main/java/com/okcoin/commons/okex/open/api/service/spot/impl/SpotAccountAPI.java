package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.bean.spot.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.spot.result.ServerTimeDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface SpotAccountAPI {

    //币币账户信息
    @GET("api/spot/v3/accounts")
    Call<List<Account>> getAccounts();

    //单一币种账户信息
    @GET("api/spot/v3/accounts/{currency}")
    Call<Account> getAccountByCurrency(@Path("currency") String currency);

    //账单流水查询
    @GET("api/spot/v3/accounts/{currency}/ledger")
    Call<JSONArray> getLedgersByCurrency(@Path("currency") String currency,
                                         @Query("after") String after,
                                         @Query("before") String before,
                                         @Query("limit") String limit,
                                         @Query("type") String type);

    //获取当前账户费率
    @GET("/api/spot/v3/trade_fee")
    Call<JSONObject> getTradeFee(@Query("category") String category, @Query("instrument_id") String instrument_id);

}
