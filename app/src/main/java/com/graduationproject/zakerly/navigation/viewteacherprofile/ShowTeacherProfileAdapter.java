package com.graduationproject.zakerly.navigation.viewteacherprofile;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.OpinionModel;
import java.util.ArrayList;


public class ShowTeacherProfileAdapter extends RecyclerView.Adapter<ShowTeacherProfileAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OpinionModel> listOfOpinion;

    public ShowTeacherProfileAdapter(Context context, ArrayList<OpinionModel> listOfOpinion) {
        this.context = context;
        this.listOfOpinion = listOfOpinion;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_students_opinion,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OpinionModel opinions= listOfOpinion.get(position);
       // Uri imageUri= Uri.parse(opinions.getImageStudent());
        int imageInt = opinions.getImageStudentInt();
        float rate=opinions.getNumStarRating();
        String opinion= opinions.getOpinion();
        String date= opinions.getDate();

        holder.imageStudentOpinion.setImageResource(imageInt);
        holder.rateOfStudentOpinion.setRating(rate);
        holder.textStudentOpinion.setText(opinion);
        holder.dateStudentOpinion.setText(date);

    }

    @Override
    public int getItemCount() {
        return listOfOpinion.size();
    }

    public void changeData(ArrayList<OpinionModel> listOfOpinion){
        this.listOfOpinion=listOfOpinion;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageStudentOpinion;
        TextView textStudentOpinion , dateStudentOpinion;
        RatingBar rateOfStudentOpinion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageStudentOpinion= itemView.findViewById(R.id.students_opinion_image);
            textStudentOpinion= itemView.findViewById(R.id.students_opinion_desc);
            rateOfStudentOpinion= itemView.findViewById(R.id.students_opinion_rating);
            dateStudentOpinion = itemView.findViewById(R.id.students_opinion_date);

        }
    }
}
