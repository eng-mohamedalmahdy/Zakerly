package com.graduationproject.zakerly.navigation.homestudent;

import androidx.lifecycle.ViewModel;

import com.google.firebase.database.Query;

public class HomeStudentPageViewModel extends ViewModel {

    public Query getAllInstructors() {
        return HomeStudentPageRepository.getAllInstructors();
    }


}
