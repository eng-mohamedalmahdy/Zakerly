package com.graduationproject.zakerly.authentication.signup;

import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.authentication.signup.pages.InstructorSignUpFragment;
import com.graduationproject.zakerly.authentication.signup.pages.StudentSignUpFragment;
import com.graduationproject.zakerly.authentication.signup.pages.UserTypePagerAdapter;
import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.databinding.FragmentSignUpBinding;

public class SignUpFragment extends BaseFragment {

    private SignUpViewModel mViewModel;
    private FragmentSignUpBinding binding;

    private ViewPager2 userTypePager;
    private Button studentTab;
    private Button instructorTab;
    private Button signUp;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new SignUpViewModelFactory(new SignUpRepository()).create(SignUpViewModel.class);

        initViews();

        initViewPager();

        initListeners();
    }


    private void initViews() {
        userTypePager = binding.viewPager;
        studentTab = binding.studentButton;
        instructorTab = binding.instructorButton;
        signUp = binding.signUp;
    }

    private void initViewPager() {
        userTypePager.setAdapter(new UserTypePagerAdapter(this));
        userTypePager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateLiveData(position);

            }
        });
    }

    private void initListeners() {
        instructorTab.setOnClickListener((v) -> userTypePager.setCurrentItem(0, true));
        studentTab.setOnClickListener((v) -> userTypePager.setCurrentItem(1, true));
        signUp.setOnClickListener((v) -> {
            String type = mViewModel.getCurrentType().getValue();
            if (type.equals(UserTypes.TYPE_INSTRUCTOR)) {

                InstructorSignUpFragment instructorSignUpFragment = (InstructorSignUpFragment) ((UserTypePagerAdapter) (userTypePager.getAdapter()))
                        .getFragmentAt(0);
                boolean valid = instructorSignUpFragment.validate();
                if (valid) {
                    Instructor instructor = instructorSignUpFragment.getInstructor();
                    String password = instructorSignUpFragment.getPassword();
                    mViewModel.signUp(instructor, password, getContext());
                }
            } else {
                StudentSignUpFragment studentSignUpFragment = (StudentSignUpFragment) ((UserTypePagerAdapter) userTypePager.getAdapter())
                        .getFragmentAt(1);
                boolean valid = studentSignUpFragment.validate();
                if (valid) {
                    Student student = studentSignUpFragment.getStudent();
                    String password = studentSignUpFragment.getPassword();
                    mViewModel.signUp(student, password, getContext());
                }
            }
        });
    }

    private void updateLiveData(int position) {

        int lightGrey = new ContextWrapper(getContext()).getColor(R.color.lightGrey);
        int blue = new ContextWrapper(getContext()).getColor(R.color.blue);
        int[] attrs = new int[]{android.R.attr.colorBackground};
        TypedArray a = getContext().getTheme().obtainStyledAttributes(R.style.Widget_AppCompat_Light_ActionBar, attrs);
        int white = a.getColor(0, Color.RED);

        switch (position) {

            case 0:
                mViewModel.getCurrentType().setValue(UserTypes.TYPE_INSTRUCTOR);
                instructorTab.setBackgroundColor(blue);
                instructorTab.setTextColor(lightGrey);
                studentTab.setBackgroundColor(white);
                studentTab.setTextColor(lightGrey);
                break;
            case 1:
                mViewModel.getCurrentType().setValue(UserTypes.TYPE_STUDENT);
                instructorTab.setBackgroundColor(white);
                instructorTab.setTextColor(lightGrey);
                studentTab.setBackgroundColor(blue);
                studentTab.setTextColor(lightGrey);
                break;
        }
    }

}