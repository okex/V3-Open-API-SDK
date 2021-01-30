package com.okcoin.commons.okex.open.api.service.index.impl;

import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.index.IndexMarketAPIService;

public class IndexMarketAPIServiceImpl implements IndexMarketAPIService {
    private final APIClient client;
    private final IndexMarketAPI indexMarketAPI;

    public IndexMarketAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.indexMarketAPI = this.client.createService(IndexMarketAPI.class);
    }

    //公共-获取指数成分
    @Override
    public String getIndex(String instrument_id) {
        return this.client.executeSync(this.indexMarketAPI.getIndex(instrument_id));
    }

}
