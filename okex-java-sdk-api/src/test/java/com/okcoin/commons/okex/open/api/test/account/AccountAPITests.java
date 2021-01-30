package com.okcoin.commons.okex.open.api.test.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.PurchaseRedempt;
import com.okcoin.commons.okex.open.api.bean.account.param.Transfer;
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class AccountAPITests extends  AccountAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(AccountAPITests.class);

    private AccountAPIService accountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.accountAPIService = new AccountAPIServiceImpl(this.config);
    }

    /**
     * 资金账户信息
     * GET /api/account/v3/wallet
     * 单一币种账户信息
     * GET /api/account/v3/wallet/<currency>
     */
    @Test
    public void getWallet() {
        //所有的资金账户信息
        List<Wallet> result = this.accountAPIService.getWallet();
        this.toResultString(AccountAPITests.LOG, "result", result);
        //单一币种账户信息
        /*List<Wallet> result2 = this.accountAPIService.getWallet("USDT");
        this.toResultString(AccountAPITests.LOG, "result", result2);*/
    }

    /**资金划转
     * POST /api/account/v3/transfer
     */
    @Test
    public void transfer() {
        Transfer transfer = new Transfer();
        transfer.setCurrency("OKB");
        transfer.setAmount("1");
        transfer.setType("0");
        transfer.setFrom("6");
        transfer.setTo("1");

//        transfer.setSub_account("ctt042501");
//        transfer.setInstrument_id("BTC-USDT");
//        transfer.setTo_instrument_id("BTC-USDT");

        JSONObject result = this.accountAPIService.transfer(transfer);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**提币
     * POST /api/account/v3/withdrawal
     */
    @Test
    public void withdraw() {
        Withdraw withdraw = new Withdraw();
        withdraw.setCurrency("OKB");
        withdraw.setAmount("2");
        withdraw.setDestination("3");
        withdraw.setTo_address("*******");
        withdraw.setTrade_pwd("*******");
        withdraw.setFee("0");

        JSONObject result = this.accountAPIService.withdraw(withdraw);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 账单流水查询
     * GET /api/account/v3/ledger
     */
    @Test
    public void getLedger() {
        JSONArray result = this.accountAPIService.getLedger("OKB",null,null,null,null);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 获取充值地址
     * GET /api/account/v3/deposit/address
     */
    @Test
    public void getDepositAddress() {
        JSONArray result = this.accountAPIService.getDepositAddress("XRP");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 获取账户资产估值
     * GET/api/account/v3/asset-valuation
     */
    @Test
    public void testGetAllAcccount(){
        JSONObject result = this.accountAPIService.getAllAccount("8","USDT");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 获取子账户余额信息
     * GET/api/account/v3/sub-account
     */
    @Test
    public void testGetSubAccount(){
        String result = this.accountAPIService.getSubAccount("ctt042501");
        this.toResultString(AccountAPITests.LOG, "result", result);

    }

    /**
     * 查询所有币种的提币记录
     * GET /api/account/v3/withdrawal/history
     * 查询单个币种的提币记录
     * GET /api/account/v3/withdrawal/history/<currency>
     */
    @Test
    public void getWithdrawalHistory() {
        /*JSONArray result = this.accountAPIService.getWithdrawalHistory();
        this.toResultString(AccountAPITests.LOG, "result", result);*/
        JSONArray result2 = this.accountAPIService.getWithdrawalHistory("OKB");
        this.toResultString(AccountAPITests.LOG, "result", result2);
    }

    /**
     * 获取所有币种充值记录
     * GET /api/account/v3/deposit/history
     * 获取单个币种充值记录
     * GET /api/account/v3/deposit/history/<currency>
     */
    @Test
    public void getDepositHistory() {
        String result = this.accountAPIService.getDepositHistory();
        this.toResultString(AccountAPITests.LOG, "result", result);
        /*String result2 = this.accountAPIService.getDepositHistory("USDT");
        this.toResultString(AccountAPITests.LOG, "result", result2);*/
    }

    /**
     * 获取币种列表
     * GET /api/account/v3/currencies
     */
    @Test
    public void getCurrencies() {
        List<Currency> result = this.accountAPIService.getCurrencies();
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 提币手续费
     * GET /api/account/v3/withdrawal/fee
     */
    @Test
    public void getWithdrawFee() {
        List<WithdrawFee> result = this.accountAPIService.getWithdrawFee("BTC");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    /**
     * 余币宝申购赎回
     * POST /api/account/v3/purchase_redempt
     */
    @Test
    public void testPurchaseRedempt() {
        PurchaseRedempt purchaseRedempt = new PurchaseRedempt();
        purchaseRedempt.setCurrency("USDT");
        purchaseRedempt.setAmount("1");
        purchaseRedempt.setSide("redempt");
        JSONObject result = this.accountAPIService.purchaseRedempt(purchaseRedempt);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

}
