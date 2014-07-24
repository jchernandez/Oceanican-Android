package com.oceanican.android.facebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.oceanican.app.R;


public class FacebookDetailFragment extends SherlockFragment{
    View view;
    FacebookItem item;
    int times=1;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_webview,null);

        webView = (WebView) view.findViewById(R.id.webView);
        item= (FacebookItem) getArguments().getSerializable("fbItem");
        String content =item.getContent();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(item.getAlternet());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.v("here","here");
                view.scrollBy(0, 200);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView  view, String  url)
            {
                if ( url.contains("facebook")) {
                    return false;
                } else
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url)));
                    return true;
                }
            }

        });
        return view;
    }

    @Override
    public void onDestroy() {
        if(webView!=null)
            webView.loadUrl(null);
        super.onDestroy();
    }
}
///adb shell am start -W -a android.intent.action.VIEW -d fb://post/091e2289a536d804e27dd05b801ef97f?owner=452160471532410