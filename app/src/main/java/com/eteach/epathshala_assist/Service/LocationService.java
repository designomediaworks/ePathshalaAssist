package com.eteach.epathshala_assist.Service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Webservice.TrackwayWebservices;

import java.util.Timer;
import java.util.TimerTask;

import static com.eteach.epathshala_assist.Utility.utilitys.isNetworkConnected;

/**
 * Created by shree on 27-03-2018.
 */

public class LocationService extends Service implements LocationListener {
    LocationManager locationManager;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 100f;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    Location location;
    private Handler mHandler = new Handler ( );
    private Timer mTimer = null;
    long notify_interval = 1000 * 60;
    public static String str_receiver = "drivertracker.service.receiver";
    Intent intent;
    private static final int STATUS_NOTIFICATION_ID = 101;

    TrackwayWebservices trackwayWebservices;

    @Override
    public void onCreate ( ) {
        super.onCreate ( );
       /* mTimer = new Timer ( );
        mTimer.schedule ( new TimerTaskToGetLocation ( ), 5, notify_interval );*/


    }

    @Override
    public int onStartCommand ( Intent intent, int flags, int startId ) {
        Create_notification();
        return super.onStartCommand ( intent, flags, startId );
    }

    @Override
    public void onLocationChanged ( Location location ) {
        //fn_getlocation();
    }

    @Override
    public void onStatusChanged ( String provider, int status, Bundle extras ) {
       // fn_getlocation();
    }

    @Override
    public void onProviderEnabled ( String provider ) {
       // fn_getlocation();
    }

    @Override
    public void onProviderDisabled ( String provider ) {

    }

    @Nullable
    @Override
    public IBinder onBind ( Intent intent ) {
        return null;
    }
    private void fn_getlocation ( ) {
        locationManager = ( LocationManager ) getApplicationContext ( ).getSystemService ( LOCATION_SERVICE );
        isGPSEnable = locationManager.isProviderEnabled ( LocationManager.GPS_PROVIDER );
        isNetworkEnable = locationManager.isProviderEnabled ( LocationManager.NETWORK_PROVIDER );

        if ( ! isGPSEnable && ! isNetworkEnable ) {

        } else {

            if ( isNetworkEnable ) {
                location = null;
                if ( ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates ( LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, this );
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }
            }

            if (isGPSEnable){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,LOCATION_INTERVAL,LOCATION_DISTANCE,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }
            }
        }

    }
    private void fn_update(Location location){

        intent.putExtra("latutide",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");

        sendBroadcast(intent);
    }
    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                        if ( isNetworkConnected ( getApplicationContext () ) ) {
                            AsyncTask asyncTask = new AsyncTask ( ) {
                                @Override
                                protected Object doInBackground ( Object[] objects )
                                {
                                    trackwayWebservices = new TrackwayWebservices ( getApplicationContext () );
                                    trackwayWebservices.getLastKnownBusLocation ( "route1" );
                                    return trackwayWebservices;
                                }
                                @Override
                                protected void onPostExecute ( Object o ) {
                                    super.onPostExecute ( o );
                                }
                            };asyncTask.execute (  );
                        }
                        else { Toast.makeText ( getApplicationContext (),"Internet connection not available",Toast.LENGTH_LONG ).show ();
                        }
                }
            });
        }
    }
    public void Create_notification()
    {
        try
        {
            Notification.Builder builder = new Notification.Builder(LocationService.this);
            builder.setSmallIcon( R.mipmap.ic_launcher)
                    .setContentText("TrackWay Running")
                    .setAutoCancel(false);
            Notification notification = builder.getNotification();

            notification.flags |= Notification.FLAG_NO_CLEAR
                    | Notification.FLAG_ONGOING_EVENT;

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }
}
