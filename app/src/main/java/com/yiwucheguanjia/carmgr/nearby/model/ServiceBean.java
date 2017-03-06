package com.yiwucheguanjia.carmgr.nearby.model;

/**
 * Created by Administrator on 2017/1/9.
 */
public class ServiceBean {
    private String serviceType;
    private String servicePrice;
    private String serviceTitle;
    private String headerUrl;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

}
