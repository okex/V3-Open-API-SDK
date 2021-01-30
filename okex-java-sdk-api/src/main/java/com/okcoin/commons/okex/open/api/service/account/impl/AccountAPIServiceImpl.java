package com.okcoin.commons.okex.open.api.service.account.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.PurchaseRedempt;
import com.okcoin.commons.okex.open.api.bean.account.param.Transfer;
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;

import java.math.BigDecimal;
import java.util.List;

public class AccountAPIServiceImpl implements AccountAPIService {

    private APIClient client;
    private AccountAPI api;

    public AccountAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(AccountAPI.class);
    }

    //资金账户信息
    @Override
    public List<Wallet> getWallet() {
        return this.client.executeSync(this.api.getWallet());
    }

    //单一币种账户信息
    @Override
    public List<Wallet> getWallet(String currency) {
        return this.client.executeSync(this.api.getWallet(currency));
    }

    //资金划转
    @Override
    public JSONObject transfer(Transfer transfer) {
        return this.client.executeSync(this.api.transfer(JSONObject.parseObject(JSON.toJSONString(transfer))));
    }

    //提币
    @Override
    public JSONObject withdraw(Withdraw withdraw) {
        return this.client.executeSync(this.api.withdraw(JSONObject.parseObject(JSON.toJSONString(withdraw))));
    }

    //账单流水查询
    @Override
    public JSONArray getLedger(String currency, String after, String before, String limit, String type) {
        return this.client.executeSync(this.api.getLedger(currency, after, before, limit, type));
    }

    //获取充值地址
    @Override
    public JSONArray getDepositAddress(String currency) {
        return this.client.executeSync(this.api.getDepositAddress(currency));
    }

    //获取账户资产估值
    @Override
    public JSONObject getAllAccount(String account_type, String valuation_currency) {
        return this.client.executeSync(this.api.getAllAccount(account_type,valuation_currency));
    }

    //获取子账户余额
    @Override
    public String getSubAccount(String sub_account) {
        return this.client.executeSync(this.api.getSubAccount(sub_account));
    }

    //查询所有币种提币记录
    @Override
    public JSONArray getWithdrawalHistory() {
        return this.client.executeSync(this.api.getWithdrawalHistory());
    }

    //查询单个币种提币记录
    @Override
    public JSONArray getWithdrawalHistory(String currency) {
        return this.client.executeSync(this.api.getWithdrawalHistory(currency));
    }

    //获取所有币种充值记录
    @Override
    public String getDepositHistory() {
        return this.client.executeSync(this.api.getDepositHistory());
    }

    //获取单个币种充值记录
    @Override
    public String getDepositHistory(String currency) {
        return this.client.executeSync(this.api.getDepositHistory(currency));
    }

    //获取币种列表
    @Override
    public List<Currency> getCurrencies() {
        return this.client.executeSync(this.api.getCurrencies());
    }

    //提币手续费
    @Override
    public List<WithdrawFee> getWithdrawFee(String currency) {
        return this.client.executeSync(this.api.getWithdrawFee(currency));
    }

    //余币宝申购赎回
    @Override
    public JSONObject purchaseRedempt(PurchaseRedempt purchaseRedempt) {
        return this.client.executeSync(this.api.purchaseRedempt(JSONObject.parseObject(JSON.toJSONString(purchaseRedempt))));
    }

}
