package com.graduationproject.zakerly.userutils.editprofile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.AuthTypes;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.Specialisation;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentEditProfileBinding;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;
import io.realm.RealmList;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {
    private static final String TAG = "EDIT_PROFILE";

    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSION_CODE = 101;

    private EditProfileFragmentArgs args;
    private FragmentEditProfileBinding binding;
    private EditProfileViewModel viewModel;
    private RealmList<Specialisation> selectedSpecialisationsList;
    private RealmList<String> selectedSpecialisationsNamesList;
    private boolean firstTime = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Init args
        args = EditProfileFragmentArgs.fromBundle(requireArguments());
        viewModel = new EditProfileViewModelFactory().create(EditProfileViewModel.class);

        selectedSpecialisationsList = new RealmList<>();
        selectedSpecialisationsNamesList = new RealmList<>();

        //Get user data
        FirebaseDataBaseClient.getInstance().getCurrentUser().addOnSuccessListener(snapshot -> {
            if (args.getUserType().equals(UserTypes.TYPE_STUDENT)) {
                viewModel.getStudentMutableLiveData().postValue(snapshot.getValue(Student.class));
            } else {
                viewModel.getInstructorMutableLiveData().postValue(snapshot.getValue(Instructor.class));
            }
            viewModel.getUserMutableLiveData().postValue(snapshot.child("user").getValue(User.class));

        }).addOnFailureListener(e -> Log.d(TAG, "onCreate: " + e.getMessage()));
        viewModel.setUpSpecialisationsList(getResources());

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater);
        binding.setType(args.getUserType());
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.camera.setOnClickListener(cameraButton -> checkPermission());
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), user -> {
            Glide.with(requireContext())
                    .load(user.getProfileImg())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .into(binding.profileImage);
        });

        viewModel.getSpecialisationNamesMutableLifeData().observe(getViewLifecycleOwner(), specialisations -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, specialisations);
            binding.specialisationsList.setAdapter(adapter);
        });
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), user -> {
            selectedSpecialisationsList = user.getInterests();
            selectedSpecialisationsNamesList = user.getInterests().stream().map(specialisation -> {
                Locale l;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    l = getResources().getConfiguration().getLocales().get(0);
                } else {
                    //noinspection deprecation
                    l = getResources().getConfiguration().locale;
                }
                return l.getLanguage().equalsIgnoreCase("ar") ? specialisation.getAr() : specialisation.getEn();

            }).collect(Collectors.toCollection(RealmList::new));

            for (int i = 0; i < selectedSpecialisationsNamesList.size(); i++) {
                addChip(selectedSpecialisationsList.get(i), selectedSpecialisationsNamesList.get(i));
            }
        });
        binding.specialisationsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!firstTime) {
                    Specialisation specialisation = viewModel.getSpecialisationMutableLifeData().getValue().get(position);
                    String specialisationName = viewModel.getSpecialisationNamesMutableLifeData().getValue().get(position);
                    if (!selectedSpecialisationsList.contains(specialisation)) {
                        addChip(specialisation, specialisationName);
                        selectedSpecialisationsList.add(specialisation);
                    }
                } else {
                    firstTime = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.apply.setOnClickListener(v ->
        {
            if (validate()) {
                if (args.getUserType().equals(UserTypes.TYPE_STUDENT)) {
                    FirebaseDataBaseClient.getInstance().addStudent(getStudent())
                            .addOnSuccessListener(aVoid -> {
                                Toasty.success(requireContext(), R.string.profile_updated_sucessfully).show();
                                NavHostFragment.findNavController(this).navigateUp();
                            })
                            .addOnFailureListener(e -> {
                                Toasty.error(requireContext(), R.string.failed_to_update_profile).show();
                            });
                } else {
                    FirebaseDataBaseClient.getInstance().addInstructor(getInstructor()).addOnSuccessListener(aVoid -> {
                        Toasty.success(requireContext(), R.string.profile_updated_sucessfully).show();
                        NavHostFragment.findNavController(this).navigateUp();
                    })
                            .addOnFailureListener(e -> {
                                Toasty.error(requireContext(), R.string.failed_to_update_profile).show();
                            });
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        String fName = binding.firstNameTextField.getEditText().getText().toString();
        String lastName = binding.lastNameTextField.getEditText().getText().toString();
        String email = binding.emailTextField.getEditText().getText().toString();
        String pricePerHour = binding.priceTextField.getEditText().getText().toString();

        if (viewModel.getUserMutableLiveData().getValue().getAuthType().equals(AuthTypes.AUTH_EMAIL)) {
            if (fName.isEmpty()) {
                valid = false;
                binding.firstNameTextField.setErrorEnabled(true);
                binding.firstNameTextField.setError(getText(R.string.this_field_cannot_be_empty));
            }
            if (lastName.isEmpty()) {
                valid = false;
                binding.lastNameTextField.setErrorEnabled(true);
                binding.lastNameTextField.setError(getText(R.string.this_field_cannot_be_empty));

            }
            if (!fName.matches("^[A-Za-z]+$")) {
                valid = false;
                binding.firstNameTextField.setErrorEnabled(true);
                binding.firstNameTextField.setError(getText(R.string.invalid_name));
            }
            if (!lastName.matches("^[A-Za-z]+$")) {
                valid = false;
                binding.lastNameTextField.setErrorEnabled(true);
                binding.lastNameTextField.setError(getText(R.string.invalid_name));
            }
            if (email.isEmpty()) {
                valid = false;
                binding.emailTextField.setErrorEnabled(true);
                binding.emailTextField.setError(getText(R.string.this_field_cannot_be_empty));
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                valid = false;
                binding.emailTextField.setErrorEnabled(true);
                binding.emailTextField.setError(getText(R.string.invalid_email));
            }

        }
        if (pricePerHour.isEmpty() && args.getUserType().equals(UserTypes.TYPE_INSTRUCTOR)) {
            valid = false;
            binding.priceTextField.setErrorEnabled(true);
            binding.priceTextField.setError(getText(R.string.this_field_cannot_be_empty));
        }
        if (selectedSpecialisationsList.isEmpty()) {
            valid = false;
            Toasty.error(requireContext(), R.string.add_one_interest).show();
        }

        if (valid) clearErrors();
        return valid;
    }

    public Instructor getInstructor() {

        return new Instructor(new User(viewModel.getUserMutableLiveData().getValue().getUID(),
                UserTypes.TYPE_INSTRUCTOR,
                binding.firstNameTextField.getEditText().getText().toString(),
                binding.lastNameTextField.getEditText().getText().toString(),
                binding.emailTextField.getEditText().getText().toString(),
                viewModel.getUserMutableLiveData().getValue().getAuthType(),
                viewModel.getUserMutableLiveData().getValue().getProfileImg(),
                selectedSpecialisationsList),
                Double.parseDouble(binding.priceTextField.getEditText().getText().toString()));

    }

    public Student getStudent() {
        return new Student(new User(viewModel.getUserMutableLiveData().getValue().getUID(),
                UserTypes.TYPE_STUDENT,
                binding.firstNameTextField.getEditText().getText().toString(),
                binding.lastNameTextField.getEditText().getText().toString(),
                binding.emailTextField.getEditText().getText().toString(),
                viewModel.getUserMutableLiveData().getValue().getAuthType(),
                viewModel.getUserMutableLiveData().getValue().getProfileImg(),
                selectedSpecialisationsList));
    }

    private void clearErrors() {
        binding.firstNameTextField.setErrorEnabled(false);
        binding.lastNameTextField.setErrorEnabled(false);
        binding.emailTextField.setErrorEnabled(false);
        binding.priceTextField.setErrorEnabled(false);
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
                binding.profileImage.setImageBitmap(bitmap);
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
                Log.d(TAG, "Successfully uploaded . . ");
                Toasty.success(requireContext(), R.string.profile_image_updated).show();
                ref.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    Log.d(TAG, "File Location :" + downloadUri);
                    User newUser = viewModel.getUserMutableLiveData().getValue();
                    newUser.setProfileImg(downloadUri.toString());
                    viewModel.getUserMutableLiveData().postValue(newUser);
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

    private void addChip(Specialisation specialisation, String specialisationName) {
        Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.view_chip_view, null, false);
        chip.setText(specialisationName);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.bottomMargin = 8;
        params.topMargin = 8;
        params.leftMargin = 8;
        params.rightMargin = 8;
        chip.setLayoutParams(params);
        binding.selectedSpecialisations.addView(chip);
        chip.setOnCloseIconClickListener(v -> {
            binding.selectedSpecialisations.removeView(chip);
            selectedSpecialisationsList.remove(specialisation);
        });
    }

}