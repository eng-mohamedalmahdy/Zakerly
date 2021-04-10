package com.graduationproject.zakerly.intro.splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.databinding.FragmentSplashBinding;


public class SplashFragment extends Fragment {

    private static final int SPLASH_TIME = 2000;
    private SplashRepository splashRepository;
    private SplashViewModel splashViewModel;
    private FragmentSplashBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        splashRepository = new SplashRepository(getContext());
        splashViewModel = new SplashViewModelFactory(splashRepository,SplashFragment.this).create(SplashViewModel.class);
        binding.splashContainer.animate().setDuration(SPLASH_TIME).alpha(1f).withEndAction(() -> splashViewModel.navigateToNextDestination()).start();

    }

    @Override
    public void onPause() {
        super.onPause();
        splashViewModel.onCleared();
    }
}