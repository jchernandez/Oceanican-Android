package com.oceanican.android.adpotion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.oceanican.app.R;

import java.util.ArrayList;

public class AdoptAdapter extends ArrayAdapter<DogItem>{

    Context context;
    ArrayList<DogItem> perrosItems;
    ImageLoader imageLoader;

    public AdoptAdapter(Context context, ArrayList<DogItem> dogItems, ImageLoader imageLoader) {
        super(context, R.layout.adopcion_item);
        this.context=context;
        this.perrosItems= dogItems;
        this.imageLoader=imageLoader;
    }


    @Override
    public int getCount() {
        return perrosItems.size();
    }

    public static class PlaceHolder{
        TextView nombre;
        TextView edad;
        TextView genero;
        NetworkImageView imageView;

        public static PlaceHolder generate(View convertView){
            PlaceHolder placeHolder = new PlaceHolder();
            placeHolder.nombre= (TextView)convertView.findViewById(R.id.adop_nombre);
            placeHolder.edad= (TextView)convertView.findViewById(R.id.adop_edad);
            placeHolder.genero= (TextView)convertView.findViewById(R.id.adop_genero);
            placeHolder.imageView= (NetworkImageView)convertView.findViewById(R.id.adop_image);
            return placeHolder;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PlaceHolder placeHolder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.adopcion_item, null);
            placeHolder= PlaceHolder.generate(convertView);
            convertView.setTag(placeHolder);
        }
        else{
            placeHolder = (PlaceHolder) convertView.getTag();
        }

        placeHolder.nombre.setText(perrosItems.get(position).getNombre());
        placeHolder.edad.setText(perrosItems.get(position).getEdad());
        placeHolder.genero.setText(perrosItems.get(position).getGenero());
        placeHolder.imageView.setImageUrl(perrosItems.get(position).getFoto(),imageLoader);

        return convertView;
    }
}
