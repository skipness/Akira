package adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.skip.akira.DeveloperKEY;
import com.skip.akira.Meat;
import com.skip.akira.R;
import com.skip.akira.YoutubePlayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import model.HistoryModel;
import model.YoutubeModel;
import util.Database;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private static final String TAG = "History ---- ";
    private static Context context;
    private List<HistoryModel> historyModels;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected YouTubeThumbnailView thumbnailView;
        protected TextView movie_name;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            thumbnailView = (YouTubeThumbnailView)view.findViewById(R.id.thumbnail);
            movie_name = (TextView)view.findViewById(R.id.movie_name);
        }

        @Override
        public void onClick(View view) {
            //context.startActivity(new Intent(context,Meat.class));
        }
    }

    public HistoryAdapter(List<HistoryModel> models, Context context){
        historyModels = models;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        HistoryModel hm = historyModels.get(position);
        holder.movie_name.setText(hm.getMovie_name());
    }

    @Override
    public int getItemCount() {
       return historyModels.size();
    }

}