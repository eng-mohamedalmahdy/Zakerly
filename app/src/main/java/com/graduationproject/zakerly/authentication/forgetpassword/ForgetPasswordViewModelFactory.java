package com.graduationproject.zakerly.authentication.forgetpassword;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class ForgetPasswordViewModelFactory implements ViewModelProvider.Factory {



    // create forgetPasswordViewModelFactory
    private ForgetPasswordRepository forgetPasswordRepository ;
    public ForgetPasswordViewModelFactory(ForgetPasswordRepository forgetPasswordRepository) {
        this.forgetPasswordRepository = forgetPasswordRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)) {
            return (T) new ForgetPasswordViewModel(forgetPasswordRepository) ;
        }
        throw new IllegalArgumentException("Unable to construct viewmodel") ;
    }

}
