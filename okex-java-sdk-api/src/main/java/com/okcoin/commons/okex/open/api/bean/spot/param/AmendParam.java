package com.okcoin.commons.okex.open.api.bean.spot.param;

import com.okcoin.commons.okex.open.api.bean.option.param.AmentDate;

import java.util.List;

public class AmendParam {

    private String instrument_id;

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    private String cancel_on_fail;
    private String client_oid;
    private String request_id;
    private String new_size;
    private String new_price;
    private String order_id;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCancel_on_fail() {
        return cancel_on_fail;
    }

    public void setCancel_on_fail(String cancel_on_fail) {
        this.cancel_on_fail = cancel_on_fail;
    }

    public String getClient_oid() {
        return client_oid;
    }

    public void setClient_oid(String client_oid) {
        this.client_oid = client_oid;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getNew_size() {
        return new_size;
    }

    public void setNew_size(String new_size) {
        this.new_size = new_size;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }
}
