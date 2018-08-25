package com.eteach.epathshala_assist.Utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by shree on 08/09/2017.
 */

public class ImagesDownload extends AsyncTask<String, Void, Bitmap> {
    String param1,param2;
    Context context;
    private notification_utilitys notificationutils;
    public ImagesDownload (Context _context )
    {
        this.context = _context;
    }
    private void showNotificationMessage(Context applicationContext, String title, String message, String timestamp, Intent resultIntent) {
        notificationutils = new notification_utilitys(applicationContext);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationutils.showNotificationMessage(title, message, timestamp, resultIntent);
    }
    private Bitmap downloadimageBitmap(String surl,String type){
        Bitmap bitmap = null;
        String path;
        try {
            InputStream inputStream = new URL ( surl ).openStream ( );
            bitmap = BitmapFactory.decodeStream ( inputStream );
            inputStream.close ();
            if ( type.equals ( "2" ) ) {

                path = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES )+"/ePathshala/" ;
                Constants.THOUGHT_NOTIFICATION_IMG = param1+param2 + ".jpg";
            }else
            {
                path = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES ) + "/ePathshala/EventView/" + param1;
            }
            FileOutputStream fileOutputStream = null;
            Integer count = 0;
            File file = new File ( path, param2+".jpg" );
            try {
                fileOutputStream = new FileOutputStream ( file );
                bitmap.compress ( Bitmap.CompressFormat.JPEG, 100, fileOutputStream );
                if (fileOutputStream != null)
                {
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
    protected void onPreExecute ( ) {
        super.onPreExecute ( );
    }

    @Override
    protected Bitmap doInBackground (String... params) {
        try
        {
        param1 = params[1];
        param2 = params[2];
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return downloadimageBitmap(params[0],"1");
    }

    @Override
    protected void onPostExecute (Bitmap bitmap) {

        /*String path = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES ) + "/ePathshala/EventView/"+param1;
        FileOutputStream fileOutputStream = null;
        Integer count = 0;
        File file = new File ( path, param2 + ".jpg" );
        // iv_stu_profile_photo.setImageBitmap ( bitmap );
        try {
            fileOutputStream = new FileOutputStream ( file );
            bitmap.compress ( Bitmap.CompressFormat.JPEG, 100, fileOutputStream );
            if (fileOutputStream != null) {
                fileOutputStream.close ( );
            }

        } catch (Exception e) {
            e.printStackTrace ( );
        }*/
        /*try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
            showNotificationMessage(context, "ePathshala", "Event Photos Downloaded Suuessfully!", timestamp, pushNotification);
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }
}
