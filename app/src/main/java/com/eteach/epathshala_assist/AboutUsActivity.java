package com.eteach.epathshala_assist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by shree on 03/09/2017.
 */

public class AboutUsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String TAG = "About us";
    ActionBar actionBar;
    WebView webView;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_about_us );
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView=(WebView)findViewById(R.id.web_view_aboutus);
        try {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://epathshalaonline.com/about.html");
        setSupportActionBar(toolbar);

            getSupportActionBar().setTitle(TAG);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed ( ) {
        Intent start_Activity = new Intent(AboutUsActivity.this,MainActivity.class);
        startActivity(start_Activity);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId ();
        if (id == android.R.id.home)
        {
            onBackPressed ();
            return true;
        }
        return super.onOptionsItemSelected ( item );
    }
}
