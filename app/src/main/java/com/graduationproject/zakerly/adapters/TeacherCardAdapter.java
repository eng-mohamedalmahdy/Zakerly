package com.graduationproject.zakerly.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.StudentAppNavigationDirections;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import io.realm.RealmList;

public class TeacherCardAdapter extends RecyclerView.Adapter<TeacherCardAdapter.ViewHolder> {

    ArrayList<Instructor> instructors;
    ArrayList<String> favoritesUid;
    ArrayList<Boolean> favoritesResults;
    int layoutId;
    Fragment currentFragment;

    public TeacherCardAdapter(Fragment currentFragment) {
        this.instructors = new ArrayList<>();
        this.layoutId = R.layout.list_item_teacher_card;
        this.currentFragment = currentFragment;
        this.favoritesUid = new ArrayList<>();

    }

    public TeacherCardAdapter(Fragment currentFragment, int layoutId) {
        this.layoutId = layoutId;
        this.currentFragment = currentFragment;
        this.instructors = new ArrayList<>();
        this.favoritesUid = new ArrayList<>();


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
        if (dataClass != null && dataClass.getUser() != null) {
            String fullName = dataClass.getUser().getFirstName() + " " + dataClass.getUser().getLastName();
            String jobName = dataClass.getTitle();
            holder.teacherName.setText(fullName);
            holder.teacherJob.setText(jobName);
            holder.teacherDesc.setText(dataClass.getBio());
            holder.ratingBar.setRating(dataClass.getAverageRate());
            holder.teacherFavorite.setImageResource(favoritesResults.get(position) ? R.drawable.ic_stargreen : R.drawable.ic_star);
            Glide.with(holder.itemView.getContext())
                    .load(dataClass.getUser().getProfileImg())
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .into(holder.teacherImage);
            holder.container.setOnClickListener(v ->
                    NavHostFragment.findNavController(currentFragment).
                            navigate(StudentAppNavigationDirections
                                    .navigateToShowTeacherProfile(instructors
                                            .get(position))));

            holder.teacherFavorite.setOnClickListener(view -> {
                FirebaseDataBaseClient.getInstance().setFavorite(
                        FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid(),
                        instructors.get(position).getUser().getUID(), !favoritesResults.get(position)).addOnSuccessListener(command -> Toasty.success(view.getContext(), R.string.done).show());
                favoritesResults.set(position, !favoritesResults.get(position));
                notifyDataSetChanged();
            });
        }

    }


    public void setInstructors(ArrayList<Instructor> instructors) {
        this.instructors = instructors;
        FirebaseDataBaseClient.getInstance().getFavorites(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid())
                .addOnSuccessListener(snapshot -> {
                    snapshot.getChildren().forEach(snapshot1 -> {
                        if (snapshot1.getValue(Boolean.class)) favoritesUid.add(snapshot1.getKey());
                    });
                    favoritesResults = favoriteMatches(instructors, favoritesUid);
                    notifyDataSetChanged();
                    Log.d("TAG", "setInstructors: " + favoritesResults);
                });
    }


    @Override
    public int getItemCount() {
        return instructors == null ? 0 : instructors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView teacherImage, teacherFavorite;
        TextView teacherName, teacherJob, teacherDesc;
        ConstraintLayout container;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherImage = itemView.findViewById(R.id.image_teacher);
            teacherName = itemView.findViewById(R.id.name_teacher);
            teacherJob = itemView.findViewById(R.id.job_teacher);
            teacherDesc = itemView.findViewById(R.id.desc_teacher);
            teacherFavorite = itemView.findViewById(R.id.favorite_teacher);
            container = itemView.findViewById(R.id.contact_container);
            ratingBar = itemView.findViewById(R.id.ratingbar_teacher);
        }
    }

    ArrayList<Boolean> favoriteMatches(ArrayList<Instructor> originalList, ArrayList<String> favorites) {
        ArrayList<Boolean> results = new ArrayList<>();
        originalList.forEach(instructor -> {
            if (instructor != null && instructor.getUser() != null)
                results.add(favorites.contains(instructor.getUser().getUID()));

        });
        return results;
    }
}







