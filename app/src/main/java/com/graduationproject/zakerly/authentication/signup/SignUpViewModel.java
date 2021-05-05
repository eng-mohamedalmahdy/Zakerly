package com.graduationproject.zakerly.authentication.signup;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;
import com.graduationproject.zakerly.core.models.Student;

import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class SignUpViewModel extends ViewModel {
    private SignUpRepository repository;
    private MutableLiveData<String> currentType;
    private MutableLiveData<Integer> instructorColor;
    private MutableLiveData<Integer> instructorTextColor;
    private MutableLiveData<Integer> studentColor;
    private MutableLiveData<Integer> studentTextColor;


    public SignUpViewModel(SignUpRepository repository) {
        this.repository = repository;
        currentType = new MutableLiveData(UserTypes.TYPE_INSTRUCTOR);
        instructorColor = new MutableLiveData((R.color.blue));
        instructorTextColor = new MutableLiveData((R.color.white));
        studentColor = new MutableLiveData((R.color.white));
        studentTextColor = new MutableLiveData((R.color.lightGrey));
    }

    public void signUp(Student student, String password, Fragment fragment) {
        repository.signUp(student, password, fragment);
    }

    public void signUp(Instructor instructor, String password, Fragment fragment) {
        repository.signUp(instructor, password, fragment);
    }

    public Task<DataSnapshot> getSpecialisations() {
        return repository.getSpecialisationsList();
    }


    public MutableLiveData<String> getCurrentType() {
        return currentType;
    }

    public MutableLiveData<Integer> getInstructorColor() {
        return instructorColor;
    }

    public MutableLiveData<Integer> getInstructorTextColor() {
        return instructorTextColor;
    }

    public MutableLiveData<Integer> getStudentColor() {
        return studentColor;
    }

    public MutableLiveData<Integer> getStudentTextColor() {
        return studentTextColor;
    }


    public void setupSpecialisationsAdapter(Spinner specialisationsSpinner, ArrayList<Specialisation> specialisations, ArrayList<String> specialisationsNames, Context c) {
        getSpecialisations().addOnSuccessListener(dataSnapshot -> {
            Locale l;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                l = c.getResources().getConfiguration().getLocales().get(0);
            } else {
                //noinspection deprecation
                l = c.getResources().getConfiguration().locale;
            }

            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Specialisation specialisation = child.getValue(Specialisation.class);
                specialisations.add(specialisation);
                if (l.getLanguage().equalsIgnoreCase("ar"))
                    specialisationsNames.add(specialisation.getAr());
                else {
                    specialisationsNames.add(specialisation.getEn());
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, specialisationsNames);
            specialisationsSpinner.setAdapter(adapter);
        }).addOnFailureListener(e -> Toasty.error(c, e.toString()).show());
    }
}