
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.BottomNavigationConstants;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.ConnectionModel;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentProfileStudentBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class ProfileStudentFragment extends Fragment {


    private FragmentProfileStudentBinding binding;
    private ProfileStudentViewModel mViewModel;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 101;
    public static final String TAG = "PROFILE_F";

    private ImageView action, favorite, videoCall, note, calender;
    private AppCompatImageView profile, camera;
    private TextView profileName;
    private RecyclerView mRecyclerView;
    private ProfileStudentAdapter adapter;


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
        adapter = new ProfileStudentAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void initListener() {

        binding.favoriteIcon.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(ProfileStudentFragmentDirections.actionProfileStudentFragmentToHomeChatFragment()));
        camera.setOnClickListener(view -> checkPermission());

        FirebaseDataBaseClient.getInstance().getProfileImageUrl()
                .addOnSuccessListener(snapshot ->
                {
                    Log.d(TAG, "initListener: Image Loaded");
                    Glide.with(requireContext())
                            .load(snapshot.getValue(String.class))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .placeholder(R.drawable.baseline_account_circle_24)
                            .into(profile);

                })
                .addOnFailureListener(e -> Log.d(TAG, "initListener: " + e.getMessage()));


        FirebaseDataBaseClient.getInstance().getCurrentUser().addOnSuccessListener(snapshot -> {
            Student currentStudent = snapshot.getValue(Student.class);
            binding.textProfileName.setText((currentStudent.getUser().getFirstName() + " " + currentStudent.getUser().getLastName()));
        });


        binding.noteIcon.setOnClickListener((v) -> NavHostFragment.findNavController(this).navigate(ProfileStudentFragmentDirections.actionProfileStudentFragmentToEditProfileFragment(UserTypes.TYPE_STUDENT)));
        FirebaseDataBaseClient.getInstance().getConnectionsForCurrentUser().addOnSuccessListener(connection -> {
            ArrayList<Instructor> instructors = new ArrayList<>();

            connection.getChildren().forEach(c ->
            {
                ConnectionModel connectionModel = c.getValue(ConnectionModel.class);
                if (connectionModel != null && connectionModel.isCurrentlyConnected()) {
                    FirebaseDataBaseClient
                            .getInstance()
                            .getUserByUid(connectionModel.getToUid())
                            .addOnSuccessListener(s -> {
                                instructors.add(s.getValue(Instructor.class));
                                adapter.setList(instructors);
                                Log.d(TAG, "initListener: " + instructors);
                            });
                }
            });
        });

        videoCall.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(ProfileStudentFragmentDirections.actionProfileStudentFragmentToVideoAppNavigation()));
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
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //here you can choose quality factor in third parameter(ex. i choosen 25)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] fileInBytes = baos.toByteArray();
                uploadImageToFireStore(fileInBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFireStore(byte[] img) {
        if (img != null) {
            String userUid = FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid();
            StorageReference ref = FirebaseStorage.getInstance().getReference("profiles/" + userUid);
            ref.putBytes(img).addOnSuccessListener(taskSnapshot -> {
                Log.d(TAG, "Successfully uploaded . . ");

                ref.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    Log.d(TAG, "File Location :" + downloadUri);
                    saveImageToFirebaseDatabase(downloadUri.toString());
                });
            }).addOnFailureListener(command -> Log.d(TAG, "uploadImageToFireStore: " + command.getMessage()));

        }
    }

    private void saveImageToFirebaseDatabase(String imageProfileUri) {

        FirebaseDataBaseClient.getInstance()
                .setCurrentUserProfilePicture(imageProfileUri)
                .addOnSuccessListener(task -> Log.d(TAG, "finally Image saved to firebase  . . . "));
    }


}





