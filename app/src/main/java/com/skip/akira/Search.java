package com.skip.akira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import model.SearchModel;

public class Search extends ActionBarActivity{

    private static final String TAG = "SEARCH ---- ";
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.search_recycler_view) RecyclerView recyclerView;
    private Intent intent;
    private String query;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<SearchModel> sList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Searching");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = this.getIntent();
        query = intent.getStringExtra("query");

        try{
        Ion.with(this)
                .load(DeveloperKEY.SEARCH_URL + DeveloperKEY.TYPE_SEARCH  + DeveloperKEY.API_KEY + "&query=" + query)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null){
                            Log.d("EXCEPTION---- ", e.toString());
                        }
                        Log.d("RESULT--- ", result.toString());
                        JsonArray items = result.getAsJsonArray("results");
                        for (int i = 0; i < items.size(); i++) {
                            SearchModel sm = new SearchModel();
                            sm.setAdult(items.get(i).getAsJsonObject().get("adult").toString());
                            sm.setBackdrop_path(items.get(i).getAsJsonObject().get("backdrop_path").toString().replaceAll("^\"|\"$",""));
                            sm.setId(items.get(i).getAsJsonObject().get("id").getAsLong());
                            sm.setOrg_title(items.get(i).getAsJsonObject().get("original_title").toString().replaceAll("^\"|\"$",""));
                            sm.setRel_day(items.get(i).getAsJsonObject().get("release_date").toString().replaceAll("^\"|\"$",""));
                            sm.setPoster_path(items.get(i).getAsJsonObject().get("poster_path").toString().replaceAll("^\"|\"$",""));
                            sm.setPopularity(items.get(i).getAsJsonObject().get("popularity").getAsFloat());
                            sm.setVote_arg(items.get(i).getAsJsonObject().get("vote_average").getAsFloat());
                            sm.setVote_count(items.get(i).getAsJsonObject().get("vote_count").getAsInt());
                            sList.add(sm);
                            adapter.notifyDataSetChanged();
                            getSupportActionBar().setTitle(query + " - " + sList.size()+" Results");
                        }
                    }
                });}catch (Exception e){
            Log.d(TAG,e.toString());
            Toast.makeText(Search.this,"Opps! Something went wrong.",Toast.LENGTH_SHORT).show();
        }


        adapter = new SearchAdapter(sList,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
