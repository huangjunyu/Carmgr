package com.yiwucheguanjia.carmgr.commercial.model;

/**
 * Created by Administrator on 2016/7/1.
 */
public class MerchantSelectItemBean {
    private String businessItemStr;

    public MerchantSelectItemBean(String businessItemStr){
        this.businessItemStr = businessItemStr;
    }
    public String getBusinessItemStr() {
        return businessItemStr;
    }

    public void setBusinessItemStr(String businessItemStr) {
        this.businessItemStr = businessItemStr;
    }

}
