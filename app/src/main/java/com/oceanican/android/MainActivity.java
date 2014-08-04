package com.oceanican.android;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.oceanican.android.adpotion.AdoptListFragment;
import com.oceanican.android.facebook.FacebookListFragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.view.GravityCompat;

public class MainActivity extends SherlockFragmentActivity {

    // Declare Variables
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    MenuListAdapter mMenuAdapter;
    String[] title;
    int[] icon;
    Fragment fragment1 = new WebFragment();
    FacebookListFragment fbFragment = new FacebookListFragment();
    AdoptListFragment adoptListFragment = new AdoptListFragment();
    OceanicanFragment oceanicanFragment = new OceanicanFragment();
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from drawer_main.xml
        setContentView(R.layout.drawer_main);

        // Get the Title
        mTitle = mDrawerTitle = getTitle();

        // Generate title
        title = new String[]{getString(R.string.twitter), getString(R.string.facebook),
                getString(R.string.adopcion),getString(R.string.oceanican)};

        // Generate icon
        icon = new int[]{R.drawable.twitter_icon, R.drawable.fb_icon,
                R.drawable.print_icon,R.drawable.oceanican};

        // Locate DrawerLayout in drawer_main.xml
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Locate ListView in drawer_main.xml
        mDrawerList = (ListView) findViewById(R.id.listview_drawer);

        // Set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Pass string arrays to MenuListAdapter
        mMenuAdapter = new MenuListAdapter(MainActivity.this, title,
                icon);

        // Set the MenuListAdapter to the ListView
        mDrawerList.setAdapter(mMenuAdapter);

        // Capture listview menu item click
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, R.string.app_name,
                R.string.app_name) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                // Set the title on the action when drawer open
                getSupportActionBar().setTitle(mDrawerTitle);
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // ListView click listener in the navigation drawer
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Locate Position
        Bundle  bundle = new Bundle();
        switch (position) {

            case 0:
                bundle.putInt("count",0);
                fragment1.setArguments(bundle);
                ft.replace(R.id.content_frame, fragment1);
                break;
            case 1:

                ft.replace(R.id.content_frame, fbFragment);
                break;
            case 2:
                ft.replace(R.id.content_frame, adoptListFragment);
                break;
            case 3:
                ft.replace(R.id.content_frame,oceanicanFragment);
        }
        ft.commit();
        mDrawerList.setItemChecked(position, true);

        // Get the title followed by the position
        setTitle(title[position]);
        // Close drawer
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}