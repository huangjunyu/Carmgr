package com.yiwucheguanjia.carmgr.merchant_detail;

/**
 * Created by Administrator on 2017/2/22.
 */
public class GoolsBean {
    private String picUrl;
    private String serviceTitle;
    private String privilegePrice;//优惠价
    private String retailPrice;//门市价
    private String saled;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getPrivilegePrice() {
        return privilegePrice;
    }

    public void setPrivilegePrice(String privilegePrice) {
        this.privilegePrice = privilegePrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getSaled() {
        return saled;
    }

    public void setSaled(String saled) {
        this.saled = saled;
    }

}
