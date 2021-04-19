package com.graduationproject.zakerly.authentication.forgetpassword;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.databinding.FragmentForgetPasswordBinding;

import org.jetbrains.annotations.NotNull;

public class ForgetPasswordFragment extends BaseFragment {

private FragmentForgetPasswordBinding binding ;
private ForgetPasswordViewModel viewModel;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater
                              , ViewGroup container
                              , Bundle savedInstanceState) {
       binding = FragmentForgetPasswordBinding.inflate(inflater,container,false);
        viewModel = new ForgetPasswordViewModel(new ForgetPasswordRepository(getContext()));
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.icBackForgetpassword.setOnClickListener(v -> getActivity().onBackPressed());
    }
}