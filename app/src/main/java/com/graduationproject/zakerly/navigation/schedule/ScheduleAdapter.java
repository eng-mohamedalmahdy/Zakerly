package com.graduationproject.zakerly.navigation.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.Schedule;
import com.graduationproject.zakerly.core.util.Util;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    ArrayList<Schedule> schedules;

    public ScheduleAdapter() {

    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        holder.txtDate.setText(Util.getDateFromStamp(schedule.getScheduleTime()));
        holder.txtTime.setText(Util.getTimeFromStamp(schedule.getScheduleTime()));
        holder.txtLesson.setText(schedule.getLessonName());
        holder.txtStudent.setText(schedule.getStudentName());
    }

    @Override
    public int getItemCount() {
        return schedules==null?0:schedules.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtTime, txtLesson, txtStudent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.class_date);
            txtTime = itemView.findViewById(R.id.class_time);
            txtLesson = itemView.findViewById(R.id.class_lesson);
            txtStudent = itemView.findViewById(R.id.class_student);
        }
    }
}
