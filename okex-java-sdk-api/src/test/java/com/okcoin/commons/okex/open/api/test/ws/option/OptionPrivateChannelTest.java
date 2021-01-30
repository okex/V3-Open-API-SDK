package com.okcoin.commons.okex.open.api.test.ws.option;


import com.okcoin.commons.okex.open.api.test.ws.option.config.WebSocketClient;
import com.okcoin.commons.okex.open.api.test.ws.option.config.WebSocketConfig;
import org.apache.commons.compress.utils.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

/**
 * 需要登录的频道
 * private channel
 *
 * @author oker
 * @date 2019/7/5 1:36 AM
 */
public class OptionPrivateChannelTest {

    private static final WebSocketClient webSocketClient = new WebSocketClient();

    @Before
    public void connect() {
        WebSocketConfig.loginConnect(webSocketClient);
        while (true) {
            if (webSocketClient.getIsLogin()) {
                return;
            } else {
                try {
                    Thread.sleep(200);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                if (!webSocketClient.getIsConnect()) {
                    return;
                }
            }
        }
    }

    @After
    public void close() {
        System.out.println(Instant.now().toString() + " close connect!");
        WebSocketClient.closeConnection();
    }

    /**
     * 用户持仓频道
     * Position Channel
     */
    @Test
    public void positionChannel() {
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("option/position:BTC-USD");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //订阅
        WebSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户账户频道
     * Account Channel
     */
    @Test
    public void accountChannel() {
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("option/account:BTC-USD");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //订阅
        WebSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户交易频道
     * Order Channel
     */
    @Test
    public void orderChannel() {
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("option/order:BTC-USD");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //订阅
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
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("option/order:BTC-USD");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //取消订阅
        WebSocketClient.unsubscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
