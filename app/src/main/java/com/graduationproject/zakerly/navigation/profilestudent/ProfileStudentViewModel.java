package com.graduationproject.zakerly.navigation.profilestudent;

import androidx.lifecycle.ViewModel;

public class ProfileStudentViewModel extends ViewModel {

    private ProfileStudentRepository repository;


    public ProfileStudentViewModel(ProfileStudentRepository repository) {
        this.repository = repository;
    }
}
