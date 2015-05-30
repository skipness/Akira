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

import com.koushikdutta.ion.Ion;
import com.skip.akira.Meat;
import com.skip.akira.R;

import java.util.List;

import model.UpcomingModel;


public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder>{

    private List<UpcomingModel> upcomingModels;
    private static Context context;

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

    public UpcomingAdapter(List<UpcomingModel> models, Context context){
        upcomingModels = models;
        this.context = context;
        Log.d("MODELS SIZE----",String.valueOf(models.size()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_nowplaying, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UpcomingModel upm = upcomingModels.get(position);
        String img_base_url = "http://image.tmdb.org/t/p/";
        String img_size = "w780";
        holder.movie_name.setText(upm.getOrg_title());
        holder.rel_day.setText("Release Day: "+upm.getRel_day());
        holder.ratingBar.setRating(upm.getVote_arg()/2);
        Log.d("IMG_PATH---- ", position + " " + img_base_url + img_size + upm.getPoster_path());
        Ion.with(holder.poster)
                .placeholder(R.drawable.ic_action_picture)
                .error(R.drawable.ic_action_picture)
                .load(img_base_url + img_size + upm.getPoster_path());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                intent.setClass(context,Meat.class);
                intent.putExtra("id",String.valueOf(upm.getId()));
                intent.putExtra("poster_path",upm.getPoster_path());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("SIZE---- ",String.valueOf(upcomingModels.size()));
        return upcomingModels.size();
    }

}
