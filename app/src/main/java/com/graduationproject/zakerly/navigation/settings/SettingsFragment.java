package com.graduationproject.zakerly.navigation.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.core.constants.BottomNavigationConstants;
import com.graduationproject.zakerly.core.constants.SiteURLs;
import com.graduationproject.zakerly.databinding.FragmentSettingsBinding;
import com.jakewharton.processphoenix.ProcessPhoenix;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;
    private CompositeDisposable disposables;
    private boolean firstTime = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setSelectedPage(BottomNavigationConstants.SETTINGS_PAGE);
        ((MainActivity) getActivity()).setNavigationVisibility(true);
        binding = FragmentSettingsBinding.inflate(inflater);
        viewModel = new SettingsViewModelFactory(new SettingsRepository(getContext())).create(SettingsViewModel.class);
        disposables = new CompositeDisposable();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListeners();
        initViews();


    }

    private void initViews() {
        Disposable d = viewModel.getNightModeEnable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> binding.darkMode.setChecked(aBoolean));
        disposables.add(d);

        LocalizationActivity activity = (LocalizationActivity) requireActivity();
        String language = activity.getCurrentLanguage().getLanguage();
        binding.languageSpinner.setSelection(language.equalsIgnoreCase("ar") ? 0 : 1);
    }

    private void initListeners() {
        binding.aboutZakerly.setOnClickListener(v -> navigateToUrl(SiteURLs.ABOUT_US));
        binding.faq.setOnClickListener(v -> navigateToUrl(SiteURLs.FAQ));
        binding.privacyPolicy.setOnClickListener(v -> navigateToUrl(SiteURLs.PRIVACY_POLICY));
        binding.contactUs.setOnClickListener(v -> navigateToUrl(SiteURLs.CONTACT_US));
        binding.signOut.setOnClickListener(v -> {
            viewModel.signOut();
            ((MainActivity) getActivity()).setNavigationVisibility(false);
            NavHostFragment.findNavController(this).navigate(SettingsFragmentDirections.actionSettingsFragmentToAuthNavigation());
            ProcessPhoenix.triggerRebirth(getContext());
        });
        binding.darkMode.setOnClickListener(v -> {
            viewModel.setNightModeEnabled(binding.darkMode.isChecked());
        });

        binding.languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!firstTime) {
                    LocalizationActivity activity = (LocalizationActivity) requireActivity();
                    if (position == 0) activity.setLanguage("ar");
                    else if (position == 1) activity.setLanguage("en");
                }
                firstTime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void navigateToUrl(String url) {
        NavHostFragment.findNavController(this).navigate(SettingsFragmentDirections.actionSettingsFragmentToWebViewFragment(url));
    }

    @Override
    public void onPause() {
        super.onPause();
        disposables.dispose();
    }
}