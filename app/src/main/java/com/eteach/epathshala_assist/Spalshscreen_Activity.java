package com.eteach.epathshala_assist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.eteach.epathshala_assist.Utility.Constants;
import com.eteach.epathshala_assist.Utility.utilitys;


public class Spalshscreen_Activity extends AppCompatActivity {
utilitys utilitys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        utilitys = new utilitys ( getApplicationContext () );
        //utilitys.checkLogin ();
        Constants.isLogin = 1;
       // FirebaseApp.initializeApp(this);
        loadMainScreen();
        Constants.versionCode = BuildConfig.VERSION_CODE;
    }

    private void loadMainScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start_Activity = new Intent(Spalshscreen_Activity.this,MainActivity.class);
                startActivity(start_Activity);
                finish();
            }
        },3000);
    }

}
