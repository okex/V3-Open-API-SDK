package com.okcoin.commons.okex.open.api.bean.spot.result;

public class OrderInfo {

    /**
     * 订单id
     */
    private String order_id;
    private String client_oid;
    /**
     * limit 订单类型的价格信息
     */
    private String price;
    /**
     * market 订单类型的价格信息
     */
    private String notional;
    /**
     * 委托数量
     */
    private String size;
    /**
     * 平均成交价
     */
    private String price_avg;
    private String order_type;
    /**
     * 委托时间
     */
    private String timestamp;
    /**
     * 成交数量
     */
    private String filled_size;
    /**
     * 订单状态 -1 已撤销 0 未成交
     */

    private String state;

    /**
     * 订单买卖类型 buy/sell
     */
    private String side;
    /**
     * 订单类型 limit/market
     */
    private String type;
    /**
     * 币对信息
     */
    private String instrument_id;

    /**
     * 计价成交量
     */
    private String filled_notional;
    private String fee_currency;
    private String fee;
    private String rebate_currency;

    public String getFee_currency() {
        return fee_currency;
    }

    public void setFee_currency(String fee_currency) {
        this.fee_currency = fee_currency;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getRebate_currency() {
        return rebate_currency;
    }

    public void setRebate_currency(String rebate_currency) {
        this.rebate_currency = rebate_currency;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    private String rebate;




    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }





    public String getPrice() {
        return this.price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }



    public String getNotional() {
        return this.notional;
    }

    public void setNotional(final String notional) {
        this.notional = notional;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(final String size) {
        this.size = size;
    }


    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFilled_size() {
        return this.filled_size;
    }

    public void setFilled_size(final String filled_size) {
        this.filled_size = filled_size;
    }


    public void setState(final String state) {
        this.state = state;
    }
    public String getState() {
        return this.state;
    }

    /*public String getStatus() {
        return this.status;
    }
    public void setStatus(final String status) {
        this.status = status;
    }*/
    public String getSide() {
        return this.side;
    }

    public void setSide(final String side) {
        this.side = side;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getInstrument_id() {
        return this.instrument_id;
    }

    public void setInstrument_id(final String instrument_id) {
        this.instrument_id = instrument_id;
    }





    public String getFilled_notional() {
        return this.filled_notional;
    }

    public void setFilled_notional(final String filled_notional) {
        this.filled_notional = filled_notional;
    }

    public String getPrice_avg() {
        return price_avg;
    }

    public void setPrice_avg(String price_avg) {
        this.price_avg = price_avg;
    }
}
