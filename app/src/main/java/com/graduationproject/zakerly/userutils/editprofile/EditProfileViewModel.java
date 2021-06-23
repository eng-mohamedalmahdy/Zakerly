package com.graduationproject.zakerly.userutils.editprofile;

import android.content.res.Resources;
import android.os.Build;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

import java.util.ArrayList;
import java.util.Locale;

public class EditProfileViewModel extends ViewModel {

    private final MutableLiveData<Student> studentMutableLiveData;
    private final MutableLiveData<Instructor> instructorMutableLiveData;
    private final MutableLiveData<User> userMutableLiveData;
    private final MutableLiveData<ArrayList<String>> specialisationNamesMutableLifeData;
    private final MutableLiveData<ArrayList<Specialisation>> specialisationMutableLifeData;

    public EditProfileViewModel() {
        studentMutableLiveData = new MutableLiveData<>();
        instructorMutableLiveData = new MutableLiveData<>();
        userMutableLiveData = new MutableLiveData<>();
        specialisationNamesMutableLifeData = new MutableLiveData<>(new ArrayList<>());
        specialisationMutableLifeData = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<Student> getStudentMutableLiveData() {
        return studentMutableLiveData;
    }

    public MutableLiveData<Instructor> getInstructorMutableLiveData() {
        return instructorMutableLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<ArrayList<String>> getSpecialisationNamesMutableLifeData() {
        return specialisationNamesMutableLifeData;
    }

    public MutableLiveData<ArrayList<Specialisation>> getSpecialisationMutableLifeData() {
        return specialisationMutableLifeData;
    }

    public Task<DataSnapshot> getSpecialisationsList() {
        return FirebaseDataBaseClient.getInstance().getSpecialisations();
    }

    public void setUpSpecialisationsList(Resources resources) {
        getSpecialisationsList().addOnSuccessListener(dataSnapshot -> {
            Locale l;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                l = resources.getConfiguration().getLocales().get(0);
            } else {
                //noinspection deprecation
                l = resources.getConfiguration().locale;
            }
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Specialisation specialisation = child.getValue(Specialisation.class);
                specialisationMutableLifeData.getValue().add(specialisation);
                if (l.getLanguage().equalsIgnoreCase("ar"))
                    specialisationNamesMutableLifeData.getValue().add(specialisation.getAr());
                else {
                    specialisationNamesMutableLifeData.getValue().add(specialisation.getEn());
                }
            }
            specialisationNamesMutableLifeData.setValue(specialisationNamesMutableLifeData.getValue());
            specialisationMutableLifeData.setValue(specialisationMutableLifeData.getValue());
        });
    }

}
