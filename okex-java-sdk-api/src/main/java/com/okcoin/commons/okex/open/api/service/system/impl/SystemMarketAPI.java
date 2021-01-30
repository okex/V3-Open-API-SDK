package com.okcoin.commons.okex.open.api.service.system.impl;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface SystemMarketAPI {

    //获取系统升级状态
    @GET("/api/system/v3/maintenance")
    Call<String> getMaintenance(@Query("status") String status);

}
