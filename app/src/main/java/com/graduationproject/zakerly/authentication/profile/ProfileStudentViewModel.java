package com.graduationproject.zakerly.authentication.profile;

import androidx.lifecycle.ViewModel;

public class ProfileStudentViewModel extends ViewModel {

    private ProfileStudentRepository repository;


    public ProfileStudentViewModel(ProfileStudentRepository repository) {
        this.repository = repository;
    }
}
