package com.graduationproject.zakerly.navigation.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.databinding.FragmentHomeChatBinding;

public class HomeChatFragment extends Fragment {
    FragmentHomeChatBinding binding;
    ImageView mImageBack;
    EditText mEtSearch;
    RecyclerView mRecyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentHomeChatBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        mRecyclerView = binding.homeChatRecyclerView;
        mImageBack = binding.homeChatBack;
        mEtSearch = binding.homeChatSearch;
    }
}