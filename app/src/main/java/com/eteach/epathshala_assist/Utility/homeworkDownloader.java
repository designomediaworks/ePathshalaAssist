package com.eteach.epathshala_assist.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import com.eteach.epathshala_assist.MainActivity;
import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.fragment.HomeworkImageViewFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by shree on 07-02-2018.
 */

public class homeworkDownloader {
    public Context context;
    public String Para1;
    public
    homeworkDownloader ( Context _context , String _Para1) {
        context = _context;
        Para1 = _Para1;
    }

    public static class ImageDownLoader extends AsyncTask<String, Void, Bitmap> {
        public SweetAlertDialog sweetAlertDialog;
        public Context context;
        public String Para_1;
        public String imgURL;
        String path;
        StringBuilder sb;
        File file;
        public ImageDownLoader ( Context _context , String _Para_1) {
            context = _context;
            Para_1 = _Para_1;
        }

        @Override
        protected void onPreExecute ( ) {
            super.onPreExecute();
            try {
                sweetAlertDialog = new SweetAlertDialog ( context, SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.setTitleText("Downloading Homework...")
                        .setContentText("ePathShala Teacher Assist")
                        .show();

            }catch (Exception e)
            {
                e.printStackTrace ();
            }
        }
        protected void genratefilename()
        {
            int passwordSize = 10;
            char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
             sb = new StringBuilder ();
            Random random = new Random ();
            for (int i = 0; i < passwordSize; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
        }

        @Override
        protected
        Bitmap doInBackground ( String... URL) {
             imgURL = URL[0];
            Bitmap bitmap = null;
            try {
                genratefilename();
                InputStream inputStream = new URL ( imgURL ).openStream ( );
                bitmap = BitmapFactory.decodeStream ( inputStream );
                path = Environment.getExternalStoragePublicDirectory ( Environment.DIRECTORY_PICTURES ) + "/ePathshala/";
                FileOutputStream fileOutputStream = null;
                Integer count = 0;
                file = new File ( path, sb.toString () + ".jpg" );
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
            if (sweetAlertDialog.isShowing ())
            {
                sweetAlertDialog.dismiss ();
                try {
                    android.support.v4.app.Fragment fragment;
                    fragment = new HomeworkImageViewFragment ();
                    Constants.navItemIndex = 20;
                    android.support.v4.app.FragmentTransaction fragmentTransaction = ((MainActivity) context ).getSupportFragmentManager ().beginTransaction ();
                    Bundle args = new Bundle ();
                    args.putString( "filepath", String.valueOf ( file ) );
                    fragment.setArguments ( args );
                    fragmentTransaction.replace ( R.id.frame, fragment);
                    fragmentTransaction.addToBackStack (null);
                    fragmentTransaction.commit ();
                }catch ( Exception e )
                {
                    e.printStackTrace ();
                }
            }
        }
    }
}
