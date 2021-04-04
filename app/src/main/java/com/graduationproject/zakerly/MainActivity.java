package com.graduationproject.zakerly;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.core.base.BaseActivity;
import com.graduationproject.zakerly.databinding.ActivityMainBinding;
import com.graduationproject.zakerly.network.GoogleClient;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.graduationproject.zakerly.core.constants.NavigationConstants;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private ChipNavigationBar navigationBar;
    FirebaseAuth mAuth= FirebaseAuth.getInstance();
    GoogleClient googleClient=new GoogleClient(MainActivity.this);
    private CallbackManager callbackManager;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // go to other activity
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
        navigationBar.setItemSelected(R.id.home, true);

        //used for facebook sign in
        callbackManager=CallbackManager.Factory.create();
        //you should define facebook login button in your xml then implement it here and call method registerCallback
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
            case NavigationConstants.HOME_PAGE:
                navigationBar.setItemSelected(R.id.home, true);
                break;
            case NavigationConstants.SEARCH_PAGE:
                navigationBar.setItemSelected(R.id.search, true);
                break;
            case NavigationConstants.FAVORITE_PAGE:
                navigationBar.setItemSelected(R.id.favorite, true);
                break;
            case NavigationConstants.ACCOUNT_PAGE:
                navigationBar.setItemSelected(R.id.account, true);
                break;
            case NavigationConstants.NOTIFICATION_PAGE:
                navigationBar.setItemSelected(R.id.notification, true);
        }
    }
    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        // this line used in  facebook signIn
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleClient.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                googleClient.firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}