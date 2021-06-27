package com.graduationproject.zakerly.navigation.viewteacherprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.GenericTypeIndicator;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.OpinionModel;
import com.graduationproject.zakerly.databinding.FragmentShowTeacherProfileBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTeacherProfileFragment extends Fragment {

    private FragmentShowTeacherProfileBinding binding;
    private ShowTeacherProfileFragmentArgs args;
    private ShowTeacherProfileViewModel viewModel;
    RecyclerView mRecyclerView;
    ShowTeacherProfileAdapter adapter;
    ImageView imageBack, imageProfile, imageDisableFavorite, imageVideoCall, imagePrice, imageRequest, imageSend;
    TextView txtProfileName, txtProfileJob, txtNumOfStudents, txtDescriptionBio, txtPercentageOfRating;
    TextView txtRate5, txtRate4, txtRate3, txtRate2, txtRate1;
    RatingBar rate5, rate4, rate3, rate2, rate1, rateInstructor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowTeacherProfileBinding.inflate(inflater, container, false);
        args = ShowTeacherProfileFragmentArgs.fromBundle(requireArguments());
        viewModel = new ShowTeacherProfileViewModelFactory(new ShowTeacherProfileRepository()).create(ShowTeacherProfileViewModel.class);
        binding.setInstructor(args.getInstructor());
        ((MainActivity) requireActivity()).setNavigationVisibility(false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setUpViews();
        viewModel.getOpinions().addOnSuccessListener(snapshot -> {
            GenericTypeIndicator<HashMap<String, OpinionModel>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, OpinionModel>>() {
            };
            Map<String, OpinionModel> objectHashMap = snapshot.getValue(objectsGTypeInd);
            if (objectHashMap == null) return;
            ArrayList<OpinionModel> objectArrayList = new ArrayList<>(objectHashMap.values());
            mRecyclerView.setAdapter(new ShowTeacherProfileAdapter(requireContext(), objectArrayList));
        });
    }

    // this method to init all views in XML
    private void initViews() {
        mRecyclerView = binding.teacherProfileRecyclerView;
        imageBack = binding.teacherProfileIcBack;
        imageProfile = binding.teacherProfileImage;
        imageDisableFavorite = binding.teacherProfileIcDisableFavorite;
        imageVideoCall = binding.teacherProfileIcDisableVideocall;
        imagePrice = binding.teacherProfileImagePrice;
        imageRequest = binding.teacherProfileIcAdd;
        imageSend = binding.teacherProfileIcSend;
        txtProfileName = binding.teacherProfileName;
        txtProfileJob = binding.teacherProfileJob;
        txtNumOfStudents = binding.teacherProfileNumOfStudent;
        txtDescriptionBio = binding.teacherProfileDescriptionBio;
        txtPercentageOfRating = binding.teacherProfileAverageRate;
        txtRate5 = binding.teacherProfileTxtRate5;
        txtRate4 = binding.teacherProfileTxtRate4;
        txtRate3 = binding.teacherProfileTxtRate3;
        txtRate2 = binding.teacherProfileTxtRate2;
        txtRate1 = binding.teacherProfileTxtRate1;
        rate5 = binding.teacherProfileRate5;
        rate4 = binding.teacherProfileRate4;
        rate3 = binding.teacherProfileRate3;
        rate2 = binding.teacherProfileRate2;
        rate1 = binding.teacherProfileRate1;
        rateInstructor = binding.teacherProfileRateInstructor;
    }

    private void setUpViews() {
        Glide.with(requireContext())
                .load(args.getInstructor().getUser().getProfileImg())
                .placeholder(R.drawable.baseline_account_circle_24)
                .into(imageProfile);
    }

}








