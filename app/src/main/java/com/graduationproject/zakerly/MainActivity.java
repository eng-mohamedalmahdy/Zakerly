package com.graduationproject.zakerly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.engmahdy.zakerly.R;
import com.engmahdy.zakerly.databinding.ActivityMainBinding;
import com.graduationproject.zakerly.core.base.BaseActivity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ChipNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
    }


    private void initViews() {
        navigationBar = binding.bottomNavigation;
    }

    private void initListeners() {
        navigationBar.setOnItemSelectedListener(i -> {
            //TODO Add the navigation logic here
        });
    }

    public void setNavigationVisibility(boolean visible){
        navigationBar.setVisibility(visible? View.VISIBLE:View.GONE);
    }
}