package com.okcoin.commons.okex.open.api.test.ws.swap;

import com.okcoin.commons.okex.open.api.test.ws.swap.config.WebSocketClient;
import com.okcoin.commons.okex.open.api.test.ws.swap.config.WebSocketConfig;
import org.apache.commons.compress.utils.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;

public class SwapPublicChannelTest {

    private static final WebSocketClient webSocketClient = new WebSocketClient();

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
     * 公共-Ticker频道
     * Ticker Channel
     */
    @Test
    public void tickerChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/ticker:ETH-USD-SWAP");
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
     * 公共-k线频道
     * 频道列表：
     * swap/candle60s // 1分钟k线数据频道
     swap/candle180s // 3分钟k线数据频道
     swap/candle300s // 5分钟k线数据频道
     swap/candle900s // 15分钟k线数据频道
     swap/candle1800s // 30分钟k线数据频道
     swap/candle3600s // 1小时k线数据频道
     swap/candle7200s // 2小时k线数据频道
     swap/candle14400s // 4小时k线数据频道
     swap/candle21600 // 6小时k线数据频道
     swap/candle43200s // 12小时k线数据频道
     swap/candle86400s // 1day k线数据频道
     swap/candle604800s // 1week k线数据频道
     */
    @Test
    public void klineChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/candle60s:BTC-USD-SWAP");
        //调用订阅方法
        WebSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(100000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共-交易频道
     * Trade Channel
     */
    @Test
    public void tradeChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/trade:BTC-USD-SWAP");
        //调用订阅方法
        WebSocketClient.subscribe(channel);
        //为保证测试方法不停，需要让线程延迟
        try {
            Thread.sleep(100000000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 公共-资金费率频道
     * funding_rate Channel
     */
    @Test
    public void fundChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/funding_rate:EOS-USDT-SWAP");
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
     * 公共-限价频道
     * priceRange Channel
     */
    @Test
    public void priceRangeChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/price_range:BTC-USD-SWAP");
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
     * 公共-5档深度频道
     * Depth5 Channel
     */
    @Test
    public void depth5Channel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/depth5:BTC-USD-SWAP");
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
     * 公共-400档深度频道
     * Depth Channel
     * 首次返回400档，然后每隔100毫秒，快照这个时间段内有更改的订单簿数据，并推送
     */
    @Test
    public void depthChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/depth:BTC-USDT-SWAP");
//        channel.add("swap/depth:XRP-USD-SWAP");
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
     * 公共-400档增量数据频道
     * Depth Channel
     * 首次返回400档，后续只要订单簿深度有变化就推送有更改的数据
     */
    @Test
    public void allDepthChannel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/depth_l2_tbt:SUN-USDT-SWAP");
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
     * 公共-标记价格频道
     * markPrice Channel
     */
    @Test
    public void Channel() {
        //添加订阅频道
        ArrayList<String> channel = Lists.newArrayList();
        channel.add("swap/mark_price:BTC-USD-SWAP");
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
        list.add("swap/candle60s:BTC-USD-SWAP");
        //订阅
        webSocketClient.subscribe(list);
        //取消订阅
        webSocketClient.unsubscribe(list);
        //为保证收到服务端返回的消息，需要让线程延迟
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
