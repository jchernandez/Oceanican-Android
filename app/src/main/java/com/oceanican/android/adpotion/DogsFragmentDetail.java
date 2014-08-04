package com.oceanican.android.adpotion;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.oceanican.android.R;


public class DogsFragmentDetail extends SherlockFragment{

    DogItem item;
    View view;
    ScrollView scrollView;
    ImageLoader mImageLoader;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestQueue mQueue= Volley.newRequestQueue(getSherlockActivity());
        mImageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(5);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        view=inflater.inflate(R.layout.fragment_dog_details,null);
        item= (DogItem) getArguments().getSerializable("currentDog");
        scrollView= (ScrollView) view.findViewById(R.id.detail_view);
        TextView name = (TextView) view.findViewById(R.id.nameDetail);

        name.setText(item.getNombre());


        View descriptCard=view.findViewById(R.id.adop_detail);
        View statusCard=view.findViewById(R.id.adop_status);

        TextView statusTitle = (TextView) statusCard.findViewById(R.id.card_title);
        TextView statusBody = (TextView) statusCard.findViewById(R.id.card_body);

        statusTitle.setText(getString(R.string.characteristics));


        String statusText="";

        if(item.getFecha()!=null)
            statusText ="Ingreso:"+item.getFecha()+"\n\n";

        //statusText= statusText+getString(R.string.size)+" "+item.getTamanio()+"\n\n";
        if(item.getDesparcitado())
            statusText= statusText+"✔ "+getString(R.string.desparacitado)+"\n\n";
        else
            statusText= statusText+"✘ "+getString(R.string.desparacitado)+"\n\n";

        statusText=statusText+item.getColor();
        statusBody.setText(item.getEdad()+", "+item.getGenero()+"\n\n");

        statusBody.append(statusText);



        TextView descriptTilte = (TextView) descriptCard.findViewById(R.id.card_title);
        TextView descriptBody = (TextView) descriptCard.findViewById(R.id.card_body);

        descriptTilte.setText(getString(R.string.description));
        descriptBody.setText(item.getDescripcion());

        getBitmap();
        return view;
    }


    public void getBitmap() {
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.detail_progressBar);

        mImageLoader.get(item.getFoto(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null && isAdded()) {
                    progressBar.setVisibility(View.GONE);

                    View imageHandler = view.findViewById(R.id.imageHandler);

                    imageView.setImageBitmap(response.getBitmap());
                    getSherlockActivity().getSupportActionBar().setBackgroundDrawable(
                            new ColorDrawable(Color.argb(0, 0, 0, 0)));
                    TypedArray array = getResources().obtainTypedArray(
                            R.style.AppTheme);

                    for (int i = 0; i < array.length(); ++i) {

                        TypedValue value = new TypedValue();
                        array.getValue(i, value);

                        int id = value.resourceId;

                        switch (value.type) {
                            case TypedValue.TYPE_INT_COLOR_ARGB4:
                                // process color.
                                break;

                            // handle other types
                        }
                    }

                    imageHandler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //amplia la imagen al darle click
                            Intent intent = new Intent(getActivity(),OpenImageDogActivity.class);
                            intent.putExtra("url",item.getFoto());
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setScaleType(null);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
