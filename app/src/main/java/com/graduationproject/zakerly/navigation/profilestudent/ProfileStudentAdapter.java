package com.graduationproject.zakerly.navigation.profilestudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.navigation.favorites.FavoriteDataClass;

import java.util.ArrayList;

public class ProfileStudentAdapter extends RecyclerView.Adapter<ProfileStudentAdapter.ViewHolder> {


    Context context ;
    ArrayList<FavoriteDataClass> list;

    public ProfileStudentAdapter(Context context, ArrayList<FavoriteDataClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_teacher_view,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteDataClass dataClass = list.get(position);
        holder.teacherName.setText(dataClass.getTeacherName());
        holder.teacherJob.setText(dataClass.getTeacherJob());
        holder.teacherDesc.setText(dataClass.getTeacherDesc());
        holder.teacherImage.setImageResource(dataClass.getImageUri());
    }

    @Override
    public int getItemCount() {
       return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView teacherImage ;
        TextView teacherName , teacherJob , teacherDesc;
     public ViewHolder(@NonNull View itemView) {
         super(itemView);
         teacherImage = itemView.findViewById(R.id.image_teacher);
         teacherName=itemView.findViewById(R.id.name_teacher);
         teacherJob=itemView.findViewById(R.id.job_teacher);
         teacherDesc=itemView.findViewById(R.id.desc_teacher);
     }
 }
}
