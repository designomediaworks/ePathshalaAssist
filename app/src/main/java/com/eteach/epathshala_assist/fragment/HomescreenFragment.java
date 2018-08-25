package com.eteach.epathshala_assist.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eteach.epathshala_assist.DBHelper.DBHandler;
import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Service.LocationService;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.utilitys;
import com.eteach.epathshala_assist.Webservice.NotificationsWebservices;
import com.eteach.epathshala_assist.fragment_gridmenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomescreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomescreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomescreenFragment extends Fragment {
    private static final String TAG_HOME = "Home";
    private static final String TAG_eVoting = "eVoting";
    private static final String TAG_EVENTS = "Events";
    private static final String TAG_FEES = "Fees";
    private static final String TAG_NOTIFICATIONS = "Notifications";
    private static final String TAG_TRACKWAY = "Trackway";
    private static final String TAG_ASSIGNMENT = "HomeWrok";
    private static final String TAG_EXAMHISTORY = "Exam Details";
    private static final String TAG_ATTANDANCE ="Attendance" ;
    private static final String TAG_TOPPERS ="Toppers" ;
    private static final String TAG_DAILYTHOUGHTS ="Daily Thoughts" ;
    private static final String TAG_EDUSPORTS ="EduSports" ;
    String studentname = "",studentclass="",studentdob="",studentAdd="",studentMobileno="",profile_download_path="";
    private  static final String web[] = {"eVoting","Notification","Events","Exam Details","Fees","HomeWork","TrackWay","Attendance","Daily Thoughts","Toppers"};
    private  static final int[] imageid = {R.drawable.evoting,R.drawable.notifications,R.drawable.events,R.drawable.examdetails,R.drawable.fees,R.drawable.homework,R.drawable.trackway,R.drawable.attendance,R.drawable.dailythouts,R.drawable.topper};
    DBHandler dbHandler;
    utilitys utilitys;
    View viewhome;
    GridView gridView;
    String AdmissionNo,Stdid,Classid;
    private NotificationsWebservices notificationsWebservices;
    Constants constants;
    ImageView iv_stu_profile_photo;
    static SweetAlertDialog pDialog;
    static fragment_gridmenu fragmentGridmenu = null ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomescreenFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomescreenFragment newInstance(String param1, String param2) {
        HomescreenFragment fragment = new HomescreenFragment ();
        Bundle args = new Bundle ();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onStart ( ) {
        super.onStart ( );
        try
        {
            /*utilitys = new utilitys ( getActivity () );
            HashMap<String, String> user = utilitys.getUserDetails ( );
            AdmissionNo = user.get ( utilitys.KEY_STD_NO );

            getfromsharedpref ( AdmissionNo );*/
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //return inflater.inflate( R.layout.fragment_home, container, false);
        ((MainActivity)getActivity()).setToolbarTitlefromfragment(TAG_HOME);
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT )
        {
            viewhome = inflater.inflate ( R.layout.fragment_home_4,container,false );
        }
        else
        {
            viewhome = inflater.inflate ( R.layout.fragment_home,container,false );
        }
            utilitys = new utilitys ( getActivity () );
        HashMap<String, String> user = utilitys.getUserDetails ( );
        AdmissionNo = user.get ( utilitys.KEY_STD_NO );
        if (AdmissionNo!=null)
        {
        getfromsharedpref (AdmissionNo);
        }
        Constants.ADMISSION_NO = AdmissionNo;
        BindStudentData ();
        CreateHomeScreenMenu();
        return viewhome;
}
    private void CreateHomeScreenMenu ( ) {
        fragmentGridmenu = new fragment_gridmenu ( getActivity (),web,imageid );
        gridView = (GridView) viewhome.findViewById ( R.id.GridMenu );
        gridView.setAdapter ( fragmentGridmenu );
        gridView.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                if(web[position] == "eVoting")
                {
                    if (Constants.STUDENT_HOUSE.isEmpty () || Constants.STUDENT_HOUSE.equals ( "null" )  )
                    {
                        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                        pDialog.setTitleText("You are not eleigible for voting")
                                .setContentText("ePathShala School Assist")
                                .show();
                        pDialog.setCancelable ( true );
                    }
                    else
                    {
                        Constants.navItemIndex = 6;
                        eVotingFinalFragment eVotingFinalFragment = new eVotingFinalFragment ( );
                        getActivity ( ).getSupportFragmentManager ( ).beginTransaction ( )
                                .replace ( R.id.frame, eVotingFinalFragment, TAG_eVoting )
                                .addToBackStack ( null )
                                .commit ( );
                        Bundle args = new Bundle ( );
                        args.putString ( "house", Constants.STUDENT_HOUSE );
                        ( ( MainActivity ) getActivity ( ) ).setToolbarTitlefromfragment ( web[position] );
                    }
                }
                if(web[position] == "Notification")
                {
                    Constants.navItemIndex = 7;
                    NotificationsFragment notificationsFragment = new NotificationsFragment ();
                    HashMap<String, String> user = utilitys.getuserandclass ( );
                    Constants.STUDENT_ID = user.get(com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_ID);
                    Constants.STUDENT_CLASS_ID = user.get(com.eteach.epathshala_assist.Utility.utilitys.KEY_STD_CLASS_ID);
                    constants = new Constants();
                    if (constants.isNetworkConnected(getActivity())==true) {
                        new DownloadNotificationsData().execute();
                    }
                    getActivity ().getSupportFragmentManager ().beginTransaction ()
                            .replace ( R.id.frame,notificationsFragment,TAG_NOTIFICATIONS )
                            .addToBackStack ( "Notification" )
                            .commit ();
                    ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( web [position]);

                    //Toast.makeText(getActivity (),"You Clicked At" +web[+position],Toast.LENGTH_LONG).show();
                }
                if(web[position] == "Events")
                {
                    Constants.navItemIndex = 8;
                    EventFragment eventFragment = new EventFragment ();
                    getActivity ().getSupportFragmentManager ().beginTransaction ()
                            .replace ( R.id.frame,eventFragment,TAG_EVENTS )
                            .addToBackStack ( "Events" )
                            .commit ();
                    ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( web [position]);

                }
                 if(web[position] == "Exam Details")
                {
                    Constants.navItemIndex = 9;
                    ExamHistoryFragment examHistoryFragment = new ExamHistoryFragment ();
                    getActivity ().getSupportFragmentManager ().beginTransaction ()
                            .replace ( R.id.frame,examHistoryFragment,TAG_EXAMHISTORY )
                            .addToBackStack ( "Exam Details" )
                            .commit ();
                    ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( web [position]);

                }
                if(web[position] == "Fees")
                {
                    Constants.navItemIndex = 10;
                    FeesFragment feesFragment = new FeesFragment ();
                    getActivity ().getSupportFragmentManager ().beginTransaction ()
                            .replace ( R.id.frame,feesFragment,TAG_FEES )
                            .addToBackStack ( "Fees" )
                            .commit ();
                    ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( web [position]);

                }
                if(web[position] == "HomeWork")
                {
                    Constants.navItemIndex = 11;
                    HomeworkFragment homeworkFragment = new HomeworkFragment ();
                    getActivity ().getSupportFragmentManager ().beginTransaction ()
                            .replace ( R.id.frame,homeworkFragment,TAG_ASSIGNMENT )
                            .addToBackStack ( "HomeWork" )
                            .commit ();
                    ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( web [position]);
                }
                if(web[position] == "TrackWay")
                {
                    try {
                        MainActivity.fab.hide();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    Constants.navItemIndex = 12;
                    TrackWayFragment trackWayFragment = new TrackWayFragment ();
                    getActivity ().getSupportFragmentManager ().beginTransaction ()
                            .replace ( R.id.frame,trackWayFragment,TAG_TRACKWAY )
                            .addToBackStack ( "TrackWay" )
                            .commit ();
                    ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( web [position]);

                }
                if(web[position] == "Attendance")
                {
                    AttandanceFragment attandanceFragment = new AttandanceFragment ();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity ().getSupportFragmentManager ().beginTransaction ();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out);
                    fragmentTransaction.replace ( R.id.frame,attandanceFragment,TAG_ATTANDANCE )
                            .addToBackStack ( "Attendance" )
                            .commit ();
                    ((MainActivity) getActivity ()).setToolbarTitlefromfragment ( web [position]);
                }
                if(web[position] == "EduSports")
                {
                    Constants.navItemIndex = 14;
                    Toast.makeText(getActivity (),"Comming Soon...." +web[+position],Toast.LENGTH_LONG).show();
                }
            }
        } );

    }
    public void BindStudentData()
    {
        try {
            TextView tv_admissionno = ( TextView ) viewhome.findViewById ( R.id.tv_admissionno );
            TextView tv_studentname = ( TextView ) viewhome.findViewById ( R.id.tv_studentname );
            TextView tv_class = ( TextView ) viewhome.findViewById ( R.id.tv_class );
            TextView tv_dob = ( TextView ) viewhome.findViewById ( R.id.tv_DOB );
            TextView tv_address = ( TextView ) viewhome.findViewById ( R.id.tv_studentAdd );
            TextView tv_mobileno = ( TextView ) viewhome.findViewById ( R.id.tv_studentMobileno );
            iv_stu_profile_photo = ( ImageView ) viewhome.findViewById ( R.id.iv_student_photo_round );
            tv_admissionno.setText ( AdmissionNo );
            tv_studentname.setText ( studentname );
            if ( studentclass.equals ( "NURSERY" ) ) {
                tv_class.setText ( "Class: " + studentclass.substring ( 0, 3 ) );
            } else {
                tv_class.setText ( "Class: " + studentclass );
            }
            tv_dob.setText ( "DOB: " + studentdob );
            tv_address.setText ( studentAdd );
            tv_address.setText ( studentAdd );
            tv_mobileno.setText ( studentMobileno );
            File StudentPhotofile = new File ( Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES )
                                                       + "/ePathshala/" + AdmissionNo + ".jpg" );
            Uri StudentPhotouri = Uri.fromFile ( StudentPhotofile );
            if ( ! StudentPhotofile.exists ( ) ) {
                setprofilephoto ( );
            }else {

                Glide.with ( getActivity ( ) ).load ( StudentPhotouri ).into ( iv_stu_profile_photo );
            }
        }catch ( Exception e )
        {
            e.printStackTrace ();
        }

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction (Uri uri);
    }
   public void getfromsharedpref ( String _AdmissionNo ) {
        try {
            dbHandler = new DBHandler ( getActivity () );
            String j = "";
            //Cursor studentList =  dbHandler.getStudent (_AdmissionNo) ;
           // j = studentList.getString ( 0 );
            j = dbHandler.getStudent (_AdmissionNo);
            JSONArray jsonArray = new JSONArray(j);
            for(int i =0 ;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                studentname = jsonObject.getString("V_S_FName").toString().toUpperCase()
                        +" "+jsonObject.getString("V_S_MName").toString().toUpperCase()
                        +" "+jsonObject.getString("V_S_LName").toString().toUpperCase();
                studentclass =jsonObject.getString("v_classname").toString();
                studentdob = jsonObject.getString("dob").toString();
                studentAdd = jsonObject.getString("V_Address1").toString();
                studentMobileno = "Mobile:"+jsonObject.getString("V_ContactNo").toString();
                profile_download_path = jsonObject.getString("imgpath").toString();
                Constants.STUDENT_HOUSE = jsonObject.getString("V_housename").toString();
                //new ImageDownLoader().execute(profile_download_path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setprofilephoto(){
        try{
            dbHandler = new DBHandler ( getActivity () );
            String j = "";
            j = dbHandler.getStudent (AdmissionNo);
            JSONArray jsonArray = new JSONArray ( j );;
            for (int i = 0; i < jsonArray.length (); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String profile_download_URL = jsonObject.getString("imgpath").toString();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ePathshala");
                if (!file.exists())
                    file.mkdir();
                new ImageDownLoader().execute(profile_download_URL);
            }
        }catch (JSONException e)
        {
            e.printStackTrace();}
    }
    private class DownloadNotificationsData extends AsyncTask {

        @Override
        protected Object doInBackground (Object[] params) {
            //user =  utility.getuserandclass ( );
            notificationsWebservices = new NotificationsWebservices(getContext());
            // notificationsWebservices.GETALLNOTIFICATIONS(user.get( utilitys.KEY_STD_CLASS_ID) ,user.get( utilitys.KEY_STD_ID));
            notificationsWebservices.GETALLNOTIFICATIONS(Constants.STUDENT_CLASS_ID ,Constants.STUDENT_ID,3);
            return notificationsWebservices;
        }
    }
    public
    class ImageDownLoader extends AsyncTask<String, Void, Bitmap> {
        //ProgressDialog progressDialog;
        @Override
        protected void onPreExecute ( ) {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setTitleText("Downloading Profile Photo!")
                    .setContentText("ePathShala School Assist")
                    .show();
        }
        @Override
        protected Bitmap doInBackground (String... URL) {
            String imgURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL( imgURL ).openStream ( );
                bitmap = BitmapFactory.decodeStream ( inputStream );
                String path = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES ) + "/ePathshala/";
                FileOutputStream fileOutputStream = null;
                Integer count = 0;
                File file = new File ( path, AdmissionNo.toUpperCase () + ".jpg" );
                // iv_stu_profile_photo.setImageBitmap ( bitmap );
                try {
                    fileOutputStream = new FileOutputStream ( file );
                    bitmap.compress ( Bitmap.CompressFormat.JPEG, 100, fileOutputStream );
                    if (fileOutputStream != null) {
                        fileOutputStream.close ( );
                    }
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
            } catch (IOException e) {
                e.printStackTrace ( );
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute (Bitmap bitmap) {
            super.onPostExecute(bitmap);
            try {
                File StudentPhotofile = new File ( Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES )
                                                           + "/ePathshala/" + AdmissionNo + ".jpg" );
                Uri StudentPhotouri = Uri.fromFile ( StudentPhotofile );
                Glide.with ( getActivity ( ) ).load ( StudentPhotouri ).into ( iv_stu_profile_photo );
                pDialog.dismissWithAnimation ( );
            }catch ( Exception e )
            {
                e.printStackTrace ();
            }
        }
    }
}
