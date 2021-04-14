package com.graduationproject.zakerly.authentication.signIn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.authentication.signup.SignUpFragment;
import com.graduationproject.zakerly.authentication.signup.SignUpRepository;
import com.graduationproject.zakerly.authentication.signup.SignUpViewModel;
import com.graduationproject.zakerly.authentication.signup.SignUpViewModelFactory;
import com.graduationproject.zakerly.authentication.signup.pages.InstructorSignUpFragment;
import com.graduationproject.zakerly.authentication.signup.pages.StudentSignUpFragment;
import com.graduationproject.zakerly.authentication.signup.pages.UserTypePagerAdapter;
import com.graduationproject.zakerly.core.base.BaseFragment;

import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.network.GoogleClient;
import com.graduationproject.zakerly.databinding.FragmentSignUpBinding;
import com.graduationproject.zakerly.databinding.FragmentSigninBinding;
public class SignInFragment extends BaseFragment {

    private FragmentSigninBinding binding;
    private SignInViewModel mViewModel ;

    TextInputLayout signinWithGoogle;
    TextInputLayout signinWithFacebook;
    TextInputEditText emailEditText ;
    EditText passwordEditText ;
    TextView forgetPasswordText ;
    Button signIn ;
    Button signUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new SignInViewModelFactory(new SignInRepository()).create(SignInViewModel.class);
        initViews();
        initListener();
    }

    private void initListener() {


    }


}