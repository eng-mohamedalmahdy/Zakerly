package com.graduationproject.zakerly.authentication.signIn;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.cache.Realm.RealmQueries;
import com.graduationproject.zakerly.core.constants.AuthTypes;
import com.graduationproject.zakerly.core.network.FacebookClient;
import com.graduationproject.zakerly.core.network.GoogleClient;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;


public class SignInRepository {


    private final MutableLiveData<FirebaseUser> userLiveData;
    public static final String TAG = "SIGN_IN_REPO";

    public SignInRepository() {
        this.userLiveData = new MutableLiveData<>();

        if (FireBaseAuthenticationClient.getInstance().getCurrentUser() != null) {
            userLiveData.postValue(FireBaseAuthenticationClient.getInstance().getCurrentUser());
        }
    }

    public Task<AuthResult> signIn(String email, String password, Fragment fragment) {
        return FireBaseAuthenticationClient.getInstance().signIn(email, password).addOnCompleteListener(task -> {
            if ((task.isSuccessful())) {
                Toasty.success(fragment.getContext(), fragment.getText(R.string.user_signin_success)).show();
                this.userLiveData.setValue(task.getResult().getUser());
                RealmQueries queries = new RealmQueries();
                FirebaseDataBaseClient.getInstance().doWithUserObject(email, student -> {
                    Log.d("Sing in Complete", "signIn: " + student.toString());

                    queries.addStudent(student);
                    NavHostFragment.findNavController(fragment).navigate(R.id.action_signInFragment_to_student_app_navigation);
                    return true;
                }, (instructor -> {
                    Log.d("Sing in complete", "signIn: " + instructor.toString());

                    queries.addTeacher(instructor);
                    return true;
                }), s -> {
                    Log.d("Sing in error", "signIn: " + task.getException().getLocalizedMessage());

                    return true;
                });
            } else {
                Log.d("Sing in error", "signIn: " + task.getException().getLocalizedMessage());
                String errorMessage = fragment.getString(R.string.login_failure) + "\n" + task.getException().getLocalizedMessage();
                Toasty.error(fragment.getContext(), errorMessage).show();
            }
        });


    }


    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public void signInWithGoogle(MainActivity activity) {
        activity.getGoogleClient().signIn(activity);
    }

    public void signInWithFacebook(MainActivity activity) {
        CallbackManager callbackManager = activity.getFacebookCallbackManager();
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
        FacebookClient client = FacebookClient.getInstance();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.v("FB_Details", "onSuccess");
                        client.handleFacebookAccessToken(loginResult.getAccessToken())
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        getFbDetails(task.getResult().getUser().getUid(), loginResult.getAccessToken(), activity);
                                    } else {
                                        Log.d("FB_Details", "onSuccess: " + task.getException().toString());
                                        Toasty.error(activity, R.string.failed_to_login).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancel() {
                        Log.v("FB_Details", "Canceled");
                        Toasty.error(activity, R.string.failed_to_login).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.v("FB_Details", exception.toString());

                        Toasty.error(activity, R.string.failed_to_login).show();
                    }
                });
    }

    private void getFbDetails(String uid, final AccessToken accessToken, MainActivity activity) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken,
                (object, response) -> {
                    Log.v("FB_Details", object.toString());
                    String fName = object.optString("first_name");
                    String lName = object.optString("last_name");
                    String email_fb = object.optString("email");
                    NavController controller = Navigation.findNavController(activity, R.id.nav_host_fragment);

                    FirebaseDataBaseClient.getInstance().getUser(email_fb).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if ((snapshot.getChildrenCount() > 0)) {
                                Toasty.success(activity, R.string.user_signin_success).show();
                                RealmQueries queries = new RealmQueries();
                                FirebaseDataBaseClient.getInstance().doWithUserObject(email_fb, student -> {
                                    queries.addStudent(student);
                                    controller.navigate(R.id.action_signInFragment_to_student_app_navigation);
                                    return true;
                                }, (instructor -> {
                                    queries.addTeacher(instructor);
                                    return true;
                                }), s -> {
                                    Log.d("Sing in error", "signIn: " + s);
                                    return true;
                                });


                            } else {
                                controller.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment(AuthTypes.AUTH_FACEBOOK, uid, fName, lName, email_fb));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: " + "CANCELED");
                        }
                    });


                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name, last_name, email,link");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void signOut() {
        GoogleClient.getInstance().signOut();
        FacebookClient.getInstance().signOut();
        FireBaseAuthenticationClient.getInstance().signOut();
    }
}
