package com.yiwucheguanjia.carmgr.merchant_detail.model;

/**
 * Created by Administrator on 2016/8/10.
 */
public class RateBean {
    private String rateUser;
    private String rateText;
    private String rateStars;
    private double service_stars;//服务星星数据
    public double getService_stars() {
        return service_stars;
    }

    public void setService_stars(double service_stars) {
        this.service_stars = service_stars;
    }
    public String getRateUser() {
        return rateUser;
    }

    public void setRateUser(String rateUser) {
        this.rateUser = rateUser;
    }

    public String getRateText() {
        return rateText;
    }

    public void setRateText(String rateText) {
        this.rateText = rateText;
    }

    public String getRateStars() {
        return rateStars;
    }

    public void setRateStars(String rateStars) {
        this.rateStars = rateStars;
    }

    public String getRateTime() {
        return rateTime;
    }

    public void setRateTime(String rateTime) {
        this.rateTime = rateTime;
    }

    private String rateTime;
}
