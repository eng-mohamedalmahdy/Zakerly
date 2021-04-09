package com.graduationproject.zakerly.authentication.signIn.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.authentication.signIn.viewmodel.LoginViewModel;
import com.graduationproject.zakerly.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain2Binding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main2);
        activityMainBinding.setViewModel(new LoginViewModel(MainActivity2.this));
        activityMainBinding.executePendingBindings();
    }
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}