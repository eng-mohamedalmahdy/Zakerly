
package com.graduationproject.zakerly.navigation.profilestudent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.core.constants.BottomNavigationConstants;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentProfileStudentBinding;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class ProfileStudentFragment extends Fragment {




    private FragmentProfileStudentBinding binding;
    private ProfileStudentViewModel mViewModel;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 101;

    ImageView action, favorite, videoCall, note, calender;
    AppCompatImageView profile, camera;
    TextView profileName;
    RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileStudentBinding.inflate(inflater, container, false);
        mViewModel = new ProfileStudentViewModelFactory(new ProfileStudentRepository()).create(ProfileStudentViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        ((MainActivity) getActivity()).setSelectedPage(BottomNavigationConstants.ACCOUNT_PAGE);
        ((MainActivity) getActivity()).setNavigationVisibility(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListener();
    }

    private void initViews() {
        action = binding.icAction;
        favorite = binding.favoriteIcon;
        videoCall = binding.videocallIcon;
        note = binding.noteIcon;
        calender = binding.calenderIcon;
        profile = binding.profileImage;
        camera = binding.camera;
        profileName = binding.textProfileName;
        mRecyclerView = binding.recyclerViewMyteacher;
    }

    private void initListener() {
        camera.setOnClickListener(view -> checkPermission());
    }

    private void checkPermission() {

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
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            // set image in amageView.
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                profile.setImageBitmap(bitmap);
                uploadImageToFireStore(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFireStore(Uri uri) {
        if (uri != null) {
            String userUid = FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid();
            StorageReference ref = FirebaseStorage.getInstance().getReference("profiles/" + userUid);
            ref.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                Log.d("Profile Fragment ", "Successfully uploaded . . ");

                ref.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    Log.d("Profile Fragment ", "File Location :" + downloadUri);
                    saveImageToFirebaseDatabase(uri.toString());
                });
            });

        }
    }

    private void saveImageToFirebaseDatabase(String imageProfileUri) {

        FirebaseDataBaseClient.getInstance()
                .setCurrentUserProfilePicture(imageProfileUri)
                .addOnSuccessListener(task -> {
                    Log.d("Profile Fragment ", "finally Image saved to firebase  . . . ");
                });
    }


}





