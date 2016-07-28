package com.yiwucheguanjia.carmgr.commercial;

/**
 * Created by Administrator on 2016/7/11.
 */
public class MerchantItemBean {
    private String merchantImgUrl;
    private String merchantDistance;//离我距离
    private String merchantArea;
    private String merchantIntroduce;//介绍
    private double merchantStars;//服务星星数据
    private String merchantStarsStr;
    private String merchantName;
    private String province;
    private String merchantRoad;//所在路
    private String merchantServiceItem;//经营项目
    private String city;//广州
    private String merchantTag;
    private String merchantMobile;


    public String getMerchantDistance() {
        return merchantDistance;
    }

    public void setMerchantDistance(String merchantDistance) {
        this.merchantDistance = merchantDistance;
    }

    public String getMerchantIntroduce() {
        return merchantIntroduce;
    }

    public void setMerchantIntroduce(String merchantIntroduce) {
        this.merchantIntroduce = merchantIntroduce;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMerchantRoad() {
        return merchantRoad;
    }

    public void setMerchantRoad(String merchantRoad) {
        this.merchantRoad = merchantRoad;
    }

    public String getMerchantServiceItem() {
        return merchantServiceItem;
    }

    public void setMerchantServiceItem(String merchantServiceItem) {
        this.merchantServiceItem = merchantServiceItem;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public String getMerchantStarsStr() {
        return merchantStarsStr;
    }

    public void setMerchantStarsStr(String merchantStarsStr) {
        this.merchantStarsStr = merchantStarsStr;
    }

    public String getMerchantImgUrl() {
        return merchantImgUrl;
    }

    public void setMerchantImgUrl(String merchantImgUrl) {
        this.merchantImgUrl = merchantImgUrl;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantTag() {
        return merchantTag;
    }

    public void setMerchantTag(String merchantTag) {
        this.merchantTag = merchantTag;
    }

    public double getMerchantStars() {
        return merchantStars;
    }

    public void setMerchantStars(double merchantStars) {
        this.merchantStars = merchantStars;
    }

    public String getMerchantArea() {
        return merchantArea;
    }

    public void setMerchantArea(String merchantArea) {
        this.merchantArea = merchantArea;
    }

    public String getMerchantMobile() {
        return merchantMobile;
    }

    public void setMerchantMobile(String merchantMobile) {
        this.merchantMobile = merchantMobile;
    }
}
