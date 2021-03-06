package com.eteach.epathshala_assist.Utility;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;

import com.eteach.epathshala_assist.fragment.ExamHistoryFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by shree on 15/09/2017.
 */

public class PDF_File_Download extends AsyncTask<String,Void,Void> {
    private static final int MEGABYTE = 1024 * 1024 ;
    private Context mContext ;
    public SweetAlertDialog sweetAlertDialog;
    private notification_utilitys notificationutils;
    String reportcard_name;
    public ExamHistoryFragment examHistoryFragment = new ExamHistoryFragment();
    public PDF_File_Download (Context context) {
                mContext = context;
    }

    @Override
    protected
    void onPreExecute ( ) {
        super.onPreExecute ( );
        try {
            sweetAlertDialog = new SweetAlertDialog ( mContext, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.setTitleText("Downloading Please Wait...")
                    .setContentText("ePathShala School Assist")
                    .show();

        }catch (Exception e)
        {
            e.printStackTrace ();
        }
    }

    public static void downloadfile( String fileURL, String filename)
    {

        File directory = new File (Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_PICTURES),"/ePathshala/");
        File pdffile  = new File(directory,filename);
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(pdffile);
        URL url = new URL(fileURL);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
        int totalsize = urlConnection.getContentLength();

        byte[] buffer = new byte[MEGABYTE];
        int bufferlenght = 0;
        while ((bufferlenght = inputStream.read(buffer)) > 0 )
        {
        fileOutputStream.write(buffer,0,bufferlenght);
        }

        }catch (Exception e)
        {
        e.printStackTrace();
        }
    }
    private void showNotificationMessage(Context applicationContext, String title, String message, String timestamp, Intent resultIntent) {
        notificationutils = new notification_utilitys(applicationContext);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationutils.showNotificationMessage(title, message, timestamp, resultIntent);

    }
    @Override
    protected Void doInBackground (String... params) {
        downloadfile(params[0],params[1]);
        reportcard_name = params[1];
        return null;
    }

    @Override
    protected void onPostExecute (Void aVoid) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
        if (sweetAlertDialog.isShowing ()) {
            sweetAlertDialog.dismiss ( );
        }
        showNotificationMessage(mContext,"Report Card Downloaded", reportcard_name, timestamp, pushNotification);
    }
}

