package com.okcoin.commons.okex.open.api.test.futures;

import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.enums.FuturesCurrenciesEnum;
import com.okcoin.commons.okex.open.api.enums.FuturesDirectionEnum;
import com.okcoin.commons.okex.open.api.enums.I18nEnum;
import com.okcoin.commons.okex.open.api.test.BaseTests;

/**
 * Futures api basetests
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/3/13 18:23
 */
public class FuturesAPIBaseTests extends BaseTests {

    public APIConfiguration config() {
        APIConfiguration config = new APIConfiguration();

        config.setEndpoint("https://www.okex.com/");

        config.setApiKey("");
        config.setSecretKey("");
        config.setPassphrase("");

        config.setPrint(true);
        config.setI18n(I18nEnum.SIMPLIFIED_CHINESE);
//        config.setI18n(I18nEnum.ENGLISH);
        return config;
    }

    /**
     * Public parameters
     */
    int from = 0;
    int to = 0;
    int limit = 20;

    /*String instrument_id = "BTC-USD-191108";
    String currency = FuturesCurrenciesEnum.BTC.name();
    String direction = FuturesDirectionEnum.LONG.getDirection();
    String leverage = "22.22";*/

}
