package com.okcoin.commons.okex.open.api.service.system.impl;

import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.index.IndexMarketAPIService;
import com.okcoin.commons.okex.open.api.service.system.SystemMarketAPIService;

public class SystemMarketAPIServiceImpl implements SystemMarketAPIService {
    private final APIClient client;
    private final SystemMarketAPI systemMarketAPI;

    public SystemMarketAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.systemMarketAPI = this.client.createService(SystemMarketAPI.class);
    }

    //获取系统升级状态
    @Override
    public String getMaintenance(String status) {
        return this.client.executeSync(this.systemMarketAPI.getMaintenance(status));
    }

}
