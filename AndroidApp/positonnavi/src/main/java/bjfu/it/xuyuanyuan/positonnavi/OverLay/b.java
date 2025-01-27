package bjfu.it.xuyuanyuan.positonnavi.OverLay;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bjfu.it.xuyuanyuan.positonnavi.R;

public class b {
    protected List<Marker> stationMarkers = new ArrayList();
    protected List<Polyline> allPolyLines = new ArrayList();
    private Marker aa;
    private Marker b;
    protected LatLng startPoint;
    protected LatLng endPoint;
    protected AMap mAMap;
    private Context c;
    private Bitmap d;
    private Bitmap e;
    private Bitmap f;
    private Bitmap g;
    private Bitmap h;
    private AssetManager i;
    protected boolean mNodeIconVisible = true;

    public b(Context var1) {
        this.c = var1;
        this.i = this.c.getResources().getAssets();
    }

    public void removeFromMap() {
        if (this.aa != null) {
            this.aa.remove();
        }

        if (this.b != null) {
            this.b.remove();
        }

        Iterator var1 = this.stationMarkers.iterator();

        while (var1.hasNext()) {
            Marker var2 = (Marker) var1.next();
            var2.remove();
        }

        var1 = this.allPolyLines.iterator();

        while (var1.hasNext()) {
            Polyline var3 = (Polyline) var1.next();
            var3.remove();
        }

        this.a();
    }

    public void setNodeIconVisibility(boolean var1) {
        this.mNodeIconVisible = var1;
        Iterator var2 = this.stationMarkers.iterator();

        while (var2.hasNext()) {
            Marker var3 = (Marker) var2.next();
            var3.setVisible(var1);
        }

        this.mAMap.reloadMap(); //自己添加的

        /*原文中并没有删除，是无法使用*/
        /*this.mAMap.postInvalidate();*/
    }

    private void a() {
        if (this.d != null) {
            this.d.recycle();
            this.d = null;
        }

        if (this.e != null) {
            this.e.recycle();
            this.e = null;
        }

        if (this.f != null) {
            this.f.recycle();
            this.f = null;
        }

        if (this.g != null) {
            this.g.recycle();
            this.g = null;
        }

        if (this.h != null) {
            this.h.recycle();
            this.h = null;
        }

    }

    protected BitmapDescriptor getStartBitmapDescriptor() {
        return this.getBitDes(this.d, "amap_start.png");
    }

    protected BitmapDescriptor getEndBitmapDescriptor() {
        return this.getBitDes(this.e, "amap_end.png");
    }

    protected BitmapDescriptor getBusBitmapDescriptor() {
        return this.getBitDes(this.f, "amap_bus.png");
    }

    protected BitmapDescriptor getWalkBitmapDescriptor() {
        return this.getBitDes(this.g, "amap_man.png");
    }

    protected BitmapDescriptor getDriveBitmapDescriptor() {
        return this.getBitDes(this.h, "amap_car.png");
    }

    protected BitmapDescriptor getBitDes(Bitmap var1, String var2) {
        String var3 = "getBitDes";

        try {
            InputStream var4 = this.i.open(var2);
            var1 = BitmapFactory.decodeStream(var4);
            var1 = a.a(var1, 0.5f); //自己写的
//            var1 = a.a(var1, p.a);    //下载的原文是这样
            var4.close();
        } catch (IOException var6) {
//            cj.a(var6, "RouteOverlay", var3);//自己注释
        } catch (Exception var7) {
//            cj.a(var7, "RouteOverlay", var3);//自己注释
        }

        return BitmapDescriptorFactory.fromBitmap(var1);
    }

    protected void addStartAndEndMarker() {
        this.aa = this.mAMap.addMarker((new MarkerOptions()).position(this.startPoint).icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_home_black_24dp))).title("您的位置"));
        /**
         * 让其自动显示终点的位置
         * */
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(this.endPoint);
        markerOptions.title("终点");
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(c.getResources(),
                R.drawable.ic_home_black_24dp)));
        this.b = this.mAMap.addMarker(markerOptions);
        this.b.showInfoWindow(); //主动显示indowindow
    }

    public void zoomToSpan() {
        if (this.startPoint != null && this.endPoint != null) {
            if (this.mAMap == null) {
                return;
            }

            LatLngBounds var1 = this.getLatLngBounds();
            this.mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(var1, 50));
        }

    }

    protected LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder var1 = LatLngBounds.builder();
        var1.include(new LatLng(this.startPoint.latitude, this.startPoint.longitude));
        var1.include(new LatLng(this.endPoint.latitude, this.endPoint.longitude));
        return var1.build();
    }

    protected int getWalkColor() {
        return Color.parseColor("#d4020d");
    }

    protected int getBusColor() {
        return Color.parseColor("#00affe");
    }

    protected int getDriveColor() {
        return Color.parseColor("#537edc");
    }
}

