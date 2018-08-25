package com.eteach.epathshala_assist.Service;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.Version;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by shree on 28-06-2018.
 */

public
class GetVersionCode extends AsyncTask<Void,String,String>{
    HttpURLConnection conn;
    String response = null,WhatsNew = "";
    String url = "http://app.eteach.co.in/appversion.json";
    String  AppVer;
    String currentVer ;
    Context mContext;
    Activity mActivity;

    HttpURLConnection connection = null;
    public GetVersionCode (Context context ,Activity activity)
    {
        mContext = context;
        mActivity = activity;

    }
    public String makeServiceCall(String reqUrl) {

        try {
            URL url = new URL ( reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = null;

                in = new BufferedInputStream ( conn.getInputStream());
                response = convertStreamToString(in);

        } catch (MalformedURLException e) {
            Log.e( TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader ( new InputStreamReader ( is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    @Override
    protected
    String doInBackground ( Void... voids ) {
        String jsonstr = makeServiceCall ( url );
        if ( jsonstr != null )
        {
            try {
                JSONObject jsonObject = new JSONObject ( jsonstr );
                JSONArray jsonArray = jsonObject.getJSONArray ("app"  );
                for ( int i = 0 ; i < jsonArray.length ();i++ )
                {
                    JSONObject object = jsonArray.getJSONObject ( i );
                    AppVer = object.getString("version");
                    WhatsNew = object.getString ( "whatsnew" );
                }
            }catch (final JSONException e)
            {
                Log.e( TAG, "Json parsing error: " + e.getMessage());

                e.printStackTrace ();
            }catch ( Exception e )
            {
                e.printStackTrace ();
            }
        }
        return AppVer;
    }

    @Override
    protected
    void onPostExecute ( String s ) {
        super.onPostExecute ( s );
        try {
            s = AppVer.replaceAll ( "\\.","" );
            if ( AppVer != null ) {
                int newVersion = Integer.parseInt ( s );
                if ( Constants.versionCode < newVersion ) {
                    showInfoView ( mActivity, String.valueOf ( newVersion ) );
                }
            }
        }catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }
    public void showInfoView( final Activity activity, final String Appver) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialogOnUIThread(activity,Appver);
            }
        });
    }

    private void showDialogOnUIThread(final Context context, String version) {
        final LayoutInflater inflater = (LayoutInflater ) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View view = inflater.inflate( R.layout.dialog_gp_version_check, null);
        bindVersionData(view, version, context);

        final AlertDialog dialog = new AlertDialog.Builder( context)
                .setTitle(R.string.gpvch_header)
                .setView(view)
                .setPositiveButton(R.string.gpvch_button_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openGooglePlay(context);
                    }
                })
                .setNegativeButton(R.string.gpvch_button_negative, null)
                .create();
        dialog.show();
    }


    private void bindVersionData( View view, String version, Context context) {
        final TextView tvVersion = (TextView) view.findViewById( R.id.tvVersionCode);
        tvVersion.setText(context.getString(R.string.app_name) + ": " + version);
        final TextView tvNews = (TextView) view.findViewById(R.id.tvChanges);
        tvNews.setText ( WhatsNew );
    }

    private void openGooglePlay(Context context) {
        final String packageName = context.getApplicationContext().getPackageName();
        final String url = context.getString(R.string.gpvch_google_play_url) + packageName;

        final Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url));
        context.startActivity(intent);
    }
}
