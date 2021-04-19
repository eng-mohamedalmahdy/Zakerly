package com.graduationproject.zakerly.authentication.forgetpassword;


import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.graduationproject.zakerly.R;

import es.dmoral.toasty.Toasty;

public class ForgetPasswordViewModel extends ViewModel {


    public ForgetPasswordRepository forgetPasswordRepository;

    public  MutableLiveData<String> email;

    public ForgetPasswordViewModel(ForgetPasswordRepository forgetPasswordRepository) {
        this.forgetPasswordRepository = forgetPasswordRepository;
        email = new MutableLiveData<>();
    }

    public void forgetPassword(Context context, MutableLiveData<String> emailLiveData) {
            String email = emailLiveData.getValue();
        if (email != null && !email.isEmpty()) {
            forgetPasswordRepository.forgetPassword(email);
        } else {
            Toasty.error(context, R.string.email_field_cannot_be_empty).show();
        }
    }
}
