package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.skip.akira.DeveloperKEY;
import com.skip.akira.Meat;
import com.skip.akira.R;

import java.util.List;

import model.PopularModel;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>{

    private List<PopularModel> popularModels;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        protected ImageView poster;

        public ViewHolder(View view){
            super(view);
            poster = (ImageView)view.findViewById(R.id.thumbnail);
        }
    }

    public PopularAdapter(List<PopularModel> models, Context context){
        popularModels = models;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_popular, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PopularModel pm = popularModels.get(position);
        Log.d("IMG_PATH---- ", DeveloperKEY.IMG_BASE_URL + "w500" + pm.getPoster_path());
        Ion.with(holder.poster)
                .fitXY()
                .centerCrop()
                .placeholder(R.drawable.ic_action_picture)
                .error(R.drawable.ic_action_picture)
                .load(DeveloperKEY.IMG_BASE_URL + "w500" + pm.getPoster_path());
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent();
                intent.setClass(context,Meat.class);
                intent.putExtra("id",String.valueOf(pm.getId()));
                intent.putExtra("poster_path",pm.getPoster_path());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularModels.size();
    }

}
