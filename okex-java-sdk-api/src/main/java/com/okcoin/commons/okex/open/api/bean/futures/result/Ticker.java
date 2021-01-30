package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * The latest data on the futures contract product  <br/>
 * Created by Tony Tian on 2018/2/26 13;16. <br/>
 */
public class Ticker {

    private String instrument_id;
    private String last;
    private String best_bid;
    private String best_ask;
    private String high_24h;
    private String low_24h;
    private String volume_24h;
    private String volume_token_24h;
    private String timestamp;
    private String last_qty;
    private String best_ask_size;
    private String best_bid_size;

    public String getVolume_token_24h() {
        return volume_token_24h;
    }

    public void setVolume_token_24h(String volume_token_24h) {
        this.volume_token_24h = volume_token_24h;
    }

    public String getLast_qty() {
        return last_qty;
    }

    public void setLast_qty(String last_qty) {
        this.last_qty = last_qty;
    }

    public String getBest_ask_size() {
        return best_ask_size;
    }

    public void setBest_ask_size(String best_ask_size) {
        this.best_ask_size = best_ask_size;
    }

    public String getBest_bid_size() {
        return best_bid_size;
    }

    public void setBest_bid_size(String best_bid_size) {
        this.best_bid_size = best_bid_size;
    }

    public String getInstrument_id() { return instrument_id; }

    public void setInstrument_id(String instrument_id) { this.instrument_id = instrument_id; }

    public String getBest_bid() {
        return best_bid;
    }

    public void setBest_bid(String best_bid) {
        this.best_bid = best_bid;
    }

    public String getBest_ask() {
        return best_ask;
    }

    public void setBest_ask(String best_ask) {
        this.best_ask = best_ask;
    }

    public String getHigh_24h() {
        return high_24h;
    }

    public void setHigh_24h(String high_24h) {
        this.high_24h = high_24h;
    }

    public String getLow_24h() {
        return low_24h;
    }

    public void setLow_24h(String low_24h) {
        this.low_24h = low_24h;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getVolume_24h() {
        return volume_24h;
    }

    public void setVolume_24h(String volume_24h) {
        this.volume_24h = volume_24h;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
