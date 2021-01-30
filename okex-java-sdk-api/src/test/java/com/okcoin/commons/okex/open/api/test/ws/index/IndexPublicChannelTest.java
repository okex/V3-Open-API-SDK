package com.okcoin.commons.okex.open.api.test.ws.index;


import com.okcoin.commons.okex.open.api.test.ws.index.config.WebSocketClient;
import com.okcoin.commons.okex.open.api.test.ws.index.config.WebSocketConfig;
import org.apache.commons.compress.utils.Lists;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

public class IndexPublicChannelTest {
    private static final WebSocketClient webSocketClient = new WebSocketClient();
    private static Logger logger = Logger.getLogger(IndexPublicChannelTest.class);

    @Before
    public void connect() {
        //与服务器建立连接
        WebSocketConfig.publicConnect(webSocketClient);
    }

    @After
    public void close() {
        System.out.println(Instant.now().toString() + " close connect!");
        WebSocketClient.closeConnection();
    }

    /**
     * 指数行情
     * index/ticker Channel
     */
    @Test
    public void indexTickerChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("index/ticker:BTC-USD");
        //调用订阅方法
        WebSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指数K线
     * index/candle Channel
     */
    @Test
    public void indexCandleChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("index/candle60s:BTC-USD");
        //调用订阅方法
        WebSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //取消订阅
    @Test
    public void unsubscribeChannel() {
        ArrayList<String> list = Lists.newArrayList();
        //添加要取消订阅的频道名
        list.add("index/candle60s:BTC-USD");
        WebSocketClient.unsubscribe(list);
        //为保证收到服务端返回的消息，需要让线程延迟
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
