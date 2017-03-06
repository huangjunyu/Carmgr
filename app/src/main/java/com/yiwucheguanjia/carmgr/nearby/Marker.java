package com.yiwucheguanjia.carmgr.nearby;

import com.amap.api.mapcore2d.aa;
import com.amap.api.maps2d.model.MarkerOptions;

/**
 * Created by Administrator on 2017/1/10.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.os.RemoteException;

import com.amap.api.mapcore2d.cj;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.RuntimeRemoteException;
import java.util.ArrayList;

public final class Marker {
    aa a;

    public Marker(MarkerOptions var1) {
    }

    public Marker(aa var1) {
        this.a = var1;
    }

    public void setPosition(LatLng var1) {
        this.a.b(var1);
    }

    public LatLng getPosition() {
        return this.a.t();
    }

    public float getZIndex() {
        return this.a.r();
    }

    public void setZIndex(float var1) {
        this.a.b(var1);
    }

    public void remove() {
        String var1 = "remove";

        try {
            this.a.a();
        } catch (Exception var3) {
            cj.a(var3, "Marker", var1);
        }

    }

    public void setObject(Object var1) {
        this.a.a(var1);
    }

    public Object getObject() {
        return this.a != null?this.a.u():null;
    }

    public void setPeriod(int var1) {
        String var2 = "setPeriod";

        try {
            this.a.a(var1);
        } catch (RemoteException var4) {
            cj.a(var4, "Marker", var2);
            throw new RuntimeRemoteException(var4);
        }
    }

    public int getPeriod() {
        String var1 = "getPeriod";

        try {
            return this.a.o();
        } catch (RemoteException var3) {
            cj.a(var3, "Marker", var1);
            throw new RuntimeRemoteException(var3);
        }
    }

    public void setIcons(ArrayList<BitmapDescriptor> var1) {
        String var2 = "setIcons";

        try {
            this.a.a(var1);
        } catch (RemoteException var4) {
            cj.a(var4, "Marker", var2);
            throw new RuntimeRemoteException(var4);
        }
    }

    public ArrayList<BitmapDescriptor> getIcons() {
        String var1 = "getIcons";

        try {
            return this.a.p();
        } catch (RemoteException var3) {
            cj.a(var3, "Marker", var1);
            throw new RuntimeRemoteException(var3);
        }
    }

    public void destroy() {
        String var1 = "destroy";

        try {
            if(this.a != null) {
                this.a.l();
            }
        } catch (Exception var3) {
            cj.a(var3, "Marker", var1);
        }

    }

    public String getId() {
        return this.a.d();
    }

    public void setTitle(String var1) {
        this.a.a(var1);
    }

    public String getTitle() {
        return this.a.f();
    }

    public void setSnippet(String var1) {
        this.a.b(var1);
    }

    public String getSnippet() {
        return this.a.g();
    }

    public void setIcon(BitmapDescriptor var1) {
        if(var1 != null) {
            this.a.a(var1);
        }

    }

    public void setAnchor(float var1, float var2) {
        this.a.a(var1, var2);
    }

    public void setDraggable(boolean var1) {
        this.a.a(var1);
    }

    public boolean isDraggable() {
        return this.a.h();
    }

    public void showInfoWindow() {
        if(this.a != null) {
            this.a.i();
        }

    }

    public void hideInfoWindow() {
        if(this.a != null) {
            this.a.j();
        }

    }

    public boolean isInfoWindowShown() {
        return this.a == null?false:this.a.k();
    }

    public void setVisible(boolean var1) {
        this.a.b(var1);
    }

    public boolean isVisible() {
        return this.a.s();
    }

    public void setRotateAngle(float var1) {
        String var2 = "setRotateAngle";

        try {
            this.a.a(var1);
        } catch (RemoteException var4) {
            cj.a(var4, "Marker", var2);
            throw new RuntimeRemoteException(var4);
        }
    }

    public boolean equals(Object var1) {
        return !(var1 instanceof Marker)?false:this.a.a(((Marker)var1).a);
    }

    public int hashCode() {
        return this.a.m();
    }

    public void setPositionByPixels(int var1, int var2) {
        String var3 = "setPositionByPixels";

        try {
            this.a.a(var1, var2);
        } catch (RemoteException var5) {
            cj.a(var5, "Marker", var3);
            var5.printStackTrace();
        }

    }
}

