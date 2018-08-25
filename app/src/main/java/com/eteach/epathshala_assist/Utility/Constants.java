package com.eteach.epathshala_assist.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by shree on 04/07/2017.
 */

public class Constants {
    public static final String WEB_SERVER_ADD = "http://www.dmwerp.com/"; //Webservice NAMESPACE
    public static final String WURL = "http://www.dmwerp.com/ERPWebservices/Webservices/"; //WEB SERVICE URL PATH
    public static final String LOGIN_ACTION = "get_stdinfo";//SOAPACTION + METHODE NAME
    public static final String WURL_PATH = "assist_login.asmx"; //CONTAINS SERVICE
    public static final String EVENT_ACTION = "GetEvent";
    public static final String EVENT_WURL_PATH = "get_events.asmx";
    public static final String SCHOOL_ID = "SCHOOL_ID";
    public static String ADMISSION_NO = "";
    public static String STUDENT_DATA = "";//hold student information return from get_stdifo method
    public static int STUDENT_SESSION_ID = 0;
    public static String STUDENT_CLASS_ID = "00";
    public static String STUDENT_ID = "000";
    public static String STUDENT_HOUSE = "BLACK";
    public static String STUDENT_HOUSE_VOTING_URL = "http://www.dmwerp.com";

    public static Boolean LOGIN_STATUS = false;
    public static String EVENT_DATA = "";//hold EVENTS information return from get_stdifo method
    public static String EVENT_NAME="";
    public static String EVENT_TIME="";
    public static String EVENT_DISCREAPTION="";
    public static String STUDENT_BUS_ROUTE="";
    public static String error = "Error";
    public static String isEmployee = "a";
    public static String ProfilePhotoPath = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES ) + "/ePathshala/";
    /*************************For FCM TOKEN REGISTRATION************************************/
    public static final String FCMTOKEN_SAVE_ACTION = "save_tockens";//SOAPACTION + METHODE NAME
    public static final String FCMTOKEN_WURL_PATH = "fcm_tockens.asmx"; //CONTAINS SERVICE
    public static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    public String deviceid = "";
    public TelephonyManager mTelephonyManager;
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "ePathshala";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static String THOUGHT_NOTIFICATION_IMG = "";
    public static final String SHARED_PREF = "ah_firebase";
    public static  int isLogin ;
    public ConnectivityManager connectivityManager;
    public static int navItemIndex = 0;
    public static int FCMStoredOnServer = 0;
    public static int versionCode = 0;
    public final static  String VersionURL = "www.app.eteach.co.in/appversion.json";


   public final boolean isNetworkConnected(Context context)
   {
       try {
           connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
           NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
           if(networkInfo!=null && networkInfo.isConnected()){
               return true;
           }
       }catch (Exception e)
       {
           Log.e("Error",e.toString());
       }
       return false;
   }
   /* private void getDeviceImei (Context context) {

        mTelephonyManager = ( TelephonyManager )  context.getSystemService( Context.TELEPHONY_SERVICE );

        if (ActivityCompat.checkSelfPermission( context , Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
         deviceid = mTelephonyManager.getDeviceId( );
        //Log.d("msg", "DeviceImei " + deviceid);

    }*/
}





