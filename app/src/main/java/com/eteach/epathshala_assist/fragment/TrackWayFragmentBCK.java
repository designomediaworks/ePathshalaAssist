package com.eteach.epathshala_assist.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Service.LocationService;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.Webservice.TrackwayWebservices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.eteach.epathshala_assist.Utility.utilitys.isNetworkConnected;

public class TrackWayFragmentBCK extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    utilitys utilitys;
    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;
    // TODO: Rename and change types of parameters
    MapView mapView;
    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;
    TrackwayWebservices trackwayWebservices;
    String response = "", RouteName,getLastpntsRes;
    private SupportMapFragment mapFragment;
    double latitude = 0.0;
    double longitude = 0.0;
    double d_latitude = 0.0;
    double d_longitude = 0.0;
    Object getLaskKnownLocation ;
    private Handler mHandler = new Handler ( );
    private Timer mTimer = null;
    long notify_interval = 1000 * 60;
    private Marker StopsMarker , BusMarker, UserMarker ;
    public TrackWayFragmentBCK ( ) {
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
    public static TrackWayFragmentBCK newInstance ( String param1, String param2) {
        TrackWayFragmentBCK fragment = new TrackWayFragmentBCK ();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            utilitys = new utilitys(getContext());
            HashMap <String, String> Route = utilitys.getRouteName();
            RouteName = Route.get(com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_ROUTE_NAME);
            if (!RouteName.equals("NA")) {
                new DownloadTrackWayData().execute(RouteName);
            }

            mTimer = new Timer ( );
            mTimer.schedule ( new TimerTaskToGetLocation(), 5, notify_interval );

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    private BroadcastReceiver broadcastReceiver =  new BroadcastReceiver ( ) {
    @Override
    public void onReceive ( Context context, Intent intent ) {
        latitude = Double.valueOf(intent.getStringExtra("latutide"));
        longitude = Double.valueOf(intent.getStringExtra("longitude"));
        getLaskKnownLocation = String.valueOf ( intent.getStringExtra  ("getLastBusLocation") );
    }
    };

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trackway, container, false);
        try {
            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapview);
            //mapFragment.getMapAsync(this);
            //getuserlocation();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return view;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed (Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach ( ) {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady (GoogleMap googleMap) {

        try {
            mMap = googleMap;
            if (response.isEmpty() || response == "NA" || response == "[]") {
                LatLng nagpur = new LatLng(21.1458, 79.0882);
               UserMarker = googleMap.addMarker(new MarkerOptions().position(nagpur).title("Nagpur City"));

               UserMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus_stop));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(nagpur));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.7f));
            } else {
                response.split(":");
                JSONArray responsArray = null;
                try {
                    responsArray = new JSONArray(response);

                    for (int i = 0; i < responsArray.length(); i++) {
                        JSONObject c = responsArray.getJSONObject(i);
                        String markertitle = c.getString("v_routename").toString();
                        String markersnipest = c.getString("v_routeAddress").toString();
                        Double _lat = Double.valueOf(c.getString("d_latitude").toString());
                        Double _lng = Double.valueOf(c.getString("d_lonigitude").toString());
                        LatLng nagpur = new LatLng(_lat, _lng);
                        StopsMarker = googleMap.addMarker(new MarkerOptions().position(nagpur).title(markertitle).snippet(markersnipest));
                        StopsMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus_stop));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(nagpur));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.7f));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction ( Uri uri );
    }

    @Override
    public void onPause ( ) {
        super.onPause ( );
        getActivity ().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume ( ) {
        super.onResume ( );
        getActivity ().registerReceiver ( broadcastReceiver, new IntentFilter( LocationService.str_receiver) );
    }
    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if ( isNetworkConnected ( getActivity () ) ) {
                        AsyncTask asyncTask = new AsyncTask ( ) {
                            @Override
                            protected Object doInBackground ( Object[] objects )
                            {
                                getLastpntsRes = trackwayWebservices.getLastKnownBusLocation ( "route1" ).toString ();
                                return trackwayWebservices;
                            }
                            @Override
                            protected void onPostExecute ( Object o ) {
                                super.onPostExecute ( o );
                                GoogleMap googleMap = null;
                                mMap = googleMap;
                                try {
                                    JSONArray jsonArray = new JSONArray ( getLastpntsRes );
                                    for ( int i=0 ; i < jsonArray.length ();i++)
                                    {
                                        JSONObject c = jsonArray.getJSONObject(i);
                                        latitude = Double.parseDouble ( c.getString ( "d_latitude" ) );
                                        longitude = Double.parseDouble ( c.getString ( "d_longitude" ) );
                                        LatLng BusLoc = new LatLng(d_latitude,d_longitude);
                                        BusMarker.remove ();
                                        BusMarker = googleMap.addMarker(new MarkerOptions().position(BusLoc).title("School Bus").snippet("Running"));
                                        BusMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.bus));
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(BusLoc));
                                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.7f));

                                    }
                                    /*if ( d_latitude != 0.0 && d_longitude != 0.0 )
                                    {
                                        LatLng nagpur = new LatLng(d_latitude,d_longitude);
                                        BusMarker = googleMap.addMarker(new MarkerOptions().position(nagpur).title("School Bus").snippet("Running"));
                                        BusMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.bus));
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(nagpur));
                                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.7f));
                                    }*/
                                } catch ( JSONException e ) {
                                    e.printStackTrace ( );
                                }catch ( Exception e )
                                {
                                    e.printStackTrace ();
                                }
                            }
                        };asyncTask.execute (  );
                    }
                    else { Toast.makeText ( getActivity (),"Internet connection not available",Toast.LENGTH_LONG ).show ();
                    }
                }
            });
        }
    }
    private class DownloadTrackWayData extends AsyncTask implements OnMapReadyCallback {
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
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute (Object o) {
            super.onPostExecute(o);
            mapFragment.getMapAsync(this);
        }

        @Override
        public void onMapReady (GoogleMap googleMap) {
            try {
                googleMap = googleMap;
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                LatLng nagpur = new LatLng(21.1458, 79.0882);
                BusMarker =  googleMap.addMarker(new MarkerOptions().position(nagpur).title("Nagpur City"));
                BusMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.bus));
                if (response.isEmpty() || response == "NA"||response.equals("[]")) {
                    nagpur = new LatLng(21.1458, 79.0882);
                    googleMap.addMarker(new MarkerOptions().position(nagpur).title("Nagpur City")).
                            setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus_stop));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(nagpur));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.7f));
                } else {
                    response.split(":");
                    JSONArray responsArray = null;
                    try {
                        responsArray = new JSONArray(response);

                        for (int i = 0; i < responsArray.length(); i++)
                        {
                            JSONObject c = responsArray.getJSONObject(i);
                            String markertitle = c.getString("v_routename").toString();
                            String markersnipest = c.getString("v_routeAddress").toString();
                            Double _lat = Double.valueOf(c.getString("d_latitude").toString());
                            Double _lng = Double.valueOf(c.getString("d_lonigitude").toString());
                            nagpur = new LatLng(_lat, _lng);
                            googleMap.addMarker(new MarkerOptions().position(nagpur).title(markertitle).snippet(markersnipest)).
                                    setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bus_stop));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(nagpur));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.7f));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run ( ) {
                    pDialog.dismissWithAnimation();
                }
            }, 5000);
        }
    }
    }
