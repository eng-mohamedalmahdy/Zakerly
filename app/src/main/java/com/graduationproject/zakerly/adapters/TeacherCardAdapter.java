package com.graduationproject.zakerly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.StudentAppNavigationDirections;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;

import java.util.ArrayList;

import io.realm.RealmList;

public class TeacherCardAdapter extends RecyclerView.Adapter<TeacherCardAdapter.ViewHolder> {

    ArrayList<Instructor> instructors;
    int layoutId;
    Fragment currentFragment;

    public TeacherCardAdapter(Fragment currentFragment) {
        this.instructors = new ArrayList<>();
        this.layoutId = R.layout.list_item_teacher_card;
        this.currentFragment = currentFragment;
    }

    public TeacherCardAdapter(Fragment currentFragment, int layoutId) {
        this.layoutId = layoutId;
        this.currentFragment = currentFragment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instructor dataClass = instructors.get(position);
        String fullName = dataClass.getUser().getFirstName() + " " + dataClass.getUser().getLastName();
        String jobName = dataClass.getTitle();
        holder.teacherName.setText(fullName);
        holder.teacherJob.setText(jobName);
        holder.teacherDesc.setText(dataClass.getBio());
        Glide.with(holder.itemView.getContext())
                .load(dataClass.getUser().getProfileImg())
                .placeholder(R.drawable.baseline_account_circle_24)
                .into(holder.teacherImage);
        holder.itemView.setOnClickListener(v ->
                NavHostFragment.findNavController(currentFragment).
                        navigate(StudentAppNavigationDirections
                                .navigateToShowTeacherProfile(instructors
                                        .get(position))));

        if (onFavoriteClickListener != null) {
            holder.teacherFavorite.setOnClickListener(view ->
                    onFavoriteClickListener.onItemClick(position));
        }

    }

    public OnItemClickListener onFavoriteClickListener;

    public void setOnFavoriteClickListener(OnItemClickListener onFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setInstructors(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return instructors == null ? 0 : instructors.size();
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







