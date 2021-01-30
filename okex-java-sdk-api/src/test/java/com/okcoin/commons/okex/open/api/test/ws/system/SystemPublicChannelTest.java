package com.okcoin.commons.okex.open.api.test.ws.system;


import com.okcoin.commons.okex.open.api.test.ws.system.config.WebSocketConfig;
import com.okcoin.commons.okex.open.api.test.ws.system.config.WebSocketClient;
import org.apache.commons.compress.utils.Lists;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

public class SystemPublicChannelTest {

    private static final WebSocketClient webSocketClient = new WebSocketClient();

    private static Logger logger = Logger.getLogger(SystemPublicChannelTest.class);

    @Before
    public void connect(){
        WebSocketConfig.publicConnect(webSocketClient);

    }

    @After
    public void close() {
        System.out.println(Instant.now().toString() + " close connect!");
        WebSocketClient.closeConnection();
    }

    /**
     * 获取系统升级状态
     * status channel
     */
    @Test
    public void systemStatusChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("system/status");

        //调用订阅方法
        WebSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(10000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
