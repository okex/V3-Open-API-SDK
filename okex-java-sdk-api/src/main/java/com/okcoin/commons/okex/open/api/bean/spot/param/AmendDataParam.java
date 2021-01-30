package com.okcoin.commons.okex.open.api.bean.spot.param;

import com.okcoin.commons.okex.open.api.bean.futures.param.AmendOrder;

import java.util.List;

public class AmendDataParam {
    private List<AmendParam> amend_data;

    public List<AmendParam> getAmend_data() {
        return amend_data;
    }

    public void setAmend_data(List<AmendParam> amend_data) {
        this.amend_data = amend_data;
    }
}
