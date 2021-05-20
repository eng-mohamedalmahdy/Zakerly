package com.graduationproject.zakerly.navigation.favorites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.graduationproject.zakerly.core.models.Instructor;

import java.util.ArrayList;


public class FavoritesViewModel extends ViewModel {

    private FavoriteRepository repository;
    MutableLiveData<String> message;

    public FavoritesViewModel(FavoriteRepository repository) {
        this.repository = repository;
    }


    public void setUpFavoritesData(FavoriteAdapter adapter) {
        repository.getFavoritesData(adapter);
    }
}
