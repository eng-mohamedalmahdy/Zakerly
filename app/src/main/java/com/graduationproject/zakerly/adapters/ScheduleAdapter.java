package com.graduationproject.zakerly.adapters;

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
    private ArrayList<Schedule> schedules;
    private final int layoutId;

    public ScheduleAdapter() {
        this.layoutId = R.layout.list_item_home_schedule;
    }

    public ScheduleAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return schedules == null ? 0 : schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date, time, lesson, student;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.class_date);
            time = itemView.findViewById(R.id.class_time);
            lesson = itemView.findViewById(R.id.class_lesson);
            student = itemView.findViewById(R.id.class_student);
        }

        public void bind(Schedule schedule) {
            date.setText(Util.getDateFromStamp(schedule.getScheduleTime()));
            time.setText(Util.getTimeFromStamp(schedule.getScheduleTime()));
            lesson.setText(schedule.getLessonName());
            student.setText(schedule.getStudentName());
        }
    }
}
