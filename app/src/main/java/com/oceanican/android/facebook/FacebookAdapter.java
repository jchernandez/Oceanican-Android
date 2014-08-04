package com.oceanican.android.facebook;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.oceanican.android.R;

import java.util.ArrayList;

public class FacebookAdapter extends ArrayAdapter<FacebookItem> {

    Context context;
    ArrayList<FacebookItem> facebookItems;
    ImageLoader imageLoader;



    public FacebookAdapter(Context context, ArrayList<FacebookItem> facebookItems,ImageLoader mImageLoader) {
        super(context, R.layout.item_facebook_list);
        this.context=context;
        this.facebookItems=facebookItems;
        this.imageLoader=mImageLoader;
    }


    @Override
    public int getCount() {
        return facebookItems.size();
    }

    public static class PlaceHolder{
        TextView title;
        NetworkImageView imageView;

        public static PlaceHolder generate(View convertView){
            PlaceHolder placeHolder = new PlaceHolder();
            placeHolder.title= (TextView)convertView.findViewById(R.id.titleFB);
            placeHolder.imageView= (NetworkImageView)convertView.findViewById(R.id.imageFB);
            return placeHolder;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PlaceHolder placeHolder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_facebook_list,null);
            placeHolder= PlaceHolder.generate(convertView);
            convertView.setTag(placeHolder);
        }
        else{
            placeHolder = (PlaceHolder) convertView.getTag();
        }

        placeHolder.title.setText(facebookItems.get(position).getTitle());
        placeHolder.imageView.setDefaultImageResId(R.drawable.icon_launcher);
        placeHolder.imageView.setImageUrl(facebookItems.get(position).fbPicture,imageLoader);
        return convertView;
    }
}

