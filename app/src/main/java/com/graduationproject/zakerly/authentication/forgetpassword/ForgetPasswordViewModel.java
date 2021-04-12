package com.graduationproject.zakerly.authentication.forgetpassword;

import androidx.lifecycle.ViewModel;

public class ForgetPasswordViewModel extends ViewModel {


    // create object from forgetPasswordRepo and take it in constructor . . .
    private ForgetPasswordRepository forgetPasswordRepository ;
    public ForgetPasswordViewModel(ForgetPasswordRepository forgetPasswordRepository) {
        this.forgetPasswordRepository = forgetPasswordRepository;
    }
}
