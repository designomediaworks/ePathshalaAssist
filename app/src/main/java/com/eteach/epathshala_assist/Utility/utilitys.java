package com.eteach.epathshala_assist.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.eteach.epathshala_assist.LoginActivity;

import java.util.HashMap;

/**
 * Created by shree on 01/09/2017.
 */

public class utilitys {

    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;
    private static String _imei = "";

        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREF_NAME = "ePathshalaAssist";

        // All Shared Preferences Keys
        private static final String IS_LOGIN = "IsLoggedIn";

        // User name (make variable public to access from outside)
        public static final String KEY_SCHOOL_NAME = "SchoolName";

        // Email address (make variable public to access from outside)
        public static final String KEY_STD_NO = "Student Admission No";

        public static final String KEY_STD_ID ="000" ;

        public static final String KEY_STD_CLASS_ID = "00";

        public static final String KEY_STD_HOUSE = "black";

        //Shared Preference name
        public static final String SHARED_PREF = "eteach_firebase";

        public static final String KEY_STD_ROUTE_NAME ="" ;

        public static AlertDialog.Builder builder;

        public static final String FCM_TOKEN = "firebase token";

        public static final String IS_BLOCK_APP = "NO";

        // Constructor
    public utilitys (Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

        /**
         * Create login session
         * */
    public void createLoginSession(String schoolname, String stdno, String stdid, String classid,String house){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_SCHOOL_NAME, schoolname);

        // Storing email in pref
        editor.putString(KEY_STD_NO, stdno);

        editor.putString(KEY_STD_ID,stdid);

        editor.putString(KEY_STD_CLASS_ID,classid);

        editor.putString ( KEY_STD_HOUSE,house );

        // commit changes
        editor.commit();
    }
        public void storeFCMinshared(String FCM_Token)
        {
            editor.putString(FCM_TOKEN,FCM_Token);
            editor.commit();
        }
        public void storeStudentRoute(String RouteName)
        {
            editor.putString(KEY_STD_ROUTE_NAME,RouteName);
            editor.commit();
        }    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_SCHOOL_NAME, pref.getString(KEY_SCHOOL_NAME, null));
        // user email id
        user.put(KEY_STD_NO, pref.getString(KEY_STD_NO, null));

        user.put ( KEY_STD_HOUSE,pref.getString ( KEY_STD_HOUSE ,null));
        // return user
        return user;
    }

    public HashMap<String,String> getuserandclass(){
        HashMap<String,String> user = new HashMap<>();
        user.put(KEY_STD_CLASS_ID,pref.getString( KEY_STD_CLASS_ID,null));
        user.put(KEY_STD_ID,pref.getString(KEY_STD_ID,null));
        return user;
    }
    public HashMap<String, String> getFCMToken(){
        HashMap<String, String> FCMToken = new HashMap<String, String>();
        // user name
        FCMToken.put(FCM_TOKEN, pref.getString(FCM_TOKEN, null));

        return FCMToken;
    }
    public HashMap<String, String> getRouteName(){
        HashMap<String, String> Route_Name = new HashMap<String, String>();
        // user name
        Route_Name.put(KEY_STD_ROUTE_NAME, pref.getString(KEY_STD_ROUTE_NAME, null));

        return Route_Name;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
    public void closeapp(final Activity activity)
{
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(activity);
        }
        else
        {
            builder = new AlertDialog.Builder(activity, AlertDialog.BUTTON_NEUTRAL);
        }
        builder.setTitle("Thank You");
        builder.setMessage("Thank You For Using Our Application Please Give Us Your Suggestions and Feedback ");
        builder.setNegativeButton("RATE US",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.eteach.epathshalaassist")); // Add package name of your application
                        activity.startActivity(intent);
                        Toast.makeText(activity, "Thank you for your Rating",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setPositiveButton("QUIT",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        activity.finishAffinity();
                    }
                });
        builder.show();
    }
}
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean isBlockApp(){
        // Check App Block status
        return pref.getBoolean(IS_BLOCK_APP, false);
    }

    public static int getConnectivityStatus ( Context context ) {
        ConnectivityManager connectivityManager = ( ConnectivityManager ) context
                .getSystemService ( Context.CONNECTIVITY_SERVICE );

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo ( );

        if ( networkInfo != null ) {
            if ( networkInfo.getType ( ) == ConnectivityManager.TYPE_WIFI
                    && networkInfo.getState ( ) == NetworkInfo.State.CONNECTED ) {

                return TYPE_WIFI;

            } else if ( networkInfo.getType ( ) == ConnectivityManager.TYPE_MOBILE
                    && networkInfo.getState ( ) == NetworkInfo.State.CONNECTED ) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean isNetworkConnected ( Context context ) {
        int networkStatus = getConnectivityStatus ( context );
        if ( networkStatus == TYPE_WIFI || networkStatus == TYPE_MOBILE ) {
            return true;
        } else {
            return false;
        }
    }
}

