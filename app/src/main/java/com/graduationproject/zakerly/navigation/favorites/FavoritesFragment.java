package com.graduationproject.zakerly.navigation.favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.base.BaseFragment;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.databinding.FragmentFavoritesBinding;

import java.util.ArrayList;

public class FavoritesFragment extends BaseFragment {

    private FragmentFavoritesBinding binding;
    private FavoritesViewModel viewModel;
    TextView mFavorite ;
    RecyclerView mRecyclerViewFavorite;

    FavoriteAdapter adapter ;
    ArrayList<Instructor> list;
   FireBaseAuthenticationClient auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentFavoritesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= new FavoritesViewModelFacory(new FavoriteRepoistory()).create(FavoritesViewModel.class);
        initViews();
        auth=FireBaseAuthenticationClient.getInstance();

    }

    private void initViews(){
        mFavorite = binding.textFavorites;
        mRecyclerViewFavorite = binding.recyclerviewFavorites;
    }

    private void iniListeners(){
        adapter.onFavoriteClickListener= postion -> {

        };
    }

}