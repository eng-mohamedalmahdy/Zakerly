
package com.graduationproject.zakerly.authentication.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.databinding.FragmentProfileStudentBinding;

import java.io.IOException;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class ProfileStudentFragment extends Fragment {



    // note btn save to test , test only .  . (:-
    // my profile picture either is for test hhhhhhhhh

    private FragmentProfileStudentBinding binding;
    private ProfileStudentViewModel mViewModel;

    private Uri uri;

    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 101;

    ImageView action, favorite, videoCall, note, calender;
    AppCompatImageView profile, camera;
    TextView profileName;
    RecyclerView mRecyclerView;
    Button saveBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileStudentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListener();

        // get the Firebase  storage reference
        mViewModel = new ProfileStudentViewModelFactory(new ProfileStudentRepository()).create(ProfileStudentViewModel.class);
    }

    private void initViews() {
        saveBtn =binding.saveBtn;
        action = binding.icAction;
        favorite = binding.favoriteIcon;
        videoCall = binding.videocallIcon;
        note = binding.noteIcon;
        calender = binding.calenderIcon;
        profile = binding.profileImage;
        camera = binding.camera;
        profileName = binding.textProfileName;
        mRecyclerView = binding.recyclerView;
    }

    private void initListener() {

        camera.setOnClickListener(view -> checkPermission());
        saveBtn.setOnClickListener(view -> {
            uploadImageToFireStore();
        });

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                // permission not granted , request it .
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup for runTime Permission
                requestPermissions(permission, PERMISSION_CODE);

            } else {
                // permission already granted
                pickImageFromGallery();
            }

        } else {
            // System os is less than 23 .
            pickImageFromGallery();
        }
    }

    private void pickImageFromGallery() {
        // intent to pick ImageFrom Gallery.
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }

    // handle result of run time Permission >>
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted .
                    pickImageFromGallery();
                } else {
                    // Permission was Denied .
                    Toasty.error(getContext(), "Permission denied ... !").show();
                }
            }
        }
    }

    // handle result of picked image >>
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data !=null) {
            // set image in amageView.
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFireStore(){
        if (uri !=null) {
            String fillName = UUID.randomUUID().toString();
            StorageReference ref = FirebaseStorage.getInstance().getReference("image.png" + fillName);
            ref.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                Log.d("Profile Fragmeent ", "Successfully uploaded . . ");

                ref.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.d("Profile Fragmeent ", "File Location :" + uri);
                    saveImageToFirebaseDatabase(uri.toString());
                });
            });

        }
    }

    private void saveImageToFirebaseDatabase(String imageProfileUri) {
        String id = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FireBaseAuthenticationClient.getInstance().getCurrentUser()
        +"/"+profile+"/"+id);
        ref.setValue(profile).addOnSuccessListener(aVoid -> {
            Log.d("Profile Fragmeent " , "finally Image saved to firebase  . . . " );
        });


    }


}





