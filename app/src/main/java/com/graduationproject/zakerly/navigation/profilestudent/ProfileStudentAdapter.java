package com.graduationproject.zakerly.navigation.profilestudent;

import android.content.Context;
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

public class ProfileStudentAdapter extends RecyclerView.Adapter<ProfileStudentAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Instructor> list;

    public ProfileStudentAdapter(Context context, ArrayList<Instructor> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_teacher_card, parent, false);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView teacherImage;
        TextView teacherName, teacherJob, teacherDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherImage = itemView.findViewById(R.id.image_teacher);
            teacherName = itemView.findViewById(R.id.name_teacher);
            teacherJob = itemView.findViewById(R.id.job_teacher);
            teacherDesc = itemView.findViewById(R.id.desc_teacher);
        }
    }
}
