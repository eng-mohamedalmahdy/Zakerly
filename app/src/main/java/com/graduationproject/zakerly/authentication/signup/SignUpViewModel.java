package com.graduationproject.zakerly.authentication.signup;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.UserTypes;

public class SignUpViewModel extends ViewModel {
    private SignUpRepository repository;
    private MutableLiveData<String> currentType;
    private MutableLiveData<Integer> instructorColor;
    private MutableLiveData<Integer> instructorTextColor;
    private MutableLiveData<Integer> studentColor;
    private MutableLiveData<Integer> studentTextColor;


    public SignUpViewModel(SignUpRepository repository, Context context) {
        this.repository = repository;
        currentType = new MutableLiveData(UserTypes.TYPE_INSTRUCTOR);
        instructorColor=new MutableLiveData((R.color.blue));
        instructorTextColor=new MutableLiveData((R.color.white));
        studentColor=new MutableLiveData((R.color.white));
        studentTextColor=new MutableLiveData((R.color.lightGrey));
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
}