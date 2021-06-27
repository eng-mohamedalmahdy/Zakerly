package com.graduationproject.zakerly.navigation.viewteacherprofile;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class ShowTeacherProfileViewModel extends ViewModel {


    public ShowTeacherProfileViewModel() {
    }
    public Task<DataSnapshot> getOpinions(){
        return  ShowTeacherProfileRepository.getOpinions();
    }
}
