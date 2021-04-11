package com.graduationproject.zakerly.authentication.signup.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.databinding.ViewInstructorSignUpBinding;

public class InstructorSignUpFragment extends BaseFragment {

    private ViewInstructorSignUpBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ViewInstructorSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public Instructor getInstructor() {

        return new Instructor(new User("",
                UserTypes.TYPE_INSTRUCTOR,
                binding.firstNameTextField.getEditText().getText().toString(),
                binding.lastNameTextField.getEditText().getText().toString(),
                binding.emailTextField.getEditText().getText().toString(), ""),
                binding.specialisationTextField.getEditText().getText().toString(),
                Double.parseDouble(binding.priceTextField.getEditText().getText().toString()));

    }

    public String getPassword() {
        return binding.passwordTextField.getEditText().getText().toString();
    }
}
