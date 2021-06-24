package com.graduationproject.zakerly.navigation.homestudent;

import com.google.firebase.database.Query;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

public class HomeStudentPageRepository {

    public static Query getAllInstructors() {

        return FirebaseDataBaseClient.getInstance().getAllInstructors();

    }

}
