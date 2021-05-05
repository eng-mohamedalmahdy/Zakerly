package com.graduationproject.zakerly.navigation.homestudent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.core.constants.BottomNavigationConstants;
import com.graduationproject.zakerly.databinding.FragmentHomeStudentBinding;


public class HomeStudentFragment extends Fragment {

    FragmentHomeStudentBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeStudentBinding.inflate(inflater, container, false);
        //binding.setLifecycleOwner(getViewLifecycleOwner());
        ((MainActivity) getActivity()).setSelectedPage(BottomNavigationConstants.HOME_PAGE);
        ((MainActivity) getActivity()).setNavigationVisibility(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}