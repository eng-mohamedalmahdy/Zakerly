package com.graduationproject.zakerly.navigation.homestudent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.adapters.TeacherCardAdapter;
import com.graduationproject.zakerly.core.constants.BottomNavigationConstants;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentHomeStudentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;


public class HomeStudentFragment extends Fragment {

    public static final String TAG = "HomeStudent";
    FragmentHomeStudentBinding binding;
    HomeStudentPageViewModel viewModel;
    TeacherCardAdapter recommendedListAdapter, topListAdapter, discoverListAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeStudentBinding.inflate(inflater, container, false);
        ((MainActivity) requireActivity()).setSelectedPage(BottomNavigationConstants.HOME_PAGE);
        ((MainActivity) requireActivity()).setNavigationVisibility(true);
        viewModel = new ViewModelProvider(this).get(HomeStudentPageViewModel.class);
        recommendedListAdapter = new TeacherCardAdapter(this, R.layout.list_item_teacher_card_small);
        topListAdapter = new TeacherCardAdapter(this, R.layout.list_item_teacher_card_small);
        discoverListAdapter = new TeacherCardAdapter(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recommendedList.setAdapter(recommendedListAdapter);
        binding.topList.setAdapter(topListAdapter);
        binding.discoverList.setAdapter(discoverListAdapter);
        initListsData();
    }

    private void initListsData() {

        viewModel.getAllInstructors().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Instructor> allInstructors = new ArrayList<>();
                ArrayList<Instructor> recommendedInstructors = new ArrayList<>();


                snapshot.getChildren().forEach(instructor -> allInstructors.add(instructor.getValue(Instructor.class)));
                Collections.shuffle(allInstructors);
                FirebaseDataBaseClient.getInstance().getCurrentUser().addOnSuccessListener(s -> {
                    Student student = s.getValue(Student.class);
                    student.getUser()
                            .getInterests()
                            .forEach(specialisation -> recommendedInstructors.addAll(allInstructors
                                    .stream()
                                    .filter(instructor -> instructor.getUser().getInterests() != null &&
                                            instructor.getUser().getInterests().contains(specialisation))
                                    .collect(Collectors.toCollection(ArrayList::new))));

                    recommendedListAdapter.setInstructors(recommendedInstructors);
                });
                discoverListAdapter.setInstructors(allInstructors);
                Log.d(TAG, "initListsData: " + allInstructors);
                topListAdapter.setInstructors(allInstructors.stream()
                        .sorted((i1, i2) -> Double.compare(i1.getRate(), i2.getRate()))
                        .collect(Collectors.toCollection(ArrayList::new)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getDetails());
            }
        });
    }
}