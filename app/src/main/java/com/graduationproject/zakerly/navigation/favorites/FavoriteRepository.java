package com.graduationproject.zakerly.navigation.favorites;


import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.adapters.TeacherCardAdapter;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;


public class FavoriteRepository {

    public void getFavoritesData(TeacherCardAdapter adapter) {
        FirebaseUser firebaseUser = FireBaseAuthenticationClient.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            FirebaseDataBaseClient.getInstance().setUpFavoritesWithAdapter(uid, adapter);

        }
    }
}
