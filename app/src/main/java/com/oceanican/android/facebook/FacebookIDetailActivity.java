package com.oceanican.android.facebook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.oceanican.app.R;

import java.util.ArrayList;

public class FacebookIDetailActivity extends SherlockFragmentActivity{

    ArrayList<FacebookItem> fbItems;
    ViewPagerAdapter adapter;
    ViewPager mPager;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(com.actionbarsherlock.view.Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_dogs_detail);

        mQueue= Volley.newRequestQueue(this);
        int currentItem = getIntent().getIntExtra("current",0);
        fbItems= (ArrayList<FacebookItem>) getIntent().getSerializableExtra("fbItems");
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setOnPageChangeListener(onPageChangeListener);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(currentItem);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FacebookDetailFragment facebookFragment = new FacebookDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("fbItem",fbItems.get(position));
            facebookFragment.setArguments(bundle);
            return facebookFragment;
        }

        @Override
        public int getCount() {
            return fbItems.size();
        }
    }

    private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            supportInvalidateOptionsMenu();
        }
    };
}
