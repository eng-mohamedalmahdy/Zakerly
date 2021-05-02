package com.graduationproject.zakerly.navigation.favorites;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoriteRepoistory {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    String INSTRUCTOR_PATH="users";

    private CollectionReference getUserCollection(){
        return database.collection(INSTRUCTOR_PATH);
    }

    public void getUserData(String userID , OnCompleteListener<DocumentSnapshot> onCompleteListener){
            getUserCollection()
                    .document(userID)
                    .get()
                    .addOnCompleteListener(onCompleteListener);

    }
}
