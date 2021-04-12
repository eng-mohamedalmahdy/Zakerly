package com.graduationproject.zakerly.authentication.signup.pages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.graduationproject.zakerly.core.base.BaseFragment;

public class UserTypePagerAdapter extends FragmentStateAdapter {
    private static final int PAGES_COUNT = 2;
    private BaseFragment instructorSignUpFragment;
    private BaseFragment studentSignUpFragment;

    public UserTypePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                instructorSignUpFragment = new InstructorSignUpFragment();
                return instructorSignUpFragment;
            case 1:
                studentSignUpFragment = new StudentSignUpFragment();
                return studentSignUpFragment;
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return PAGES_COUNT;
    }



    public BaseFragment getFragmentAt(int position) {

        switch (position) {
            case 0:
                return instructorSignUpFragment;
            case 1:
            default:
                return studentSignUpFragment;
        }
    }
}
