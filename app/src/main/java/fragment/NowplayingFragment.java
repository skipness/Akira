package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.skip.akira.R;

import java.util.ArrayList;
import java.util.List;

import adapter.NowplayingAdapter;
import model.NowplayingModel;


public class NowplayingFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String base_url = "http://api.themoviedb.org/3/movie/now_playing";
    private final String api_key = "?api_key=5c031f60eac7abcd934658309f30bb1a";
    private List<NowplayingModel> npmList = new ArrayList<>();

    public NowplayingFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("VIEW HERE---- ", "yay");
        View view = inflater.inflate(R.layout.fragment_nowplaying, container, false);

        Ion.with(this)
                .load(base_url+api_key)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null){
                            Log.d("EXCEPTION---- ",e.toString());
                        }
                        Log.d("RESULT--- ", result.toString());
                        JsonArray items = result.getAsJsonArray("results");
                        for (int i = 0; i < items.size(); i++) {
                            NowplayingModel npm = new NowplayingModel();
                            npm.setAdult(items.get(i).getAsJsonObject().get("adult").toString());
                            npm.setBackdrop_path(items.get(i).getAsJsonObject().get("backdrop_path").toString().replaceAll("^\"|\"$",""));
                            npm.setId(items.get(i).getAsJsonObject().get("id").getAsLong());
                            npm.setOrg_title(items.get(i).getAsJsonObject().get("original_title").toString().replaceAll("^\"|\"$",""));
                            npm.setRel_day(items.get(i).getAsJsonObject().get("release_date").toString().replaceAll("^\"|\"$",""));
                            npm.setPoster_path(items.get(i).getAsJsonObject().get("poster_path").toString().replaceAll("^\"|\"$",""));
                            npm.setPopularity(items.get(i).getAsJsonObject().get("popularity").getAsFloat());
                            npm.setVote_arg(items.get(i).getAsJsonObject().get("vote_average").getAsFloat());
                            npm.setVote_count(items.get(i).getAsJsonObject().get("vote_count").getAsInt());
                            npmList.add(npm);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        recyclerView = (RecyclerView)view.findViewById(R.id.now_playing_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NowplayingAdapter(npmList,getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

}
