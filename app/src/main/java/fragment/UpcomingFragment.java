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

import adapter.UpcomingAdapter;
import model.UpcomingModel;

public class UpcomingFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String base_url = "http://api.themoviedb.org/3/movie/upcoming";
    private final String api_key = "?api_key=5c031f60eac7abcd934658309f30bb1a";
    private List<UpcomingModel> upmList = new ArrayList<>();

    public UpcomingFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("VIEW HERE---- ","yay");
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

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
                            UpcomingModel upm = new UpcomingModel();
                            upm.setAdult(items.get(i).getAsJsonObject().get("adult").toString());
                            upm.setBackdrop_path(items.get(i).getAsJsonObject().get("backdrop_path").toString().replaceAll("^\"|\"$",""));
                            upm.setId(items.get(i).getAsJsonObject().get("id").getAsLong());
                            upm.setOrg_title(items.get(i).getAsJsonObject().get("original_title").toString().replaceAll("^\"|\"$",""));
                            upm.setRel_day(items.get(i).getAsJsonObject().get("release_date").toString().replaceAll("^\"|\"$",""));
                            upm.setPoster_path(items.get(i).getAsJsonObject().get("poster_path").toString().replaceAll("^\"|\"$",""));
                            upm.setPopularity(items.get(i).getAsJsonObject().get("popularity").getAsFloat());
                            upm.setVote_arg(items.get(i).getAsJsonObject().get("vote_average").getAsFloat());
                            upm.setVote_count(items.get(i).getAsJsonObject().get("vote_count").getAsInt());
                            upmList.add(upm);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        recyclerView = (RecyclerView)view.findViewById(R.id.upcoming_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UpcomingAdapter(upmList, getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }
}
