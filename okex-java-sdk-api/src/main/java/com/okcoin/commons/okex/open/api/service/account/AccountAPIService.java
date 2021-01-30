package com.okcoin.commons.okex.open.api.service.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.PurchaseRedempt;
import com.okcoin.commons.okex.open.api.bean.account.param.Transfer;
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import retrofit2.http.Query;

import java.math.BigDecimal;
import java.util.List;


public interface AccountAPIService {
    //资金账户信息
    List<Wallet> getWallet();

    //单一币种账户信息
    List<Wallet> getWallet(String currency);

    //资金划转
    JSONObject transfer(Transfer transfer);

    //提币
    JSONObject withdraw(Withdraw withdraw);

    //账单流水查询
    JSONArray getLedger(String currency, String after, String before, String limit, String type);

    //获取充值地址
    JSONArray getDepositAddress(String currency);

    //获取账户资产估值
    JSONObject getAllAccount(String account_type,String valuation_currency);

    //获取子账户余额信息
    String getSubAccount(String sub_account);

    //查看所有币种提币记录
    JSONArray getWithdrawalHistory();

    //查看单个币种提币记录
    JSONArray getWithdrawalHistory(String currency);

    //获取所有币种充值记录
    String getDepositHistory();

    //获取单个币种充值记录
    String getDepositHistory(String currency);

    //获取币种列表
    List<Currency> getCurrencies();

    //提币手续费
    List<WithdrawFee> getWithdrawFee(String currency);

    //余币宝申购赎回
    JSONObject purchaseRedempt(PurchaseRedempt purchaseRedempt);

}
