package com.graduationproject.zakerly.navigation.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;

import java.util.ArrayList;

import io.realm.RealmList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    ArrayList<Instructor> list;

    public FavoriteAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_teacher_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instructor dataClass = list.get(position);
        String fullName = dataClass.getUser().getFirstName() + " " + dataClass.getUser().getLastName();
        RealmList<Specialisation> specialisations = dataClass.getUser().getInterests();
        String jobName = specialisations.isEmpty() ? "" : specialisations.get(0).getAr();
        holder.teacherName.setText(fullName);
        holder.teacherJob.setText(jobName);
        holder.teacherDesc.setText(dataClass.getBio());
        Glide.with(holder.itemView.getContext())
                .load(dataClass.getUser().getProfileImg())
                .placeholder(R.drawable.baseline_account_circle_24)
                .into(holder.teacherImage);

        if (onFavoriteClickListener != null) {
            holder.teacherFavorite.setOnClickListener(view -> {
                onFavoriteClickListener.onItemClick(position);
            });
        }

    }

    OnItemClockListener onFavoriteClickListener;

    public void setOnFavoriteClickListener(OnItemClockListener onFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener;
    }

    interface OnItemClockListener {
        void onItemClick(int position);
    }

    public void setList(ArrayList<Instructor> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView teacherImage, teacherFavorite;
        TextView teacherName, teacherJob, teacherDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherImage = itemView.findViewById(R.id.image_teacher);
            teacherName = itemView.findViewById(R.id.name_teacher);
            teacherJob = itemView.findViewById(R.id.job_teacher);
            teacherDesc = itemView.findViewById(R.id.desc_teacher);
            teacherFavorite = itemView.findViewById(R.id.favorite_teacher);
        }
    }
}







