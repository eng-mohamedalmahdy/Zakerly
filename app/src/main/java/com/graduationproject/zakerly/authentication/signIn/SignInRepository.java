package com.graduationproject.zakerly.authentication.signIn;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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


    private MutableLiveData<FirebaseUser> userLiveData;

    public SignInRepository() {
        this.userLiveData = new MutableLiveData<>();

        if (FireBaseAuthenticationClient.getInstance().getCurrentUser() != null) {
            userLiveData.postValue(FireBaseAuthenticationClient.getInstance().getCurrentUser());
        }
    }

    public Task<AuthResult> signIn(String email, String password, Context context) {
        return FireBaseAuthenticationClient.getInstance().signIn(email, password).addOnCompleteListener(task -> {
            if ((task.isSuccessful())) {
                Toasty.success(context, context.getText(R.string.user_signin_success)).show();
                this.userLiveData.setValue(task.getResult().getUser());
                RealmQueries queries = new RealmQueries();
                FirebaseDataBaseClient.getInstance().doWithUserObject(email, student -> {
                    queries.addStudent(student);
                    return true;
                }, (instructor -> {
                    queries.addTeacher(instructor);
                    return true;
                }), s -> {
                    Log.d("Sing in error", "signIn: " + s);
                    return true;
                });
            } else {
                Toasty.error(context, context.getText(R.string.login_failure)).show();
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
                    controller.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment(AuthTypes.AUTH_FACEBOOK, uid, fName, lName, email_fb));
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
