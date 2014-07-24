package com.oceanican.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.oceanican.app.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OceanicanFragment extends SherlockFragment {

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

        return view;
    }
}
