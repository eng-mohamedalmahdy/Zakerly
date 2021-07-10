package com.graduationproject.zakerly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.ItemSearchModel;

import java.util.ArrayList;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ViewHolder> {

    ArrayList<ItemSearchModel> items ;
    Context context ;

    public ItemSearchAdapter(ArrayList<ItemSearchModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_item_search,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemSearchModel item = items.get(position);
        holder.mName.setText(item.getName());
        holder.mJob.setText(item.getJob());
        holder.mRate.setRating((float)item.getRate());
        Glide.with(holder.itemView.getContext())
                .load(item.getImageProfile())
                .into(holder.mImage);

        if (onItemSearchClickListener !=null){
            onItemSearchClickListener.onItemSearch(position, item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    OnItemSearchClickListener onItemSearchClickListener;
    public interface OnItemSearchClickListener{
        void onItemSearch(int position, ItemSearchModel itemSearchModel);
    }


    public void setItems(ArrayList<ItemSearchModel> items){
        this.items= items;
}

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImage;
        TextView mName, mJob;
        RatingBar mRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           onBind();
        }

        void onBind(){
            mImage = itemView.findViewById(R.id.item_image);
            mName = itemView.findViewById(R.id.item_name);
            mJob = itemView.findViewById(R.id.item_job);
            mRate = itemView.findViewById(R.id.item_rating);
        }

    }
}
