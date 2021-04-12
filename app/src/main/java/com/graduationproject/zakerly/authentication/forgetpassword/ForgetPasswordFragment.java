package com.graduationproject.zakerly.authentication.forgetpassword;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.authentication.signIn.LoginViewModel;
import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.databinding.FragmentForgetPasswordBinding;

public class ForgetPasswordFragment extends BaseFragment {

private FragmentForgetPasswordBinding binding ;


    @Override
    public View onCreateView(LayoutInflater inflater
                              , ViewGroup container
                              , Bundle savedInstanceState) {
       binding = FragmentForgetPasswordBinding.inflate(inflater,container,false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(new ForgetPasswordViewModel(new ForgetPasswordRepository(getContext())));
        binding.executePendingBindings();
    }
}