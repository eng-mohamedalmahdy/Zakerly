package com.graduationproject.zakerly.navigation.instructorAccountPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.ConnectionModel;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.models.StudentModel;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentInstructorPageBinding;

import java.util.ArrayList;

public class InstructorPageFragment extends Fragment {

    private static final String TAG = "InstructorPageFragment";
    private FragmentInstructorPageBinding binding;
    private InstructorPageViewModel viewModel;
    private InstructorPageAdapter adapter;
    private RecyclerView mRecyclerView;
    private TextView numOfStudent;
    ImageView profileImage, icFavorite, icVideoCall, editProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInstructorPageBinding.inflate(inflater, container, false);
        viewModel = new InstructorPageViewModelFactory(new InstructorPageRepository()).create(InstructorPageViewModel.class);
        ((MainActivity) requireActivity()).setNavigationVisibility(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
    }

    private void initListeners() {

        FirebaseDataBaseClient.getInstance().getProfileImageUrl()
                .addOnSuccessListener(snapshot ->
                {
                    Log.d(TAG, "initListener: Image Loaded");
                    Glide.with(requireContext())
                            .load(snapshot.getValue(String.class))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.baseline_account_circle_24)
                            .into(binding.instructorPageImage);

                })
                .addOnFailureListener(e -> Log.d(TAG, "initListener: " + e.getMessage()));


        FirebaseDataBaseClient.getInstance().getCurrentUser().addOnSuccessListener(snapshot -> {
            Student currentStudent = snapshot.getValue(Student.class);
            binding.instructorPageName.setText((currentStudent.getUser().getFirstName() + " " + currentStudent.getUser().getLastName()));
        });

        FirebaseDataBaseClient.getInstance().getConnectionsForCurrentUser().addOnSuccessListener(connections -> {
            ArrayList<StudentModel> students = new ArrayList<>();

            connections.getChildren().forEach(c ->
            {
                ConnectionModel connectionModel = c.getValue(ConnectionModel.class);
                Log.d(TAG, "initListeners: " + connectionModel);
                if (connectionModel != null && connectionModel.isCurrentlyConnected()) {
                    FirebaseDataBaseClient
                            .getInstance()
                            .getUserByUid(connectionModel.getToUid())
                            .addOnSuccessListener(s -> {
                                Student student = s.getValue(Student.class);
                                StudentModel studentModel = new StudentModel(student.getUser().getProfileImg(),
                                        student.getUser().getFirstName(),
                                        connectionModel.getLatestTopic());
                                students.add(studentModel);
                                adapter.setListOfStudent(students);
                                numOfStudent.setText(Integer.toString(students.size()));
                                Log.d(TAG, "initListener: " + students);
                            });
                }
            });
        });
        editProfile.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(InstructorPageFragmentDirections.actionInstructorPageFragmentToEditProfileFragment2(UserTypes.TYPE_INSTRUCTOR)));
    }


    private void initViews() {
        profileImage = binding.instructorPageImage;
        icFavorite = binding.instructorPageIcFavorite;
        icVideoCall = binding.instructorPageIcVideocall;
        editProfile = binding.instructorPageIcNote;
        numOfStudent = binding.instructorPageNumOfStudent;
        mRecyclerView = binding.instructorPageRecyclerView;
        adapter = new InstructorPageAdapter();
        mRecyclerView.setAdapter(adapter);
    }

}