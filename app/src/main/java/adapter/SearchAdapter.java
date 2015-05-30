package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.koushikdutta.ion.Ion;
import com.skip.akira.Meat;
import com.skip.akira.R;

import java.util.List;

import model.HistoryModel;
import model.NowplayingModel;
import model.SearchModel;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private static final String TAG = "SEARCH ---- ";
    private static Context context;
    private List<SearchModel> searchModels;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected CardView cardView;
        protected ImageView poster;
        protected TextView movie_name;
        protected TextView rel_day;
        protected RatingBar ratingBar;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view.findViewById(R.id.card_view);
            poster = (ImageView)view.findViewById(R.id.thumbnail);
            movie_name = (TextView)view.findViewById(R.id.movie_name);
            rel_day = (TextView)view.findViewById(R.id.rel_day);
            ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        }
    }

    public SearchAdapter(List<SearchModel> models, Context context){
        searchModels = models;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_search, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        final SearchModel sm = searchModels.get(position);
        String img_base_url = "http://image.tmdb.org/t/p/";
        String img_size = "w780";
        holder.movie_name.setText(sm.getOrg_title());
        holder.rel_day.setText("Release Day: "+sm.getRel_day());
        holder.ratingBar.setRating(sm.getVote_arg()/2);
        Ion.with(holder.poster)
                .placeholder(R.drawable.ic_action_picture)
                .error(R.drawable.ic_action_picture)
                .load(img_base_url + img_size + sm.getPoster_path());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                intent.setClass(context,Meat.class);
                intent.putExtra("id",String.valueOf(sm.getId()));
                intent.putExtra("poster_path",sm.getPoster_path());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
       return searchModels.size();
    }

}