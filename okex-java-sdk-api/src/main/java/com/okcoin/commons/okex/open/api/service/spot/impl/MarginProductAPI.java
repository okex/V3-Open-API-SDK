package com.okcoin.commons.okex.open.api.service.spot.impl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MarginProductAPI {

    //公共-获取标记价格
    @GET("/api/margin/v3/instruments/{instrument_id}/mark_price")
    Call<String> getMarginMarkPrice(@Path("instrument_id") String instrument_id);

}
