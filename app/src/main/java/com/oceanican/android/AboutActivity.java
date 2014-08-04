package com.oceanican.android;

import android.os.Bundle;
import android.webkit.WebView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.nodes.Document;

public class AboutActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.fragment_webview);
        WebView webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl("http://oceanican.webfactional.com/app_about.html");
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
