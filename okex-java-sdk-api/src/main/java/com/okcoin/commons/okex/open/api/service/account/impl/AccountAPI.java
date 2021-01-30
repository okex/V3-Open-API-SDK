package com.okcoin.commons.okex.open.api.service.account.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Account api
 *
 * @author hucj
 * @version 1.0.0
 * @date 2018/07/04 20:51
 */
public interface AccountAPI {
    //资金账户信息
    @GET("/api/account/v3/wallet")
    Call<List<Wallet>> getWallet();

    //单个币种账户信息
    @GET("/api/account/v3/wallet/{currency}")
    Call<List<Wallet>> getWallet(@Path("currency") String currency);

    //资金划转
    @POST("/api/account/v3/transfer")
    Call<JSONObject> transfer(@Body JSONObject jsonObject);

    //提币
    @POST("/api/account/v3/withdrawal")
    Call<JSONObject> withdraw(@Body JSONObject jsonObject);

    //账单流水查询
    @GET("/api/account/v3/ledger")
    Call<JSONArray> getLedger(@Query("currency") String currency,@Query("after") String after,
                              @Query("before") String before,  @Query("limit") String limit,@Query("type") String type);

    //获取充值地址
    @GET("/api/account/v3/deposit/address")
    Call<JSONArray> getDepositAddress(@Query("currency") String currency);

    //获取账户资产估值
    @GET("/api/account/v3/asset-valuation")
    Call<JSONObject> getAllAccount(@Query("account_type") String account_type,
                                   @Query("valuation_currency") String valuation_currency);

    //获取子账户余额信息
    @GET("/api/account/v3/sub-account")
    Call<String> getSubAccount(@Query("sub-account") String sub_account);

    //查询所有币种提币记录
    @GET("/api/account/v3/withdrawal/history")
    Call<JSONArray> getWithdrawalHistory();

    //查看单个币种提币记录
    @GET("/api/account/v3/withdrawal/history/{currency}")
    Call<JSONArray> getWithdrawalHistory(@Path("currency") String currency);

    //获取所有币种充值记录
    @GET("/api/account/v3/deposit/history")
    Call<String> getDepositHistory();

    //获取单个币种充值记录
    @GET("/api/account/v3/deposit/history/{currency}")
    Call<String> getDepositHistory(@Path("currency") String currency);

    //获取币种列表
    @GET("/api/account/v3/currencies")
    Call<List<Currency>> getCurrencies();

    //提币手续费
    @GET("/api/account/v3/withdrawal/fee")
    Call<List<WithdrawFee>> getWithdrawFee(@Query("currency") String currency);

    //余币宝申购赎回
    @POST("/api/account/v3/purchase_redempt")
    Call<JSONObject> purchaseRedempt(@Body JSONObject jsonObject);

}
