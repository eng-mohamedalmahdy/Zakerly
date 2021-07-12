package com.graduationproject.zakerly.core.network.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.core.cache.Realm.RealmQueries;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.ConnectionModel;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Message;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.OpinionModel;
import com.graduationproject.zakerly.core.models.Schedule;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.adapters.TeacherCardAdapter;
import com.graduationproject.zakerly.core.models.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;


public class FirebaseDataBaseClient {

    private static final String TAG = "FIREBASE_TAG";
    private static FirebaseDataBaseClient instance;
    private static DatabaseReference usersReference;
    private static DatabaseReference specialisationsReference;
    private static DatabaseReference favoritesReference;
    private static DatabaseReference opinionsReference;
    private static DatabaseReference notificationsReference;
    private static DatabaseReference connectionsReference;
    private static DatabaseReference chatsReference;
    private static DatabaseReference schedulesReference;


    private FirebaseDataBaseClient() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersReference = database.getReference("users");
        specialisationsReference = database.getReference("specialisations");
        favoritesReference = database.getReference("favorites");
        opinionsReference = database.getReference("opinions");
        notificationsReference = database.getReference("notifications");
        connectionsReference = database.getReference("connections");
        chatsReference = database.getReference("chats");
        schedulesReference = database.getReference("schedules");
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

    public Task<Void> setToken(String token) {
        return usersReference
                .child(FireBaseAuthenticationClient
                        .getInstance()
                        .getCurrentUser()
                        .getUid())
                .child("user")
                .child("notificationToken").setValue(token);
    }

    public Task<DataSnapshot> getSpecialisations() {
        return specialisationsReference.get();
    }

    public Query getUser(String email) {
        return usersReference.orderByChild("user/email").equalTo(email);
    }

    public Query getAllInstructors() {
        return usersReference.orderByChild("user/type").equalTo(UserTypes.TYPE_INSTRUCTOR);
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

    private void getUsers(Task<DataSnapshot> uids, TeacherCardAdapter adapter) {
        ArrayList<Instructor> favorites = new ArrayList<>();

        uids.addOnSuccessListener(dataSnapshot ->
        {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                usersReference.child(Objects.requireNonNull(child.getKey())).get().addOnSuccessListener(dataSnapshot1 -> {
                    Boolean b = child.getValue(Boolean.class);
                    if (b) {
                        Instructor i = dataSnapshot1.getValue(Instructor.class);
                        Log.d(TAG, "onSuccess: " + i);
                        favorites.add(i);
                    }
                }).addOnCompleteListener(task -> {
                    Log.d(TAG, "onSuccess: " + favorites);
                    adapter.setInstructors(favorites);
                });
            }
        });


    }

    public void setUpFavoritesWithAdapter(String uid, TeacherCardAdapter adapter) {
        getUsers(getUsersFavoritesUID(uid), adapter);
    }

    public Task<DataSnapshot> getProfileImageUrl() {
        return usersReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid()).child("user").child("profileImg").get();
    }

    public Task<DataSnapshot> getCurrentUser() {
        return usersReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid()).get();
    }

    public Task<DataSnapshot> getOpinions(String uid) {
        return opinionsReference.child(uid).get();

    }

    public Task<Void> setFavorite(String studentUid, String teacherUid, boolean favoriteStatus) {
        return favoritesReference.child(studentUid).child(teacherUid).setValue(favoriteStatus);
    }

    public Task<DataSnapshot> getFavorites(String uid) {
        return favoritesReference.child(uid).get();
    }

    public Task<DataSnapshot> getUserByUid(String uid) {
        return usersReference.child(uid).get();
    }


    public void doWithUserObjectByUid(String uid,
                                      Function<Student, Boolean> studentAction,
                                      Function<Instructor, Boolean> instructorAction, Function<String, Boolean> errorAction) {

        FirebaseDataBaseClient.getInstance().getUserByUid(uid).addOnSuccessListener(snapshot -> {
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
        });
    }

    public Task<Void> addNotification(String uid, NotificationData data) {
        return notificationsReference.child(uid).child(data.getNotificationId()).setValue(data);
    }

    public Task<DataSnapshot> getConnectionData(String uid) {
        return connectionsReference
                .child(FireBaseAuthenticationClient
                        .getInstance()
                        .getCurrentUser()
                        .getUid())
                .child(uid).get();
    }

    public Task<DataSnapshot> getNotification(String userUid, String notificationId) {
        return notificationsReference.child(userUid).child(notificationId).get();
    }

    public Task<Void> setConnection(ConnectionModel c) {
        connectionsReference.child(c.getFromUid()).child(c.getToUid()).setValue(c);
        return connectionsReference.child(c.getToUid()).child(c.getFromUid()).setValue(c.swapped());
    }

    public Task<DataSnapshot> getAllNotifications() {
        return notificationsReference
                .child(FireBaseAuthenticationClient
                        .getInstance()
                        .getCurrentUser()
                        .getUid()).get();
    }

    public Task<DataSnapshot> getConnectionsForCurrentUser() {
        return connectionsReference
                .child(FireBaseAuthenticationClient
                        .getInstance()
                        .getCurrentUser()
                        .getUid()).get();
    }

    public String getRandomKey() {
        return notificationsReference.push().getKey();
    }

    public Task<Void> addFeedback(String uid, OpinionModel opinionModel) {
        String id = getRandomKey();
        return opinionsReference.child(uid).child(id).setValue(opinionModel);
    }

    public Task<DataSnapshot> getConnectionsUser(String uid) {
        return connectionsReference.child(uid).get();
    }

    public void removeConnections(String uid, String uid1) {
        connectionsReference.child(uid).child(uid1).removeValue();
        connectionsReference.child(uid1).child(uid).removeValue();
    }

    public Task<Void> setUser(String uid, Instructor instructor) {
        return usersReference.child(uid).setValue(instructor);
    }

    public Task<Void> setUser(String uid, Student student) {
        return usersReference.child(uid).setValue(student);
    }

    public Task<DataSnapshot> getLastMessageWithUser(String combinedUid) {
        return chatsReference.child(combinedUid).orderByChild("timeOfSendMsg").limitToFirst(1).get();
    }

    public String getCombinedUid(String uid) {

        FirebaseUser firebaseuser = FireBaseAuthenticationClient.getInstance().getCurrentUser();
        User localUser = null;
        if (firebaseuser != null) {
            localUser = new RealmQueries().getUser(firebaseuser.getUid());
        }

        String instructorUid = localUser.getType().equals(UserTypes.TYPE_INSTRUCTOR) ? localUser.getUID() : uid;
        String studentUid = localUser.getType().equals(UserTypes.TYPE_STUDENT) ? localUser.getUID() : uid;
        return instructorUid + "_" + studentUid;
    }

    public Task<DataSnapshot> getChatWithUser(String uid) {
        return chatsReference.child(getCombinedUid(uid)).get();
    }

    public Task<Void> sendMessage(Message message) {
        return chatsReference.child(getCombinedUid(message.getReceiverID())).child(message.getMessageId()).setValue(message);
    }

    public DatabaseReference getChat(String uid) {
        return chatsReference.child(getCombinedUid(uid));
    }

    public Task<DataSnapshot> getSchedules() {
        return schedulesReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid()).get();
    }

    public Task<Void> addSchedules(Schedule schedule) {
        return schedulesReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid())
                .child(schedule.getId()).setValue(schedule);
    }

    public Task<Void> deleteSchedules(Schedule schedule) {
        return schedulesReference.child(FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid())
                .child(schedule.getId()).removeValue();
    }

}
