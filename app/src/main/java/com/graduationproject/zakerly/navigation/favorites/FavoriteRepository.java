package com.graduationproject.zakerly.navigation.favorites;


import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;


public class FavoriteRepository {

    public void getFavoritesData(FavoriteAdapter adapter) {
        FirebaseUser firebaseUser = FireBaseAuthenticationClient.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            FirebaseDataBaseClient.getInstance().setUpFavoritesWithAdapter(uid, adapter);

        }
    }
}
