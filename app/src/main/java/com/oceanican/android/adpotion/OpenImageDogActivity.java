package com.oceanican.android.adpotion;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.oceanican.app.R;

public class OpenImageDogActivity extends SherlockActivity {

    ImageLoader mImageLoader;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(com.actionbarsherlock.view.Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activitiy_dog_image);

        url=getIntent().getStringExtra("url");

        RequestQueue mQueue= Volley.newRequestQueue(this);

        mImageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(5);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.argb(128, 0, 0, 0)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getBitmap();
    }

    public void getBitmap() {
        mImageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    ImageView bigImage = (ImageView) findViewById(R.id.bigImage);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);
                    bigImage.setVisibility(View.VISIBLE);
                    bigImage.setImageBitmap(response.getBitmap());

                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
