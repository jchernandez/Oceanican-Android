package com.oceanican.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class OceanicanFragment extends SherlockFragment implements AdapterView.OnItemClickListener {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_oceanican,null);

        ListView listView = (ListView) view.findViewById(R.id.oceanicanList);

        ArrayList<String> ayuda = new ArrayList<String>();
        ayuda.addAll(Arrays.asList(getResources().getStringArray(
                R.array.acercaDe)));
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getSherlockActivity()   ,
                android.R.layout.simple_list_item_1, ayuda);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){

            case 0:{
                startActivity(new Intent(getSherlockActivity(),AboutActivity.class));

            }break;
            case 1:{
                startActivity(new Intent(getSherlockActivity(),ContactActivity.class));
            }break;
            case 2:{
                final View donaView = View.inflate(getSherlockActivity(),R.layout.donacion_dialog,null);
                AlertDialog.Builder donacionBuilder = new AlertDialog.Builder(getSherlockActivity());
                donacionBuilder.setView(donaView);
                WebView webView = (WebView) donaView.findViewById(R.id.dona_web);
                String text = " <form style=\"display: table; margin: auto;\" action=\"https://www.paypal.com/cgi-bin/webscr\" method=\"post\" target=\"_top\">\n" +
                        "          <input type=\"hidden\" name=\"cmd\" value=\"_s-xclick\">\n" +
                        "          <input type=\"hidden\" name=\"hosted_button_id\" value=\"44A22JR3MBKKG\">\n" +
                        "          <input type=\"image\" src=\"https://www.paypalobjects.com/es_XC/MX/i/btn/btn_donateCC_LG.gif\" border=\"0\" name=\"submit\" alt=\"PayPal, la forma más segura y rápida de pagar en línea.\">\n" +
                        "          <img alt=\"\" border=\"0\" src=\"https://www.paypalobjects.com/es_XC/i/scr/pixel.gif\" width=\"1\" height=\"1\">\n" +
                        "</form>";
                webView.loadData(text,null,"UTF-8");

                donacionBuilder.show();
            }break;
        }
    }
}
