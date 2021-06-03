package com.okcoin.commons.okex.open.api.websocket;

public interface WebSocket {

    void connect();

    void close();

    void login(String apiKey, String apiSecret, String passphrase);

    void subscribe(String... args);

    void unSubscribe(String... args);

    void sendPing();

    boolean checkSum(String data);

    void setProxyHost(String host);

    void setProxyPort(Integer port);
}
