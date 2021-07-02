package com.graduationproject.zakerly.navigation.instructorAccountPage;

import androidx.lifecycle.ViewModel;

public class InstructorPageViewModel  extends ViewModel {
    private InstructorPageRepository repository;

    public InstructorPageViewModel(InstructorPageRepository repository) {
        this.repository = repository;
    }
}
