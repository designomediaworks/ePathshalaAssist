package com.eteach.epathshala_assist.fcmservice;

import android.content.SharedPreferences;
import android.util.Log;

import com.eteach.epathshala_assist.Utility.utilitys;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by shree on 20/07/2017.
 */

public class FCMRegistrationIntentIDService extends FirebaseInstanceIdService {
    public utilitys utilitys;
    private static final String TAG = FCMRegistrationIntentIDService.class.getSimpleName();
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        storeRegIdInpref(refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void storeRegIdInpref(String refreshedToken) {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(utilitys.SHARED_PREF,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("regID",refreshedToken);
        editor.commit();
    }

    private void sendRegistrationToServer(String token) {
       Log.e(TAG, "Sendtodmwserver"+token);

    }
}

