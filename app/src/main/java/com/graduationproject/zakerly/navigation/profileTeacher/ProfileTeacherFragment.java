package com.graduationproject.zakerly.navigation.profileTeacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.OpinionModel;
import com.graduationproject.zakerly.databinding.FragmentProfileStudentBinding;
import com.graduationproject.zakerly.databinding.FragmentTeacherProfileBinding;
import com.graduationproject.zakerly.navigation.profilestudent.ProfileStudentRepository;
import com.graduationproject.zakerly.navigation.profilestudent.ProfileStudentViewModel;
import com.graduationproject.zakerly.navigation.profilestudent.ProfileStudentViewModelFactory;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProfileTeacherFragment extends Fragment {

    private FragmentTeacherProfileBinding binding;
    private ProfileTeacherViewModel viewModel;
    RecyclerView mRecyclerView;
    ProfileTeacherAdapter adapter;
    ImageView imageBack, imageProfile, imageDisableFavorite, imageVideoCall, imagePrice, imageRequest, imageSend;
    TextView txtProfileName, txtProfileJob, txtNumOfStudents, txtDescriptionBio, txtPercentageOfRating;
    TextView txtRate5, txtRate4, txtRate3, txtRate2, txtRate1;
    RatingBar rate5, rate4, rate3, rate2, rate1, rateInstructor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeacherProfileBinding.inflate(inflater, container, false);
        viewModel = new ProfileTeacherViewModelFactory(new ProfileTeacherRepository()).create(ProfileTeacherViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        adapter= new ProfileTeacherAdapter(getActivity(),fillRecyclerView());
        mRecyclerView.setAdapter(adapter);
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

    // thi method for test recyclerView  . . .
    private ArrayList<OpinionModel> fillRecyclerView(){
        ArrayList<OpinionModel> opinions= new ArrayList<>();
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = formatter.format(todayDate);

        for (int i =0 ;i<11;i++){
            OpinionModel opinionModel = new OpinionModel("Hi I Am Mohamed And I Love You And I Want To Attend With You "
            ,3,R.drawable.test_img,todayString);
            opinions.add(opinionModel);
        }
        return opinions;
    }
}








