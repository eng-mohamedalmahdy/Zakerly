package com.graduationproject.zakerly.navigation.instructorAccountPage;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.StudentModel;

import java.util.ArrayList;

public class InstructorPageAdapter extends RecyclerView.Adapter<InstructorPageAdapter.ViewHolder> {
    private ArrayList<StudentModel> listOfStudent;

    public InstructorPageAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_student_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentModel student = listOfStudent.get(position);

        Glide.with(holder.itemView.getContext())
                .load(student.getImage())
                .placeholder(R.drawable.baseline_account_circle_24)
                .into(holder.customeStudentImage);
        holder.customeStudentName.setText(student.getName());
        holder.customeStudentClass.setText(student.getClassOfStudent());

        // this method customized to handle click Listener of remove item . .
        if (onDeleteClickListener != null) {
            holder.customeStudentIcRemove.setOnClickListener(view -> onDeleteClickListener.onItemClickListener(position));
        }

    }

    public void setListOfStudent(ArrayList<StudentModel> listOfStudent) {
        this.listOfStudent = listOfStudent;
        notifyDataSetChanged();
    }

    private OnItemClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnItemClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }


    @Override
    public int getItemCount() {
        return listOfStudent == null ? 0 : listOfStudent.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView customeStudentImage, customeStudentIcRemove;
        TextView customeStudentName, customeStudentClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customeStudentImage = itemView.findViewById(R.id.custome_student_image);
            customeStudentName = itemView.findViewById(R.id.custome_student_name);
            customeStudentClass = itemView.findViewById(R.id.custome_student_class);
            customeStudentIcRemove = itemView.findViewById(R.id.custome_student_ic_remove);
        }
    }
}
