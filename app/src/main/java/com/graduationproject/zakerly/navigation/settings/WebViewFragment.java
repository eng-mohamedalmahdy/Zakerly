package com.graduationproject.zakerly.navigation.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;


public class WebViewFragment extends Fragment {

    private WebViewFragmentArgs args;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setNavigationVisibility(false);
        args = WebViewFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webview = view.findViewById(R.id.web_view);
        WebSettings set = webview.getSettings();
        set.setBuiltInZoomControls(true);
        webview.loadUrl(args.getUrl());
    }
}