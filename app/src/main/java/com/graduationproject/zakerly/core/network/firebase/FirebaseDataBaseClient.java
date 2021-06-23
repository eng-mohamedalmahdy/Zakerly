package com.graduationproject.zakerly.core.network.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.navigation.favorites.FavoriteAdapter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.realm.RealmList;


public class FirebaseDataBaseClient {

    private static final String TAG = "FIREBASE_TAG";
    private static FirebaseDataBaseClient instance;
    private static DatabaseReference usersReference;
    private static DatabaseReference specialisationsReference;
    private static DatabaseReference favoritesReference;


    private FirebaseDataBaseClient() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
        specialisationsReference = database.getReference("specialisations");
        favoritesReference = database.getReference("favorites");
    }

    public static FirebaseDataBaseClient getInstance() {
        if (instance == null) instance = new FirebaseDataBaseClient();
        return instance;
    }

    public Task<Void> addStudent(Student student) {
        return usersReference.child(student.getUser().getUID()).setValue(student);
    }

    public Task<Void> addInstructor(Instructor instructor) {
        return usersReference.child(instructor.getUser().getUID()).setValue(instructor);
    }

    public Task<DataSnapshot> getSpecialisations() {
        return specialisationsReference.get();
    }

    public Query getUser(String email) {
        return usersReference.orderByChild("user/email").equalTo(email);
    }

    public Task<Void> setCurrentUserProfilePicture(String imgUrl) {
        return usersReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid()).child("user").child("profileImg").setValue(imgUrl);
    }

    public void doWithUserObject(String email,
                                 Function<Student, Boolean> studentAction,
                                 Function<Instructor, Boolean> instructorAction, Function<String, Boolean> errorAction) {

        FirebaseDataBaseClient.getInstance().getUser(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("LOOPING", "onDataChange: " + snapshot.getChildrenCount());

                String type = "";
                for (DataSnapshot child : snapshot.getChildren()) {
                    type = child.child("user/type").getValue(String.class);
                    Log.d("LOOPING", "onDataChange: " + type);
                    if (UserTypes.TYPE_INSTRUCTOR.equals(type)) {
                        Instructor instructor = child.getValue(Instructor.class);
                        instructorAction.apply(instructor);
                    } else if (UserTypes.TYPE_STUDENT.equals(type)) {
                        Student student = child.getValue(Student.class);
                        studentAction.apply(student);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorAction.apply(error.getMessage());
            }
        });
    }

    private Task<DataSnapshot> getUsersFavoritesUID(String uid) {
        return favoritesReference.child(uid).get();
    }

    private void getUsers(Task<DataSnapshot> uids, FavoriteAdapter adapter) {
        ArrayList<Instructor> favorites = new ArrayList<>();

        uids.addOnSuccessListener(dataSnapshot ->
        {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                usersReference.child(Objects.requireNonNull(child.getKey())).get().addOnSuccessListener(dataSnapshot1 -> {
                    Instructor i = dataSnapshot1.getValue(Instructor.class);
                    Log.d(TAG, "onSuccess: " + i);
                    favorites.add(i);
                }).addOnCompleteListener(task -> {
                    Log.d(TAG, "onSuccess: " + favorites);
                    adapter.setList(favorites);
                });
            }
        });


    }

    public void setUpFavoritesWithAdapter(String uid, FavoriteAdapter adapter) {
        getUsers(getUsersFavoritesUID(uid), adapter);
    }

    public Task<DataSnapshot> getProfileImageUrl() {
        return usersReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid()).child("user").child("profileImg").get();
    }

    public Task<DataSnapshot> getCurrentUser() {
        return usersReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid()).get();
    }
}
