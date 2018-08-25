package com.eteach.epathshala_assist.fragment;

import android.Manifest;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eteach.epathshala_assist.R;

import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.Webservice.TrackwayWebservices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.eteach.epathshala_assist.Utility.utilitys.isNetworkConnected;

public class TrackWayFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    utilitys utilitys;
    private LocationManager locationManager;
    private LocationListener locationListener;
    // TODO: Rename and change types of parameters
    private Boolean timerRunflag = true;
    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;
    TrackwayWebservices trackwayWebservices;
    String response = "", RouteName, getLastpntsRes;
    private SupportMapFragment mapFragment;
    double latitude = 0.0;
    double longitude = 0.0;
    double d_latitude = 0.0;
    double d_longitude = 0.0;
    Location mBusLastLocation,mBusCurrentlocation ;
    private Handler mHandler = new Handler ( );
    private Timer mTimer = null;
    long notify_interval = 1000 ;
    private Marker StopsMarker, BusMarker, UserMarker;
    private  Circle circle;
    public static final float CAMERA_POSOTION = 12.7f;
    public LatLng mGeoPosition;
    public static String str_receiver = "drivertracker.service.receiver";
    IntentFilter intentFilter;
    public float bearing ;
    public TrackWayFragment ( ) {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomescreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackWayFragment newInstance ( String param1, String param2 ) {
        TrackWayFragment fragment = new TrackWayFragment ( );
        Bundle args = new Bundle ( );
        args.putString ( ARG_PARAM1, param1 );
        args.putString ( ARG_PARAM2, param2 );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        try {
            utilitys = new utilitys ( getContext ( ) );
            HashMap <String, String> Route = utilitys.getRouteName ( );
            RouteName = Route.get ( com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_ROUTE_NAME );
            BusLastLocation();
            if ( ! RouteName.equals ( "NA" ) ) {
                new DownloadTrackWayData ( ).execute ( RouteName );
            }
        intentFilter = new IntentFilter ( str_receiver );

        } catch ( RuntimeException e ) {
            e.printStackTrace ( );
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver ( ) {
        @Override
        public void onReceive ( Context context, Intent intent ) {
            latitude = Double.valueOf ( intent.getStringExtra ( "latutide" ) );
            longitude = Double.valueOf ( intent.getStringExtra ( "longitude" ) );
            UserLocation(latitude,longitude);
        }
    };

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_trackway, container, false );
        try {

            mapFragment = ( SupportMapFragment ) getChildFragmentManager ( ).findFragmentById ( R.id.mapview );

            mapFragment.getMapAsync ( this );

        } catch ( Exception e ) {
            e.printStackTrace ( );
        } finally {
            return view;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed ( Uri uri ) {
        if ( mListener != null ) {
            mListener.onFragmentInteraction ( uri );
        }
    }

    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );
        locationManager = ( LocationManager ) getActivity ( ).getSystemService ( Context.LOCATION_SERVICE );
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach ( ) {
        timerRunflag = false;
        mListener = null;
        super.onDetach ( );

    }

    @Override
    public void onMapReady ( GoogleMap googleMap ) {
        mMap = googleMap;
        mMap.setMapType ( GoogleMap.MAP_TYPE_HYBRID );
        if ( ActivityCompat.checkSelfPermission ( getActivity (), Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                ( getActivity (), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled ( true );
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
    public String markertitle = "";
    public String markersnipest="";

        public
        void UserLocation ( double latitude, double longitude ) {
            try {

                if (response.isEmpty() || response == "NA" || response == "[]") {
                    mGeoPosition = new LatLng(latitude, longitude);
                    UserMarker = mMap.addMarker(new MarkerOptions().position(mGeoPosition).title("You'r hear!"));

                   // UserMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus_stop));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mGeoPosition));

                    mMap.animateCamera(CameraUpdateFactory.zoomTo(CAMERA_POSOTION));

                } else {
                    response.split(":");


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public
        void PickupnStopLocation ( ) {
            getActivity ().runOnUiThread ( new Runnable ( ) {
                @Override
                public
                void run ( ) {
            try {
                JSONArray responsArray = null;
                responsArray = new JSONArray(response);
                for (int i = 0; i < responsArray.length(); i++) {
                    JSONObject c = responsArray.getJSONObject(i);
                     markertitle = c.getString("v_routename").toString();
                     markersnipest = c.getString("v_routeAddress").toString();
                    Double _lat = Double.valueOf(c.getString("d_latitude").toString());
                    Double _lng = Double.valueOf(c.getString("d_lonigitude").toString());
                    mGeoPosition = new LatLng(_lat, _lng);

                                StopsMarker = mMap.addMarker ( new MarkerOptions ( ).position ( mGeoPosition ).title ( markertitle ).snippet ( markersnipest ) );
                                StopsMarker.setIcon ( BitmapDescriptorFactory.fromResource ( R.mipmap.ic_bus_stop ) );
                                mMap.moveCamera ( CameraUpdateFactory.newLatLng ( mGeoPosition ) );
                                mMap.animateCamera ( CameraUpdateFactory.zoomTo ( CAMERA_POSOTION ) );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                }
            } );
        }

        public
        void BusLastLocation ( ) {
            mTimer = new Timer ( );
            mTimer.schedule ( new TimerTaskToGetLocation ( ), 1000, notify_interval );
        }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }

    @Override
    public void onPause ( ) {
        super.onPause ( );
    }

    @Override
    public
    void onStart ( ) {
        super.onStart ( );
       // LocalBroadcastManager.getInstance ( getActivity () ).registerReceiver (broadcastReceiver, intentFilter );
    }

    @Override
    public
    void onStop ( ) {
        super.onStop ( );
        timerRunflag = false;

    }

    @Override
    public void onResume ( ) {
        super.onResume ( );
//        LocalBroadcastManager.getInstance ( getActivity () ).registerReceiver (broadcastReceiver, intentFilter );

    }
    private class TimerTaskToGetLocation extends TimerTask {

            Constants constants = new Constants ();

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Boolean t = constants.isNetworkConnected ( getActivity () );
                    if ( constants.isNetworkConnected ( getActivity () ) ) {
                        AsyncTask asyncTask = new AsyncTask ( ) {
                            @Override
                            protected Object doInBackground ( Object[] objects )
                            {
                                try{
                                    trackwayWebservices = new TrackwayWebservices(getActivity());
                                   getLastpntsRes =  trackwayWebservices.getLastKnownBusLocation ( "route1" ).toString ();

                            }catch ( Exception e )
                                {
                                e.printStackTrace ();
                                }
                                return trackwayWebservices;
                            }
                            @Override
                            protected void onPostExecute ( Object o ) {
                                super.onPostExecute ( o );

                                try {
                                    mBusLastLocation = new Location ( "" );
                                    JSONArray jsonArray = new JSONArray ( getLastpntsRes );
                                    for ( int i = 0 ; i < jsonArray.length ( ) ; i++ ) {
                                        JSONObject c = jsonArray.getJSONObject ( i );
                                        d_latitude = Double.parseDouble ( c.getString ( "d_latitude" ) );
                                        d_longitude = Double.parseDouble ( c.getString ( "d_longitude" ) );
                                        mGeoPosition = new LatLng ( d_latitude, d_longitude );
                                        mBusCurrentlocation = new Location ( "" );
                                        //new Location of school bus form server
                                        mBusCurrentlocation.setLatitude ( d_latitude );
                                        mBusCurrentlocation.setLongitude ( d_longitude );
                                        //last location of school bus
                                        mBusLastLocation.setLatitude ( d_latitude );
                                        mBusLastLocation.setLongitude ( d_longitude );

                                        if ( mBusLastLocation != null ) {
                                            bearing = mBusCurrentlocation.bearingTo ( mBusLastLocation );
                                        }
                                        //Thread.sleep ( 1000 );
                                        if ( getActivity ( ) != null ) {
                                            getActivity ( ).runOnUiThread ( new Runnable ( ) {
                                                @Override
                                                public
                                                void run ( ) {

                                                    if ( BusMarker != null ) {
                                                        BusMarker.remove ( );
                                                        circle.remove ( );
                                                    }
                                                    BusMarker = mMap.addMarker ( new MarkerOptions ( ).position ( mGeoPosition ).title ( "School Bus" ).snippet ( "Running" ) );
                                                    BusMarker.setIcon ( BitmapDescriptorFactory.fromResource ( R.mipmap.ic_schoolbus ) );
                                                    mMap.moveCamera ( CameraUpdateFactory.newLatLng ( mGeoPosition ) );
                                                    BusMarker.setAnchor ( 0.5f, 0.5f );
                                                    BusMarker.setRotation ( bearing );
                                                    BusMarker.setFlat ( true );
                                                    mMap.animateCamera ( CameraUpdateFactory.zoomTo ( 16f ) );

                                                    circle = mMap.addCircle ( new CircleOptions ( )
                                                                                      .center ( mGeoPosition )
                                                                                      .radius ( 250 )
                                                                                      .strokeColor ( Color.RED )
                                                                                      .fillColor ( 0x00000000 ) );
                                                    circle.setCenter ( mGeoPosition );

                                                }
                                            } );

                                        }else {
                                            return;
                                        }
                                    }

                                } catch ( JSONException e ) {
                                    e.printStackTrace ( );
                                }catch ( Exception e )
                                {
                                   e.printStackTrace ();
                                }
                            }
                        };asyncTask.execute (  );
                    }
                    else {
                        //Toast.makeText ( getActivity (),"Internet connection not available",Toast.LENGTH_LONG ).show ();
                    }
                }
            });

        }
    }
    private class DownloadTrackWayData extends AsyncTask  {
        SweetAlertDialog pDialog;

        @Override
        protected void onPreExecute ( ) {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
            pDialog.setTitleText("Loading TrackWay Route....")
                    .setContentText("ePathshala School Assist")
                    .show();
        }
        @Override
        protected Object doInBackground (Object[] params) {
            try {
                trackwayWebservices = new TrackwayWebservices(getActivity());
                String r = (String) params[0];
                response = trackwayWebservices.showroutelist((String) params[0]).toString();//Route name which is alloted to student
                PickupnStopLocation();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute (Object o) {
            super.onPostExecute(o);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run ( ) {
                    pDialog.dismissWithAnimation();
                }
            }, 5000);
        }

    }
}
