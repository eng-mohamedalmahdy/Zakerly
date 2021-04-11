package com.graduationproject.zakerly.authentication.signup.pages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class UserTypePagerAdapter extends FragmentStateAdapter {
    private static final int PAGES_COUNT = 2;
    private Fragment instructorSignUpFragment;
    private Fragment studentSignUpFragment;

    public UserTypePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
        instructorSignUpFragment = new InstructorSignUpFragment();
        studentSignUpFragment = new StudentSignUpFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return instructorSignUpFragment;
            case 1:
            default:
                return studentSignUpFragment;
        }
    }


    @Override
    public int getItemCount() {
        return PAGES_COUNT;
    }

    public Fragment getFragmentAt(int position) {

        switch (position) {
            case 0:
                return instructorSignUpFragment;
            case 1:
            default:
                return studentSignUpFragment;
        }
    }
}
