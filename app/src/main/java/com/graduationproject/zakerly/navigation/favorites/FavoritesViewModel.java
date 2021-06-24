package com.graduationproject.zakerly.navigation.favorites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.graduationproject.zakerly.adapters.TeacherCardAdapter;


public class FavoritesViewModel extends ViewModel {

    private FavoriteRepository repository;
    MutableLiveData<String> message;

    public FavoritesViewModel(FavoriteRepository repository) {
        this.repository = repository;
    }


    public void setUpFavoritesData(TeacherCardAdapter adapter) {
        repository.getFavoritesData(adapter);
    }
}
