package com.graduationproject.zakerly.authentication.signup.pages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class UserTypePagerAdapter extends FragmentStateAdapter {
    private static final int PAGES_COUNT = 2;

    public UserTypePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new InstructorSignUpFragment();
            case 1:
            default:
                return new StudentSignUpFragment();
        }
    }


    @Override
    public int getItemCount() {
        return PAGES_COUNT;
    }
}
