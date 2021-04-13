package com.graduationproject.zakerly.authentication.signup.pages;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.authentication.signup.SignUpRepository;
import com.graduationproject.zakerly.authentication.signup.SignUpViewModel;
import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Specialisation;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.databinding.ViewInstructorSignUpBinding;
import com.graduationproject.zakerly.databinding.ViewStudentSignUpBinding;

import java.util.ArrayList;

import io.realm.RealmList;

public class StudentSignUpFragment extends BaseFragment {

    private ViewStudentSignUpBinding binding;
    private Spinner specialisationsSpinner;
    private SignUpViewModel viewModel;
    private ChipGroup selectedSpecialisations;
    private ArrayList<Specialisation> specialisations;
    private ArrayList<String> specialisationsNames;
    private RealmList<Specialisation> selectedSpecialisationsList;
    private boolean firstTime = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ViewStudentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public String getPassword() {
        return binding.passwordTextField.getEditText().getText().toString();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new SignUpViewModel(new SignUpRepository());

        specialisationsSpinner = binding.specialisationsList;
        selectedSpecialisations = binding.selectedSpecialisations;
        selectedSpecialisationsList = new RealmList<>();
        specialisations = new ArrayList<>();
        specialisationsNames = new ArrayList<>();
        viewModel.setupSpecialisationsAdapter(specialisationsSpinner, specialisations, specialisationsNames, getContext());
        specialisationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!firstTime) {
                    Specialisation specialisation = specialisations.get(position);
                    String specialisationName = specialisationsNames.get(position);
                    if (!selectedSpecialisationsList.contains(specialisation)) {
                        addChip(specialisation, specialisationName);
                    }
                } else {
                    firstTime = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Student getStudent() {
        return new Student(new User("",
                UserTypes.TYPE_INSTRUCTOR,
                binding.firstNameTextField.getEditText().getText().toString(),
                binding.lastNameTextField.getEditText().getText().toString(),
                binding.emailTextField.getEditText().getText().toString(),
                selectedSpecialisationsList, ""));
    }

    public boolean validate() {
        clearErrors();
        boolean valid = true;
        String fName = binding.firstNameTextField.getEditText().getText().toString();
        String lastName = binding.lastNameTextField.getEditText().getText().toString();
        String email = binding.emailTextField.getEditText().getText().toString();
        String password = binding.passwordTextField.getEditText().getText().toString();

        if (fName.isEmpty()) {
            valid = false;
            binding.firstNameTextField.setErrorEnabled(true);
            binding.firstNameTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (lastName.isEmpty()) {
            valid = false;
            binding.lastNameTextField.setErrorEnabled(true);
            binding.lastNameTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (email.isEmpty()) {
            valid = false;
            binding.emailTextField.setErrorEnabled(true);
            binding.emailTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            binding.emailTextField.setErrorEnabled(true);
            binding.emailTextField.setError(getText(R.string.invalid_email));
        }
        if (password.isEmpty()) {
            valid = false;
            binding.passwordTextField.setErrorEnabled(true);
            binding.passwordTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (password.length() < 8) {
            valid = false;
            binding.passwordTextField.setErrorEnabled(true);
            binding.passwordTextField.setError(getText(R.string.password_must_be_at_least_8));
        }

        return valid;
    }

    private void clearErrors() {
        binding.firstNameTextField.setErrorEnabled(false);
        binding.lastNameTextField.setErrorEnabled(false);
        binding.emailTextField.setErrorEnabled(false);
        binding.passwordTextField.setErrorEnabled(false);
    }

    private void addChip(Specialisation specialisation, String specialisationName) {
        Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.view_chip_view, null, false);
        chip.setText(specialisationName);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.bottomMargin = 8;
        params.topMargin = 8;
        params.leftMargin = 8;
        params.rightMargin = 8;
        chip.setLayoutParams(params);
        selectedSpecialisations.addView(chip);
        selectedSpecialisationsList.add(specialisation);
        chip.setOnCloseIconClickListener(v -> {
            selectedSpecialisations.removeView(chip);
            selectedSpecialisationsList.remove(specialisation);
        });
    }

}
