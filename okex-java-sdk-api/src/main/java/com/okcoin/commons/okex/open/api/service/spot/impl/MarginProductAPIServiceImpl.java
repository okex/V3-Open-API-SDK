package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.MarginProductAPIService;

public class MarginProductAPIServiceImpl implements MarginProductAPIService {
    private final APIClient client;
    private final MarginProductAPI marginProductAPI;

    public MarginProductAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.marginProductAPI = this.client.createService(MarginProductAPI.class);
    }

    //公共-获取标记价格
    @Override
    public String getMarginMarkPrice(String instrument_id) {
        return this.client.executeSync(this.marginProductAPI.getMarginMarkPrice(instrument_id));

    }

}