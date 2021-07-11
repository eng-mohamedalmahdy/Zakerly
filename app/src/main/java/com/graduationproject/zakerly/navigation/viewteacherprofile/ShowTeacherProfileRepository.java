package com.graduationproject.zakerly.navigation.viewteacherprofile;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

public class ShowTeacherProfileRepository {

    public static Task<DataSnapshot> getOpinions(String uid) {
        return FirebaseDataBaseClient.getInstance().getOpinions(uid);
    }
}
