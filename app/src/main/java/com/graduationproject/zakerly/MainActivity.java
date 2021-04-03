package com.graduationproject.zakerly;


import android.os.Bundle;
import android.view.View;

import com.graduationproject.zakerly.core.base.BaseActivity;
import com.graduationproject.zakerly.databinding.ActivityMainBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.graduationproject.zakerly.core.constants.NavigationConstants;

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
        navigationBar.setItemSelected(R.id.home, true);
    }


    private void initViews() {
        navigationBar = binding.bottomNavigation;
    }

    private void initListeners() {
        navigationBar.setOnItemSelectedListener(i -> {
            //TODO Add the navigation logic here
        });
    }

    public void setNavigationVisibility(boolean visible) {
        navigationBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setSelectedPage(int page) {
        switch (page) {
            case NavigationConstants.HOME_PAGE:
                navigationBar.setItemSelected(R.id.home, true);
                break;
            case NavigationConstants.SEARCH_PAGE:
                navigationBar.setItemSelected(R.id.search, true);
                break;
            case NavigationConstants.FAVORITE_PAGE:
                navigationBar.setItemSelected(R.id.favorite, true);
                break;
            case NavigationConstants.ACCOUNT_PAGE:
                navigationBar.setItemSelected(R.id.account, true);
                break;
            case NavigationConstants.NOTIFICATION_PAGE:
                navigationBar.setItemSelected(R.id.notification, true);
        }
    }
}