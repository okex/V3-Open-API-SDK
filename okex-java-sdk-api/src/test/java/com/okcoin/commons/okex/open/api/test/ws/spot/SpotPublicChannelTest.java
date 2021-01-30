package com.okcoin.commons.okex.open.api.test.ws.spot;

import com.okcoin.commons.okex.open.api.test.ws.spot.config.WebSocketClient;
import com.okcoin.commons.okex.open.api.test.ws.spot.config.WebSocketConfig;
import com.okcoin.commons.okex.open.api.utils.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * 公共频道
 * public channel
 *
 * @author oker
 * @create 2019-06-12 14:45
 **/
public class SpotPublicChannelTest {

    private static final WebSocketClient webSocketClient = new WebSocketClient();

    @Before
    public void connect() {
        WebSocketConfig.publicConnect(webSocketClient);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        System.out.println(DateFormatUtils.format(new Date() , DateUtils.TIME_STYLE_S4) + " close connect!");
        webSocketClient.closeConnection();
    }

    /**
     * 公共-ticker频道
     * Ticker Channel
     */
    @Test
    public void tickerChannel() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("spot/ticker:ETH-USDT");
        webSocketClient.subscribe(list);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共-k线频道
     * Kline Channel
     */
    @Test
    public void klineChannel() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("spot/candle60s:ETH-USDT");
        webSocketClient.subscribe(list);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共-交易频道
     * Trade Channel
     */
    @Test
    public void tradeChannel() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("spot/trade:BTC-USDT");
        webSocketClient.subscribe(list);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共-5档深度
     * Depth5 Channel
     */
    @Test
    public void depth5Channel() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("spot/depth5:ETH-USDT");
        webSocketClient.subscribe(list);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共-400档深度
     * Depth Channel
     */
    @Test
    public void depthChannel() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("spot/depth:ETC-USDT");
        webSocketClient.subscribe(list);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共-400档增量数据频道
     * Depth Channel
     */
    @Test
    public void allDepthChannel() {
        final ArrayList<String> list = new ArrayList<>();
        list.add("spot/depth_l2_tbt:BTM-ETH");
        webSocketClient.subscribe(list);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
