package com.skip.akira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.shamanland.fab.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.ContentModel;
import model.ReviewModel;
import model.YoutubeModel;
import util.Database;
import util.NetWorkStateChecker;


public class Meat extends ActionBarActivity implements View.OnClickListener{

    private static final String TAG = "MEAT ---- ";
    private SharedPreferences sp;
    private Intent intent;
    private String id;
    private String youtube_key;
    private String movieName;
    private Database dbHelper;
    private NetWorkStateChecker checker = new NetWorkStateChecker();
    boolean overWifi;
    boolean isWifi;

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.poster) ImageView poster;
    @InjectView(R.id.ic_share) ImageView share;
    @InjectView(R.id.fab) FloatingActionButton fab;
    @InjectView(R.id.movie_name) TextView movie_name;
    @InjectView(R.id.rel_day) TextView rel_day;
    @InjectView(R.id.runtime) TextView time;
    @InjectView(R.id.tagline) TextView tagline;
    @InjectView(R.id.vote_avg) TextView vote_avg;
    @InjectView(R.id.overview) TextView overview;
    @InjectView(R.id.author_name) TextView author_name;
    @InjectView(R.id.review) TextView review;
    @InjectView(R.id.review_more) TextView review_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);
        ButterKnife.inject(this);
        intent = this.getIntent();
        id = intent.getStringExtra("id");
        String poster_path = intent.getStringExtra("poster_path");
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        boolean x = checker.isNetworkAvailable(this);
        if(x == false){

        }else {
            fetch();
        }
        fab.setOnClickListener(this);
        share.setOnClickListener(this);
        review_more.setOnClickListener(this);
        dbHelper = new Database(this);
    }

    private void fetch() {
        Ion.with(this)
                .load(DeveloperKEY.BASE_URL + id + DeveloperKEY.API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("RESULT--- ", result.toString());
                        ContentModel cm = new ContentModel();
                        String temp;
                        cm.setBudget(result.get("budget").getAsInt());
                        cm.setHomepath(result.get("homepage").getAsString().replaceAll("^\"|\"$", ""));
                        cm.setId(result.get("id").getAsInt());
                        cm.setImdb_id(result.get("imdb_id").getAsString().replaceAll("^\"|\"$", ""));
                        cm.setOrg_title(result.get("original_title").getAsString().replaceAll("^\"|\"$", ""));
                        movieName = cm.getOrg_title();
                        cm.setOverview(result.get("overview").getAsString().replaceAll("^\"|\"$", ""));
                        cm.setPopularity(result.get("popularity").getAsFloat());
                        if(result.get("poster_path").isJsonNull()){
                            temp = "null";
                        }else {
                            cm.setPoster_path(result.get("poster_path").getAsString().replaceAll("^\"|\"$", ""));
                            temp = DeveloperKEY.IMG_BASE_URL + "original" + cm.getPoster_path();
                        }
                        //cm.setBackdrop_path(result.get("backdrop_path").getAsString().replaceAll("^\"|\"$", ""));
                        cm.setRel_date(result.get("release_date").getAsString().replaceAll("^\"|\"$", ""));
                        cm.setRuntime(result.get("runtime").getAsInt());
                        cm.setStatus(result.get("status").getAsString().replaceAll("^\"|\"$", ""));
                        cm.setTag_line(result.get("tagline").getAsString().replaceAll("^\"|\"$", ""));
                        cm.setVote_arg(result.get("vote_average").getAsFloat());
                        cm.setVote_count(result.get("vote_count").getAsInt());

                        Ion.with(getApplicationContext())
                                .load(temp)
                                .withBitmap()
                                .error(R.drawable.ic_launcher)
                                .fitXY()
                                .centerCrop()
                                .intoImageView(poster)
                                .setCallback(new FutureCallback<ImageView>() {
                                    @Override
                                    public void onCompleted(Exception e, ImageView result) {
                                        findViewById(R.id.headerProgress).setVisibility(View.GONE);
                                    }
                                });

                        initView(cm.getOrg_title(), cm.getRel_date(), cm.getRuntime(), cm.getTag_line(), cm.getVote_arg(), cm.getStatus(), cm.getOverview(), cm.getId());
                    }
                });
    }

    private void initView(String org_title, String rel_date, int runtime, String tag, Float vote_arg, String status, String intro, int id) {
        dbHelper.open();
        dbHelper.insertHistoryRecord(id, org_title);
        dbHelper.close();
        fab.setSize(FloatingActionButton.SIZE_MINI);
        movie_name.setText(org_title);
        rel_day.setText(rel_date + " | " + status);
        time.setText(runtime + " Minutes");
        tagline.setText(tag);
        vote_avg.setText(String.valueOf(vote_arg / 2));
        overview.setText(intro);

        Ion.with(this)
                .load(DeveloperKEY.BASE_URL + id + DeveloperKEY.TYPE_REVIEW + DeveloperKEY.API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("RESULT--- ", result.toString());
                        if (result.get("total_results").getAsInt() != 0){
                            JsonArray items = result.getAsJsonArray("results");
                                ReviewModel rm = new ReviewModel();
                                rm.setAuthor(items.get(0).getAsJsonObject().get("author").toString().replaceAll("^\"|\"$", ""));
                                rm.setReview(items.get(0).getAsJsonObject().get("content").toString().replaceAll("^\"|\"$", ""));
                                rm.setUrl(items.get(0).getAsJsonObject().get("url").toString().replaceAll("^\"|\"$", ""));
                                author_name.setText(rm.getAuthor());
                                review.setText(rm.getReview());
                        }else {
                            author_name.setVisibility(View.GONE);
                            review_more.setVisibility(View.GONE);
                           // review.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            review.setText("No any comment yet");
                        }
                    }
                });

        findViewById(R.id.pb).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                sp = PreferenceManager.getDefaultSharedPreferences(this);
                overWifi = sp.getBoolean("KEY_OVERWIFI", false);
                isWifi = checker.getWiFiState(this);
                if (overWifi == true && isWifi == false) {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("Opps")
                            .setMessage("You are not using wifi!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else{
                    Log.d(TAG, DeveloperKEY.BASE_URL + id + DeveloperKEY.TYPE_VIDEOS + DeveloperKEY.API_KEY);
                    Ion.with(this)
                            .load(DeveloperKEY.BASE_URL + intent.getStringExtra("id") + DeveloperKEY.TYPE_VIDEOS + DeveloperKEY.API_KEY)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    Log.d("RESULT--- ", result.toString());
                                    JsonArray items = result.getAsJsonArray("results");
                                    YoutubeModel ym = new YoutubeModel();
                                    ym.setKey(items.get(0).getAsJsonObject().get("key").toString().replaceAll("^\"|\"$", ""));
                                    Intent intent = new Intent();
                                    intent.setClass(getApplicationContext(), YoutubePlayer.class);
                                    intent.putExtra("id",ym.getKey());
                                    startActivity(intent);
                                    Log.d(TAG,youtube_key);
                                }
                            });
        }
                break;

            case R.id.ic_share:

                Log.d(TAG, DeveloperKEY.BASE_URL + id + DeveloperKEY.TYPE_VIDEOS + DeveloperKEY.API_KEY);
                Ion.with(this)
                        .load(DeveloperKEY.BASE_URL + intent.getStringExtra("id") + DeveloperKEY.TYPE_VIDEOS + DeveloperKEY.API_KEY)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                                         @Override
                                         public void onCompleted(Exception e, JsonObject result) {
                                             Log.d("RESULT--- ", result.toString());
                                             JsonArray items = result.getAsJsonArray("results");
                                             YoutubeModel ym = new YoutubeModel();
                                             ym.setKey(items.get(0).getAsJsonObject().get("key").toString().replaceAll("^\"|\"$", ""));
                                             Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                                             intent.setType("text/plain");
                                             intent.putExtra(Intent.EXTRA_SUBJECT,"Watch " + movieName + " trailer");
                                             Log.d(TAG,ym.getKey());
                                             String x = "https://www.youtube.com/watch?v=" + ym.getKey() + "\n" + "Share via Akira";
                                             Log.d(TAG,x);
                                             intent.putExtra(Intent.EXTRA_TEXT,x);
                                             startActivity(Intent.createChooser(intent,"Share via"));
                                         }
                                     });
                break;
        }
    }

    /*
    private void getYoutubeInfo(){
        Log.d(TAG, DeveloperKEY.BASE_URL + id + DeveloperKEY.TYPE_VIDEOS + DeveloperKEY.API_KEY);
        Ion.with(this)
                .load(DeveloperKEY.BASE_URL + intent.getStringExtra("id") + DeveloperKEY.TYPE_VIDEOS + DeveloperKEY.API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("RESULT--- ", result.toString());
                        JsonArray items = result.getAsJsonArray("results");
                        YoutubeModel ym = new YoutubeModel();
                        ym.setKey(items.get(0).getAsJsonObject().get("key").toString().replaceAll("^\"|\"$", ""));
                        youtube_key = ym.getKey();
                        Log.d(TAG,youtube_key);
                    }
                });
    }*/
}
