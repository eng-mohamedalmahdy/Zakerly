package com.graduationproject.zakerly;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.CallbackManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.authentication.signIn.SignInFragmentDirections;
import com.graduationproject.zakerly.core.base.BaseActivity;
import com.graduationproject.zakerly.core.constants.AuthTypes;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.ActivityMainBinding;
import com.graduationproject.zakerly.core.network.GoogleClient;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.graduationproject.zakerly.core.constants.BottomNavigationConstants;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private ActivityMainBinding binding;
    private ChipNavigationBar navigationBar;
    private GoogleClient googleClient;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
        initListeners();
        setNavigationVisibility(false);
        navigationBar.setItemSelected(R.id.home, true);

        googleClient = GoogleClient.getInstance();
        googleClient.createRequest(this);

        //used for facebook sign in
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);

    }


    private void initViews() {
        navigationBar = binding.bottomNavigation;
    }

    private void initListeners() {
        navigationBar.setOnItemSelectedListener(i -> {
            //TODO Add the navigation logic here
        });
    }

    public void setNavigationVisibility(boolean visible) {
        navigationBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setSelectedPage(int page) {
        switch (page) {
            case BottomNavigationConstants.HOME_PAGE:
                navigationBar.setItemSelected(R.id.home, true);
                break;
            case BottomNavigationConstants.SEARCH_PAGE:
                navigationBar.setItemSelected(R.id.search, true);
                break;
            case BottomNavigationConstants.FAVORITE_PAGE:
                navigationBar.setItemSelected(R.id.favorite, true);
                break;
            case BottomNavigationConstants.ACCOUNT_PAGE:
                navigationBar.setItemSelected(R.id.account, true);
                break;
            case BottomNavigationConstants.NOTIFICATION_PAGE:
                navigationBar.setItemSelected(R.id.notification, true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // this line used in  facebook signIn
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleClient.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String fName = account.getDisplayName();
                String lName = account.getFamilyName();
                String email = account.getEmail();
                NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);

                FirebaseDataBaseClient.getInstance().getUser(email).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d(TAG, "onDataChange: " + snapshot.toString());
                        if ((snapshot.getChildrenCount() > 0)) {
                            Toasty.success(MainActivity.this, R.string.user_signin_success).show();
                        } else {
                            controller.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment(AuthTypes.AUTH_G_MAIL, account.getIdToken(), fName, lName, email));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: " + "CANCELD");
                    }
                });

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public GoogleClient getGoogleClient() {
        return googleClient;
    }

    public CallbackManager getFacebookCallbackManager() {
        return callbackManager;
    }
}