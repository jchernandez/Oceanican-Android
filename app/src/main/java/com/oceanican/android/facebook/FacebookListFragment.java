package com.oceanican.android.facebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.oceanican.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FacebookListFragment extends SherlockFragment implements AdapterView.OnItemClickListener {

    View view;
    ListView fbListView;
    ArrayList<FacebookItem> fbItems;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        context=getSherlockActivity();
        view=inflater.inflate(R.layout.facebook_fragment,null);

        fbListView= (ListView) view.findViewById(R.id.fbList);

        fbListView.setOnItemClickListener(this);
        getList();

        return view;
    }


    public void getList(){

        RequestQueue mQueue= Volley.newRequestQueue(getSherlockActivity());
        final ImageLoader mImageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        String url="https://www.facebook.com/feeds/page.php?id=452160471532410&format=json";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                fbItems= new ArrayList<FacebookItem>();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("entries");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject entrie = jsonArray.getJSONObject(i);
                        FacebookItem item = new FacebookItem();
                        item.setTitle(Html.fromHtml(entrie.getString("title")).toString());
                        item.setContent(entrie.getString("content"));
                        item.setAlternet(entrie.getString("alternate"));
                        fbItems.add(item);
                    }

                    FacebookAdapter adapter = new FacebookAdapter(context,fbItems,mImageLoader);
                    fbListView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });


        mQueue.add(jsonObjectRequest);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.facebook,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.open_facebook) {
            Intent intent = getOpenFacebookIntent(getSherlockActivity());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/452160471532410"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Oceanican"));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context,FacebookIDetailActivity.class);
        intent.putExtra("fbItems",fbItems);
        intent.putExtra("current",position);
        startActivity(intent);
    }
}
