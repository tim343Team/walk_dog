package com.wallet.walkthedog.untils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GnssStatus;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.wallet.walkthedog.bus_event.GpsLocationEvent;
import com.wallet.walkthedog.bus_event.GpsSateliteEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GpsUtils {

    private Context context;
    private LocationManager locationManager;
    private static final String TAG = "GPS-Info";

    public GpsUtils(Context context) {
        this.context = context;
        initLocationManager();
    }


    /**
     * 获取权限，并检查有无开户GPS
     */
    private void initLocationManager() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            ToastUtils.shortToast("手机定位未打开，请在设置中打开");
            // 转到手机设置界面，用户设置GPS
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
        }
        getProviders();
    }

    public void startLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG, "缺失权限: ");
            return;
        }
        Log.e(TAG, "startLocation: " + bestProvider);
        // 监听位置信息(经纬度变化)
        // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
        // 参数2，位置信息更新周期，单位毫秒
        // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        // 参数4，监听
        // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, myLocationListener);
        // 监听GPS状态，主要是捕获到的各个卫星的状态
        locationManager.addGpsStatusListener(gpsStatusListener);
    }

    public void stopLocation() {
        Log.e(TAG, "stopLocation: ");
        locationManager.removeUpdates(myLocationListener);
        locationManager.removeGpsStatusListener(gpsStatusListener);

    }

    /**
     * 获取可定位的方式
     */
    private MyLocationListener myLocationListener;
    private String bestProvider;

    private void getProviders() {
        //获取定位方式
        List<String> providers = locationManager.getProviders(true);
        for (String s : providers) {
            Log.e(TAG, "providers:" + s);
        }

        Criteria criteria = new Criteria();
        // 查询精度：高，Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精确
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 是否查询海拨
        criteria.setAltitudeRequired(false);
        // 是否查询方位角
        criteria.setBearingRequired(true);
        // 是否查询方位角准确度
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        // 指示所需的速度准确度
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        // 设置是否要求速度
        criteria.setSpeedRequired(true);
        // 电量要求：低  Criteria.POWER_LOW,Criteria.POWER_HIGH
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        bestProvider = locationManager.getBestProvider(criteria, false);  //获取最佳定位
        myLocationListener = new MyLocationListener();
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            //定位时调用
            Log.e(TAG, "onLocationChanged");
            Log.e(TAG, String.valueOf(location.getLatitude()));
            Log.e(TAG, String.valueOf(location.getLongitude()));
            Log.e(TAG, (location.getSpeed() * 3.6) + "km/h");
            GpsLocationEvent gpsSateliteEvent=new GpsLocationEvent();
            gpsSateliteEvent.setLatitude(location.getLatitude());
            gpsSateliteEvent.setLongitude(location.getLongitude());
            gpsSateliteEvent.setSpeed( (location.getSpeed() * 3.6) + "km/h");
            EventBus.getDefault().post(gpsSateliteEvent);
//            infoTextView.setText("Latitude:" + location.getLatitude() + "\n" + "Longitude:" + location.getLongitude() + "\n" + "Speed:" + (location.getSpeed() * 3.6) + "km/h");
//            getGpsStatus(textView);
            //经纬度转城市
//            Geocoder geocoder = new Geocoder(context);
//            try {
//                addresses =geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),10);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            for(Address address:addresses){
//                //国家  CN
//                Log.e(TAG,address.getCountryCode());
//                //国家
//                Log.e(TAG,address.getCountryName());
//                //省，市，地址
//                Log.e(TAG,address.getAdminArea());
//                Log.e(TAG,address.getLocality());
//                Log.e(TAG,address.getFeatureName());
//                //经纬度
//                Log.e(TAG, String.valueOf(address.getLatitude()));
//                Log.e(TAG, String.valueOf(address.getLongitude()));
//                //m/s
//                Log.e(TAG, String.valueOf(location.getSpeed() * 3.6));
//            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //定位状态改变
            Log.e(TAG, "onStatusChanged");

        }

        @Override
        public void onProviderEnabled(String provider) {
            //定位开启
            Log.e(TAG, "onProviderEnabled");

        }

        @Override
        public void onProviderDisabled(String provider) {
            //定位关闭
            Log.e(TAG, "onProviderDisabled");

        }
    }

    private boolean isGpsEnable() {
        if (locationManager != null) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        return false;
    }

    private void getGpsStatus(TextView textView) {
        List<GpsSatellite> numsatellitelist = new ArrayList<>();
        if (locationManager == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG, "缺失权限: ");
            return;
        }
        GpsStatus status = locationManager.getGpsStatus(null);
        //获取最大的卫星数（这个只是一个预设值）
        int maxsatellites = status.getMaxSatellites();
        Log.e(TAG, "maxsatellites----count=" + maxsatellites);
        Iterator<GpsSatellite> it = status.getSatellites().iterator();
        numsatellitelist.clear();
        //记录实际的卫星数目
        int count = 0;
        while (it.hasNext() && count <= maxsatellites) {
            //保存卫星的数据到一个队列，用于刷新界面
            GpsSatellite s = it.next();
            numsatellitelist.add(s);
            count++;
            Log.e(TAG, "updategpsstatus----count=" + count);
        }
        int msatellitenum = numsatellitelist.size();
        Log.e(TAG, "msatellitenum----count=" + msatellitenum);
        String originalText = textView.getText().toString();
        textView.setText(originalText + "\n" + "count:" + count + "\n" + "msatellitenum:" + msatellitenum);
    }

    /**
     * Gps状态监听
     */
    private GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.e(TAG, "缺失权限: ");
                return;
            }
            GpsStatus gpsStatus = locationManager.getGpsStatus(null);
            switch (event) {
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    // 第一次定位时间UTC gps可用
                    // Log.v(TAG,"GPS is usable");
                    Log.e(TAG, "GPS_EVENT_FIRST_FIX: ");
                    int i = gpsStatus.getTimeToFirstFix();
                    break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:// 周期的报告卫星状态
                    // Log.d(TAG, "gpsStatusListener: GPS_EVENT_SATELLITE_STATUS");
                    // 得到所有收到的卫星的信息，包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
                    Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
                    ArrayList<GpsSatellite> showSatelliteList = new ArrayList<GpsSatellite>();
                    ArrayList<GpsSatellite> connSatelliteList = new ArrayList<GpsSatellite>();
                    for (GpsSatellite satellite : satellites) {
                        // 包括 卫星的高度角、方位角、信噪比、和伪随机号（及卫星编号）
                        /*
                         * satellite.getElevation(); //卫星仰角 satellite.getAzimuth();
                         * //卫星方位角 satellite.getSnr(); //信噪比 satellite.getPrn();
                         * //伪随机数，可以认为他就是卫星的编号 satellite.hasAlmanac(); //卫星历书
                         * satellite.hasEphemeris(); satellite.usedInFix();
                         */

                        showSatelliteList.add(satellite);
                        if (satellite.usedInFix()) // 当卫星被GPS引擎用于计算最近位置时，返回true
                            connSatelliteList.add(satellite);
                    }
                    Log.d(TAG,
                            "gpsStatusListener: showSatelliteList.size() = " + showSatelliteList.size());
                    GpsSateliteEvent gpsSateliteEvent=new GpsSateliteEvent();
                    gpsSateliteEvent.setShowSatellite(showSatelliteList.size());
                    gpsSateliteEvent.setConnSatellite(connSatelliteList.size());
                    EventBus.getDefault().post(gpsSateliteEvent);
                    break;
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.e(TAG, "GPS_EVENT_STARTED: ");
//                    textView.setText("GPS_EVENT_STARTED" );
                    break;
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.e(TAG, "GPS_EVENT_STOPPED: ");
//                    textView.setText("GPS_EVENT_STOPPED" );
                    break;
                default:
                    break;
            }
        }
    };

//    private void updateSpeedByLocation(Location location) {
//        int tempSpeed = (int) (location.getSpeed() * 3.6); // m/s --> Km/h
//        int adasSpeed = tempSpeed;
//        int recordSpeed = tempSpeed;
//        double nowLatitude = location.getLatitude();
//        double nowLongitude = location.getLongitude();
//        Log.i("GPS", "Speed:" + tempSpeed);
//        if (recorderFront != null) {
//            if (recordSpeed > 0) {
//                recorderFront.setSpeed(recordSpeed);
//                recordSpeed = 0; // 清除速度
//            }
//            recorderFront.setLat(new DecimalFormat("#.00000")
//                    .format(nowLatitude) + "");
//            recorderFront.setLong(new DecimalFormat("#.00000")
//                    .format(nowLongitude) + "");
//        }
//    }
}
