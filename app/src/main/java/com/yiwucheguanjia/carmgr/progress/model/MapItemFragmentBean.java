package com.yiwucheguanjia.carmgr.progress.model;

import com.yiwucheguanjia.carmgr.progress.view.MapItemFragment;

/**
 * Created by Administrator on 2017/1/9.
 */
public class MapItemFragmentBean {

    private MapItemFragment mapItemFragment;
    private String serviceType;
    private String servicePrice;
    private String serviceTitle;
    private String headerUrl;
    private String id;
    private String content;
    private int star;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public MapItemFragment getMapItemFragment() {
        return mapItemFragment;
    }

    public void setMapItemFragment(MapItemFragment mapItemFragment) {
        this.mapItemFragment = mapItemFragment;
    }
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
