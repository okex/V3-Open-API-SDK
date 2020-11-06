package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotAccountAPI {


    /**
     * 挖矿相关数据
     *
     * @return
     */
    @GET("api/spot/v3/miningdata")
    Call<Map<String, Object>> getMiningdata();

    /**
     * 币币资产
     *
     * @return
     */
    @GET("api/spot/v3/accounts")
    Call<List<Account>> getAccounts();

    /**
     * 币币指定币资产
     *
     * @param currency
     * @return
     */
    @GET("api/spot/v3/accounts/{currency}")
    Call<Account> getAccountByCurrency(@Path("currency") String currency);

    /**
     * 币币账单流水
     * @param currency
     * @param before
     * @param after
     * @param limit
     * @param type
     * @return
     */
    @GET("api/spot/v3/accounts/{currency}/ledger")
    Call<JSONArray> getLedgersByCurrency(@Path("currency") String currency,
                                         @Query("before") String before,
                                         @Query("after") String after,
                                         @Query("limit") String limit,
                                         @Query("type") String type);

    //当前账户交易手续等级的费率
    @GET("/api/spot/v3/trade_fee")
    Call<JSONObject> getTradeFee(@Query("category") String category);

}
