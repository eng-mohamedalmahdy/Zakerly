package com.graduationproject.zakerly.navigation.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.Schedule;
import com.graduationproject.zakerly.core.models.Schedule2;
import com.graduationproject.zakerly.core.util.Util;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
        Context context;
        ArrayList<Schedule2>schedules;

    public ScheduleAdapter(Context context, ArrayList<Schedule2> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_schedule,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Schedule2 schedule  = schedules.get(position);
            holder.txtDate.setText(schedule.getDate());
            holder.txtTime.setText(schedule.getTime());
            holder.txtLesson.setText(schedule.getLessonName());
            holder.txtStudent.setText(schedule.getStudentName());
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDate,txtTime,txtLesson,txtStudent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.class_date);
            txtTime = itemView.findViewById(R.id.class_time);
            txtLesson = itemView.findViewById(R.id.class_lesson);
            txtStudent = itemView.findViewById(R.id.class_student);
        }
    }
}
