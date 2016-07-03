package com.yiwucheguanjia.carmgr.progress;

/**
 * Created by Administrator on 2016/6/30.
 */
public class MerchantBean {
    private String merchantStr;//商家名称
    private String payStatusStr;//进度
    private String servicTypeStr;//服务类型
    private String orderOperate;//操作订单
    private String orderNumberStr;//订单号
    private String timeStr;//发布时间
    private String urgingOrder;//催订单

    public String getOrderOperate() {
        return orderOperate;
    }

    public void setOrderOperate(String orderOperate) {
        this.orderOperate = orderOperate;
    }

    public String getUrgingOrder() {
        return urgingOrder;
    }

    public void setUrgingOrder(String urgingOrder) {
        this.urgingOrder = urgingOrder;
    }

    public String getMerchantStr() {
        return merchantStr;
    }

    public void setMerchantStr(String merchantNStr) {
        this.merchantStr = merchantNStr;
    }

    public String getPayStatusStr() {
        return payStatusStr;
    }

    public void setPayStatusStr(String payStatusStr) {
        this.payStatusStr = payStatusStr;
    }

    public String getServicTypeStr() {
        return servicTypeStr;
    }

    public void setServicTypeStr(String servicTypeStr) {
        this.servicTypeStr = servicTypeStr;
    }

    public String getOrderNumberStr() {
        return orderNumberStr;
    }

    public void setOrderNumberStr(String orderNumberStr) {
        this.orderNumberStr = orderNumberStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
