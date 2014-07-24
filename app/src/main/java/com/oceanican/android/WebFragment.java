package com.oceanican.android;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.oceanican.app.R;

public class WebFragment extends SherlockFragment{

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_webview,null);
        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.loadUrl("https://mobile.twitter.com/RefugiOceaniCAN");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

        return view;
    }
}
