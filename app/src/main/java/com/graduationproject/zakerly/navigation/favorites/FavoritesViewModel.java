package com.graduationproject.zakerly.navigation.favorites;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FavoritesViewModel extends ViewModel {

    private FavoriteRepoistory repoistory;
    MutableLiveData<String> message ;

    public FavoritesViewModel(FavoriteRepoistory repoistory) {
        this.repoistory = repoistory;
    }

    private void getUserData(String userID){
    repoistory.getUserData(userID, task -> {
        if (task.isSuccessful()){
               FavoriteDataClass dataClass =  task.getResult().toObject(FavoriteDataClass.class);
        }else {

        }
    });
    }
}
