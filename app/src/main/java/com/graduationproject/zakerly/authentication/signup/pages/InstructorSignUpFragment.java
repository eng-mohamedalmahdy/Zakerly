package com.graduationproject.zakerly.authentication.signup.pages;

import android.os.Bundle;
import android.util.Patterns;
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

    public boolean validate() {
        boolean valid = true;
        String fName = binding.firstNameTextField.getEditText().getText().toString();
        String lastName = binding.lastNameTextField.getEditText().getText().toString();
        String email = binding.emailTextField.getEditText().getText().toString();
        String password = binding.passwordTextField.getEditText().getText().toString();
        String pricePerHour = binding.priceTextField.getEditText().getText().toString();

        if (fName.isEmpty()){
            valid=false;
            binding.firstNameTextField.setErrorEnabled(true);
            binding.firstNameTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (lastName.isEmpty()){
            binding.lastNameTextField.setErrorEnabled(true);
            binding.lastNameTextField.setError(getText(R.string.this_field_cannot_be_empty));

        }
        if (email.isEmpty()){
            binding.emailTextField.setErrorEnabled(true);
            binding.emailTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valid=false;
            binding.emailTextField.setErrorEnabled(true);
            binding.emailTextField.setError(getText(R.string.invalid_email));
        }
        if (password.isEmpty()){
            binding.passwordTextField.setErrorEnabled(true);
            binding.passwordTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (password.length()<8){
            binding.passwordTextField.setErrorEnabled(true);
            binding.passwordTextField.setError(getText(R.string.password_must_be_at_least_8));
        }
        if (pricePerHour.isEmpty()){
            binding.priceTextField.setErrorEnabled(true);
            binding.priceTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }

        if (valid)clearErrors();
        return valid;
    }

    private void clearErrors() {
        binding.firstNameTextField.setErrorEnabled(false);
        binding.lastNameTextField.setErrorEnabled(false);
        binding.emailTextField.setErrorEnabled(false);
        binding.passwordTextField.setErrorEnabled(false);
        binding.specialisationTextField.setErrorEnabled(false);
        binding.priceTextField.setErrorEnabled(false);
    }
}