package com.graduationproject.zakerly.navigation.profileTeacher;

import androidx.lifecycle.ViewModel;

public class ProfileTeacherViewModel  extends ViewModel {

    private ProfileTeacherRepository repository;

    public ProfileTeacherViewModel(ProfileTeacherRepository repository) {
        this.repository = repository;
    }
}
