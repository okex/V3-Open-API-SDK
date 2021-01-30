package com.okcoin.commons.okex.open.api.service.spot;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.spot.param.MarginLeverage;
import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * 杠杆资产相关接口
 */
public interface MarginAccountAPIService {

    //币币杠杆账户信息
    List<Map<String, Object>> getAccounts();

    //单一币对账户信息
    Map<String, Object> getAccountsByInstrumentId(final String instrument_id);

    //账单流水查询
    List<UserMarginBillDto> getLedger(final String instrument_id, final String after, final String before, String limit, final String type);

    //杠杆配置信息
    List<Map<String, Object>> getAvailability();

    //某个杠杆配置信息
    List<Map<String, Object>> getAvailabilityByInstrumentId(final String instrument_id);

    //获取借币记录
    List<MarginBorrowOrderDto> getBorrowedAccounts(final String status, final String after, final String before, String limit);

    //某币对借币记录
    List<MarginBorrowOrderDto> getBorrowedAccountsByInstrumentId(final String instrument_id,
                                                              final String status,
                                                              final String after,
                                                              final String before,
                                                              final String limit);

    //借币
    BorrowResult borrow(BorrowRequestDto order);

    //还币
    RepaymentResult repayment(RepaymentRequestDto order);

    //获取杠杆倍数
    JSONObject getLeverage(String leverage);

    //设置杠杆倍数
    JSONObject setLeverage(String instrument_id, MarginLeverage leverage);

}
