package com.eteach.epathshala_assist;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.eteach.epathshala_assist.listner.WSCallerVersionListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static com.eteach.epathshala_assist.Utility.utilitys.isNetworkConnected;

/**
 * Created by shree on 23-06-2018.
 */

public
class GooglePlayStoreAppVersionNameLoader extends AsyncTask<String, Void, String> {
    String newVersion = "";
    String currentVersion = "";
    WSCallerVersionListener mWsCallerVersionListener;
    boolean isVersionAvailabel;
    boolean isAvailableInPlayStore;
    Context mContext;
    String mStringCheckUpdate = "";

    GooglePlayStoreAppVersionNameLoader(Context mContext, WSCallerVersionListener callback) {
        mWsCallerVersionListener = callback;
        this.mContext = mContext;
    }

    @Override
    protected
    String doInBackground ( String... strings ) {
        try {
            isAvailableInPlayStore = true;
            if (isNetworkConnected(mContext)) {
                Document document =  Jsoup.connect( "https://play.google.com/store/apps/details?id=" + mContext.getPackageName())
                .timeout ( 10000 ).get ();
                mStringCheckUpdate = document.getElementsByAttributeValue ("itemprop","softwareVersion").first().text();
                return mStringCheckUpdate;
            }

        } catch (Exception e) {
            isAvailableInPlayStore = false;
            return mStringCheckUpdate;
        } catch (Throwable e) {
            isAvailableInPlayStore = false;
            return mStringCheckUpdate;
        }
        return mStringCheckUpdate;
    }

    @Override
    protected
    void onPostExecute ( String s ) {
        if (isAvailableInPlayStore == true) {
            newVersion = s;
            Log.e( "new Version", newVersion);
            checkApplicationCurrentVersion();
            if (currentVersion.equalsIgnoreCase(newVersion)) {
                isVersionAvailabel = false;
                Toast.makeText( mContext, mContext.getResources().getString(R.string.app_upto_date), Toast.LENGTH_LONG).show();
            } else {
                isVersionAvailabel = true;
            }
            mWsCallerVersionListener.onGetResponse(isVersionAvailabel);
        }
    }
    public void checkApplicationCurrentVersion() {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        currentVersion = packageInfo.versionName;
        Log.e("currentVersion", currentVersion);
    }
}
