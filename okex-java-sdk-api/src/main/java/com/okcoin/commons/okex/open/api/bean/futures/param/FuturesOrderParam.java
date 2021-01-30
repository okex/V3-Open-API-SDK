package com.okcoin.commons.okex.open.api.bean.futures.param;

public class FuturesOrderParam {
    //公共参数
    private String instrument_id;
    private String type;
    private String order_type;
    private String size;
    //计划委托
    private String trigger_price;
    private String algo_price;

    public String getAlgo_type() {
        return algo_type;
    }

    public void setAlgo_type(String algo_type) {
        this.algo_type = algo_type;
    }

    private String algo_type;
    //跟踪委托
    private String callback_rate;
    //冰山委托
    private String algo_variance;
    private String avg_amount;
    private String price_limit;
    //时间加权委托
    private String sweep_range;
    private String sweep_ratio;
    private String single_limit;
    private String time_interval;
    //止盈止损
    private String tp_trigger_price;
    private String tp_price;
    private String tp_trigger_type;
    private String sl_trigger_type;
    private String sl_trigger_price;
    private String sl_price;

    public String getTp_trigger_price() {
        return tp_trigger_price;
    }

    public void setTp_trigger_price(String tp_trigger_price) {
        this.tp_trigger_price = tp_trigger_price;
    }

    public String getTp_price() {
        return tp_price;
    }

    public void setTp_price(String tp_price) {
        this.tp_price = tp_price;
    }

    public String getTp_trigger_type() {
        return tp_trigger_type;
    }

    public void setTp_trigger_type(String tp_trigger_type) {
        this.tp_trigger_type = tp_trigger_type;
    }

    public String getSl_trigger_type() {
        return sl_trigger_type;
    }

    public void setSl_trigger_type(String sl_trigger_type) {
        this.sl_trigger_type = sl_trigger_type;
    }

    public String getSl_trigger_price() {
        return sl_trigger_price;
    }

    public void setSl_trigger_price(String sl_trigger_price) {
        this.sl_trigger_price = sl_trigger_price;
    }

    public String getSl_price() {
        return sl_price;
    }

    public void setSl_price(String sl_price) {
        this.sl_price = sl_price;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTrigger_price() {
        return trigger_price;
    }

    public void setTrigger_price(String trigger_price) {
        this.trigger_price = trigger_price;
    }

    public String getAlgo_price() {
        return algo_price;
    }

    public void setAlgo_price(String algo_price) {
        this.algo_price = algo_price;
    }

    public String getCallback_rate() {
        return callback_rate;
    }

    public void setCallback_rate(String callback_rate) {
        this.callback_rate = callback_rate;
    }

    public String getAlgo_variance() {
        return algo_variance;
    }

    public void setAlgo_variance(String algo_variance) {
        this.algo_variance = algo_variance;
    }

    public String getAvg_amount() {
        return avg_amount;
    }

    public void setAvg_amount(String avg_amount) {
        this.avg_amount = avg_amount;
    }

    public String getPrice_limit() {
        return price_limit;
    }

    public void setPrice_limit(String price_limit) {
        this.price_limit = price_limit;
    }

    public String getSweep_range() {
        return sweep_range;
    }

    public void setSweep_range(String sweep_range) {
        this.sweep_range = sweep_range;
    }

    public String getSweep_ratio() {
        return sweep_ratio;
    }

    public void setSweep_ratio(String sweep_ratio) {
        this.sweep_ratio = sweep_ratio;
    }

    public String getSingle_limit() {
        return single_limit;
    }

    public void setSingle_limit(String single_limit) {
        this.single_limit = single_limit;
    }

    public String getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }
}
