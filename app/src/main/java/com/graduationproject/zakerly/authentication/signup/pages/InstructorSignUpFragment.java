package com.graduationproject.zakerly.authentication.signup.pages;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.authentication.signup.SignUpFragment;
import com.graduationproject.zakerly.authentication.signup.SignUpRepository;
import com.graduationproject.zakerly.authentication.signup.SignUpViewModel;
import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.core.constants.AuthTypes;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.databinding.ViewInstructorSignUpBinding;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import io.realm.RealmList;


public class InstructorSignUpFragment extends BaseFragment {

    private SignUpFragment parent;

    private ViewInstructorSignUpBinding binding;
    private Spinner specialisationsSpinner;
    private SignUpViewModel viewModel;
    private ChipGroup selectedSpecialisations;
    private ArrayList<Specialisation> specialisations;
    private ArrayList<String> specialisationsNames;
    private RealmList<Specialisation> selectedSpecialisationsList;
    private boolean firstTime = true;

    public InstructorSignUpFragment(SignUpFragment parent) {
        this.parent = parent;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ViewInstructorSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
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

        setUpViews();

    }

    private void setUpViews() {
        if (!parent.getArgs().getAuthType().equals(AuthTypes.AUTH_EMAIL)) {
            binding.emailTextField.setEnabled(false);
            binding.passwordTextField.setEnabled(false);

            binding.firstNameTextField.getEditText().setText(parent.getArgs().getFirstName());
            binding.lastNameTextField.getEditText().setText(parent.getArgs().getLastName());
            binding.emailTextField.getEditText().setText(parent.getArgs().getEmail());
        }
    }


    public Instructor getInstructor() {
        Instructor i = new Instructor(new User("",
                UserTypes.TYPE_INSTRUCTOR,
                binding.firstNameTextField.getEditText().getText().toString(),
                binding.lastNameTextField.getEditText().getText().toString(),
                binding.emailTextField.getEditText().getText().toString(),
                parent.getAuthType(), "",
                selectedSpecialisationsList),
                Double.parseDouble(binding.priceTextField.getEditText().getText().toString()));
        i.setTitle(binding.titleTextField.getEditText().getText().toString());
        return i;

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

        if (parent.getArgs().getAuthType().equals(AuthTypes.AUTH_EMAIL)) {
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
            if (!fName.matches("^[A-Za-z]+$")) {
                valid = false;
                binding.firstNameTextField.setErrorEnabled(true);
                binding.firstNameTextField.setError(getText(R.string.invalid_name));
            }
            if (!lastName.matches("^[A-Za-z]+$")) {
                valid = false;
                binding.lastNameTextField.setErrorEnabled(true);
                binding.lastNameTextField.setError(getText(R.string.invalid_name));
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
        }
        if (pricePerHour.isEmpty()) {
            valid = false;
            binding.priceTextField.setErrorEnabled(true);
            binding.priceTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (selectedSpecialisationsList.isEmpty()){
            valid = false;
            Toasty.error(getContext(),R.string.please_add_some_subjects).show();
        }

        if (valid) clearErrors();
        return valid;
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

    private void clearErrors() {
        binding.firstNameTextField.setErrorEnabled(false);
        binding.lastNameTextField.setErrorEnabled(false);
        binding.emailTextField.setErrorEnabled(false);
        binding.passwordTextField.setErrorEnabled(false);
        binding.priceTextField.setErrorEnabled(false);
    }


}
