package com.yiwucheguanjia.merchantcarmgr.post.model;

/**
 * Created by Administrator on 2016/12/8.
 */
public class ServiceItemBean {
    /**
     * img_path : http://112.74.13.51:8080/carmgr/upload/image/merchant_info/33.png
     * state : 审核中
     * service_name : 至尊黄金顶级VIP极致保养
     * access_times : 1000
     * date_time : 2016-12-08
     */

    private String img_path;
    private String state;
    private String service_name;
    private String access_times;
    private String date_time;

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getAccess_times() {
        return access_times;
    }

    public void setAccess_times(String access_times) {
        this.access_times = access_times;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
