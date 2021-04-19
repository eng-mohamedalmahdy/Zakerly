package com.graduationproject.zakerly.authentication.forgetpassword;

import android.content.Context;

import androidx.annotation.NonNull;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;

import es.dmoral.toasty.Toasty;

public class ForgetPasswordRepository {

    private final Context context;
    private final FireBaseAuthenticationClient authenticationClient;

    public ForgetPasswordRepository(Context context) {
        this.context = context;
        authenticationClient = FireBaseAuthenticationClient.getInstance();
    }

    public void forgetPassword(String email) {

        authenticationClient.forgetPassword(email).addOnCompleteListener(task ->  {
            if (task.isSuccessful()) {
                Toasty.success(context, R.string.reset_password_mail_sent).show();
            }
            else {
                Toasty.error(context, R.string.failed_to_send_reset_mail).show();
            }
        });
    }
}
