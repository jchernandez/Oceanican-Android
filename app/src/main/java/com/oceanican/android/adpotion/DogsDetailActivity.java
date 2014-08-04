package com.oceanican.android.adpotion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.oceanican.android.R;

import java.util.ArrayList;

public class DogsDetailActivity extends SherlockFragmentActivity{

    ArrayList<DogItem> dogsItems;
    ViewPagerAdapter adapter;
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(com.actionbarsherlock.view.Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_dogs_detail);
        int currentItem = getIntent().getIntExtra("current",0);
        dogsItems= (ArrayList<DogItem>) getIntent().getSerializableExtra("dogsItems");
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPager.setOnPageChangeListener(onPageChangeListener);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(currentItem);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setIcon(android.R.drawable.screen_background_light_transparent);

        getSupportActionBar().setTitle("");
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DogsFragmentDetail dogsFragment = new DogsFragmentDetail();
            Bundle bundle = new Bundle();
            bundle.putSerializable("currentDog",dogsItems.get(position));
            dogsFragment.setArguments(bundle);
            return dogsFragment;
        }

        @Override
        public int getCount() {
            return dogsItems.size();
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
