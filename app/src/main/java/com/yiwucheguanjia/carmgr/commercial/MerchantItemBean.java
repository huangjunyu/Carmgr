package com.yiwucheguanjia.carmgr.commercial;

/**
 * Created by Administrator on 2016/7/11.
 */
public class MerchantItemBean {
    private String merchantImgUrl;
    private String merchantName;
    private String merchantTag;
    private double merchantStars;//服务星星数据
    private String merchantStarsStr;
    private String merchantAddress;
    private String merchantMobile;

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

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantMobile() {
        return merchantMobile;
    }

    public void setMerchantMobile(String merchantMobile) {
        this.merchantMobile = merchantMobile;
    }
}
