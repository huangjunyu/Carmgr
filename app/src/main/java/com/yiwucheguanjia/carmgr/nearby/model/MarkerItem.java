package com.yiwucheguanjia.carmgr.nearby.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/2/13.
 */
public class MarkerItem {
    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public Bitmap getMarkerBitmap() {
        return markerBitmap;
    }

    public void setMarkerBitmap(Bitmap markerBitmap) {
        this.markerBitmap = markerBitmap;
    }

    private String markerId;
    private Bitmap markerBitmap;
}
