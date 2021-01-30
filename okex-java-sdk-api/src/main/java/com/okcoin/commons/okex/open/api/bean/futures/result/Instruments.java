package com.okcoin.commons.okex.open.api.bean.futures.result;

/**
 * futures contract products <br/>
 *
 * @author Tony Tian
 * @version 1.0.0
 * @date 2018/2/26 10:49
 */
public class Instruments {
    private String instrument_id;
    private String underlying;
    private String base_currency;
    private String quote_currency;
    private String settlement_currency;
    private String contract_val;
    private String listing;
    private String delivery;
    private String tick_size;
    private String alias;
    private String is_inverse;
    private String contract_val_currency;
    private String trade_increment;
    private String category;
    private String underlying_index;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnderlying_index() {
        return underlying_index;
    }

    public void setUnderlying_index(String underlying_index) {
        this.underlying_index = underlying_index;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public String getBase_currency() {
        return base_currency;
    }

    public void setBase_currency(String base_currency) {
        this.base_currency = base_currency;
    }

    public String getQuote_currency() {
        return quote_currency;
    }

    public void setQuote_currency(String quote_currency) {
        this.quote_currency = quote_currency;
    }

    public String getSettlement_currency() {
        return settlement_currency;
    }

    public void setSettlement_currency(String settlement_currency) {
        this.settlement_currency = settlement_currency;
    }

    public String getContract_val() {
        return contract_val;
    }

    public void setContract_val(String contract_val) {
        this.contract_val = contract_val;
    }

    public String getListing() {
        return listing;
    }

    public void setListing(String listing) {
        this.listing = listing;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getTick_size() {
        return tick_size;
    }

    public void setTick_size(String tick_size) {
        this.tick_size = tick_size;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIs_inverse() {
        return is_inverse;
    }

    public void setIs_inverse(String is_inverse) {
        this.is_inverse = is_inverse;
    }

    public String getContract_val_currency() {
        return contract_val_currency;
    }

    public void setContract_val_currency(String contract_val_currency) {
        this.contract_val_currency = contract_val_currency;
    }

    public String getTrade_increment() {
        return trade_increment;
    }

    public void setTrade_increment(String trade_increment) {
        this.trade_increment = trade_increment;
    }

    @Override
    public String toString() {
        return "Instruments{" +
                "instrument_id='" + instrument_id + '\'' +
                ", underlying='" + underlying + '\'' +
                ", base_currency='" + base_currency + '\'' +
                ", quote_currency='" + quote_currency + '\'' +
                ", settlement_currency='" + settlement_currency + '\'' +
                ", contract_val='" + contract_val + '\'' +
                ", listing='" + listing + '\'' +
                ", delivery='" + delivery + '\'' +
                ", tick_size='" + tick_size + '\'' +
                ", alias='" + alias + '\'' +
                ", is_inverse='" + is_inverse + '\'' +
                ", contract_val_currency='" + contract_val_currency + '\'' +
                ", trade_increment='" + trade_increment + '\'' +
                '}';
    }
}
