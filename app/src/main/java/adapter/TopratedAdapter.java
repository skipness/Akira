package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.skip.akira.DeveloperKEY;
import com.skip.akira.Meat;
import com.skip.akira.R;

import java.util.List;

import model.TopratedModel;
import model.UpcomingModel;


public class TopratedAdapter extends RecyclerView.Adapter<TopratedAdapter.ViewHolder>{

    private List<TopratedModel> topratedModels;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView poster;

        public ViewHolder(View view){
            super(view);
            poster = (ImageView)view.findViewById(R.id.thumbnail);
        }
    }

    public TopratedAdapter(List<TopratedModel> models, Context context){
        topratedModels = models;
        this.context = context;
        Log.d("MODELS SIZE----",String.valueOf(models.size()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_toprated, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TopratedModel trm = topratedModels.get(position);
        Log.d("IMG_PATH---- ", position + " " + DeveloperKEY.IMG_BASE_URL + "w500" + trm.getPoster_path());
        Ion.with(holder.poster)
                .fitXY()
                .centerCrop()
                .placeholder(R.drawable.ic_action_picture)
                .error(R.drawable.ic_action_picture)
                .load(DeveloperKEY.IMG_BASE_URL + "w500" + trm.getPoster_path());
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                intent.setClass(context,Meat.class);
                intent.putExtra("id",String.valueOf(trm.getId()));
                intent.putExtra("poster_path",trm.getPoster_path());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("SIZE---- ",String.valueOf(topratedModels.size()));
        return topratedModels.size();
    }

}
