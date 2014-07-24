package com.oceanican.android.adpotion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
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

import java.text.ParseException;
import java.util.ArrayList;


public class AdoptListFragment extends SherlockFragment implements AdapterView.OnItemClickListener {

    View view;
    ListView perrosListView;
    ArrayList<DogItem> perrosArrayList;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        context=getSherlockActivity();
        view=inflater.inflate(R.layout.adopcion_fragment,null);

        perrosListView= (ListView) view.findViewById(R.id.perros_list);

        perrosListView.setOnItemClickListener(this);
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

        String url="http://api.oceanican.webfactional.com/v1/oceanican/adopcion";
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                perrosArrayList= new ArrayList<DogItem>();

                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for(int i=0;i<jsonArray.length();i++){
                        DogItem perro = new DogItem();
                        JSONObject jPerro = jsonArray.getJSONObject(i);
                        perro.setNombre(jPerro.getString("nombre"));
                        perro.setFoto(jPerro.getString("foto"));
                        perro.setEdad(jPerro.getString("edad"));
                        perro.setGenero(jPerro.getString("genero"));
                        perro.setDescripcion(jPerro.getString("descripcion"));
                        perro.setTamanio(jPerro.getString("tamano"));
                        perro.setColor(jPerro.getString("color"));
                        int desp= jPerro.getJSONArray("desparacitado").getInt(0);
                        if(desp==1)
                            perro.setDesparcitado(true);
                        else
                            perro.setDesparcitado(false);
                        try {
                            perro.setFecha(jPerro.getString("fecha_ingreso"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        perrosArrayList.add(perro);
                    }

                    AdoptAdapter adapter = new AdoptAdapter(context,perrosArrayList,mImageLoader);
                    perrosListView.setAdapter(adapter);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context,DogsDetailActivity.class);
        intent.putExtra("current",position);
        intent.putExtra("dogsItems",perrosArrayList);
        startActivity(intent);
    }
}
