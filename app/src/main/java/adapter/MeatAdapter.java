package adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.skip.akira.DeveloperKEY;
import com.skip.akira.Meat;
import com.skip.akira.R;

import java.util.List;

import model.ContentModel;

public class MeatAdapter extends RecyclerView.Adapter<MeatAdapter.ViewHolder> {

    private static final String TAG = "MEAT ADAPTER ---- ";
    private List<ContentModel> contentModels;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView share;
        protected TextView movie_name;
        protected TextView rel_day;
        protected TextView time;
        protected TextView tagline;
        protected TextView vote_avg;
        protected TextView overview;

        public ViewHolder(View view) {
            super(view);
                movie_name = (TextView) view.findViewById(R.id.movie_name);
                rel_day = (TextView) view.findViewById(R.id.rel_day);
                time = (TextView) view.findViewById(R.id.runtime);
                tagline = (TextView) view.findViewById(R.id.tagline);
                vote_avg = (TextView) view.findViewById(R.id.vote_avg);
                overview = (TextView) view.findViewById(R.id.overview);
                share = (ImageView) view.findViewById(R.id.ic_share);
        }
    }

    public MeatAdapter(List<ContentModel> models){
        contentModels = models;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_content, viewGroup, false);
            ViewHolder vItem = new ViewHolder(view);
            return vItem;
    }

    @Override
    public void onBindViewHolder(MeatAdapter.ViewHolder holder, int position) {
        ContentModel cm = contentModels.get(position);
        Log.d(TAG, "POSTER PATH: " + cm.getBackdrop_path());
            Log.d(TAG, "MOVIE DATA LOADED");
            holder.movie_name.setText(cm.getOrg_title());
            holder.rel_day.setText(cm.getRel_date() + " | " + cm.getStatus());
            holder.time.setText(String.valueOf(cm.getRuntime()) + "Minutes");
            holder.tagline.setText("\"" + cm.getTag_line() + "\"");
            holder.vote_avg.setText(String.valueOf(cm.getVote_arg() / 2));
            holder.overview.setText(cm.getOverview());
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String shareBody = "";
                    intent.putExtra(Intent.EXTRA_SUBJECT,"");
                    intent.putExtra(Intent.EXTRA_TEXT,shareBody);

                }
            });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"SIZE "+String.valueOf(contentModels.size()));
        return contentModels.size();
    }
}
