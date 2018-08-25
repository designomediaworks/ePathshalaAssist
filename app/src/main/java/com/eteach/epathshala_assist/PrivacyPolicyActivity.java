package com.eteach.epathshala_assist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String TAG = "Privacy Policy";
    ActionBar actionBar;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://dmwerp.com/PPA.htm");
        setSupportActionBar(toolbar);
        try {
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setTitle(TAG);

        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed ( ) {
        Intent start_Activity = new Intent(PrivacyPolicyActivity.this,MainActivity.class);
        startActivity(start_Activity);
        finish();
        super.onBackPressed();
    }
}
