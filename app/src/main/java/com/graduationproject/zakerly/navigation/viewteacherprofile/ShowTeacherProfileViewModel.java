package com.graduationproject.zakerly.navigation.viewteacherprofile;

import androidx.lifecycle.ViewModel;

public class ShowTeacherProfileViewModel extends ViewModel {

    private ShowTeacherProfileRepository repository;

    public ShowTeacherProfileViewModel(ShowTeacherProfileRepository repository) {
        this.repository = repository;
    }
}
