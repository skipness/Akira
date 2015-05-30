package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import adapter.PopularAdapter;
import model.PopularModel;

public class PopularFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String base_url = "http://api.themoviedb.org/3/movie/popular";
    private final String api_key = "?api_key=5c031f60eac7abcd934658309f30bb1a";
    private List<PopularModel> pmList = new ArrayList<>();

    public PopularFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        Ion.with(this)
                .load(base_url+api_key)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null){
                            Log.d("EXCEPTION---- ",e.toString());
                        }
                        JsonArray items = result.getAsJsonArray("results");
                        for (int i = 0; i < items.size(); i++) {
                            PopularModel pm = new PopularModel();
                            pm.setAdult(items.get(i).getAsJsonObject().get("adult").toString());
                            pm.setBackdrop_path(items.get(i).getAsJsonObject().get("backdrop_path").toString().replaceAll("^\"|\"$", ""));
                            pm.setId(items.get(i).getAsJsonObject().get("id").getAsLong());
                            pm.setOrg_title(items.get(i).getAsJsonObject().get("original_title").toString().replaceAll("^\"|\"$", ""));
                            pm.setRel_day(items.get(i).getAsJsonObject().get("release_date").toString().replaceAll("^\"|\"$", ""));
                            pm.setPoster_path(items.get(i).getAsJsonObject().get("poster_path").toString().replaceAll("^\"|\"$", ""));
                            pm.setPopularity(items.get(i).getAsJsonObject().get("popularity").getAsFloat());
                            pm.setVote_arg(items.get(i).getAsJsonObject().get("vote_average").getAsFloat());
                            pm.setVote_count(items.get(i).getAsJsonObject().get("vote_count").getAsInt());
                            pmList.add(pm);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        recyclerView = (RecyclerView)view.findViewById(R.id.popular_recycler_view);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PopularAdapter(pmList, getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

}
