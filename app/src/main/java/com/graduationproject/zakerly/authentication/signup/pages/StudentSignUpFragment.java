package com.graduationproject.zakerly.authentication.signup.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.base.BaseFragment;

public class StudentSignUpFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.view_student_sign_up,container,false);
    }
}
