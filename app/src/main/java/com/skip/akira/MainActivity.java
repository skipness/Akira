package com.skip.akira;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.heinrichreimersoftware.materialdrawer.DrawerFrameLayout;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import adapter.PagerAdapter;
import model.SearchModel;
import util.NetWorkStateChecker;
import util.TEST;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity ---- ";
    private Toolbar toolbar;
    private SearchView searchView;
    private DrawerFrameLayout drawerLayout;
    private PagerSlidingTabStrip tabs;
    private ViewPager viewPager;
    private ActionBarDrawerToggle toggle;
    private PagerAdapter pagerAdapter;
    private NetWorkStateChecker nChecker = new NetWorkStateChecker();

    private List<SearchModel> sList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean x = nChecker.isNetworkAvailable(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        drawerLayout = (DrawerFrameLayout)findViewById(R.id.drawer);
        tabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.pager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AKIRA");
        initDrawer();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(toggle);
        pagerAdapter = new PagerAdapter(x, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabs.setViewPager(viewPager);
        pagerAdapter.notifyDataSetChanged();
    }

    private void initDrawer(){
        drawerLayout.setProfile(
                new DrawerProfile()
                        .setBackground(getResources().getDrawable(R.drawable.bg_default_profile_art))

        );

        drawerLayout.addItem(
                new DrawerItem()
                        .setImage(getResources().getDrawable(R.drawable.ic_drawer_favorites))
                        .setTextPrimary("Favorites")
                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                            @Override
                            public void onClick(DrawerItem drawerItem, int id, int position) {
                                Toast.makeText(MainActivity.this, "Clicked first item (#" + id + ")", Toast.LENGTH_SHORT).show();
                            }
                        })
        );

        drawerLayout.addItem(
                new DrawerItem()
                        .setImage(getResources().getDrawable(R.drawable.ic_drawer_history))
                        .setTextPrimary("History")
                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                            @Override
                            public void onClick(DrawerItem drawerItem, int id, int position) {
                                startActivity(new Intent(MainActivity.this,History.class));
                                drawerLayout.closeDrawer();
                            }
                        })
        );
        drawerLayout.addDivider();

        drawerLayout.addItem(
                new DrawerItem()
                        .setTextPrimary("Setting")
                        .setOnItemClickListener(new DrawerItem.OnItemClickListener() {
                            @Override
                            public void onClick(DrawerItem drawerItem, int id, int position) {
                                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                                drawerLayout.closeDrawer();
                            }
                        })
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                intent.putExtra("query",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
        }

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

}
