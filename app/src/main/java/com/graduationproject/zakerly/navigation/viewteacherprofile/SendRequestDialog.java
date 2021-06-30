package com.graduationproject.zakerly.navigation.viewteacherprofile;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.cache.DataStoreManger;
import com.graduationproject.zakerly.core.constants.NotificationType;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.PushNotification;
import com.graduationproject.zakerly.core.models.Student;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.network.retrofit.RetrofitClient;
import com.graduationproject.zakerly.databinding.DialogSendRequestBinding;

import org.jetbrains.annotations.NotNull;

import es.dmoral.toasty.Toasty;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SendRequestDialog extends DialogFragment {
    private DialogSendRequestBinding binding;
    private SendRequestDialogArgs args;
    private CompositeDisposable disposable;
    public static final String TAG = "SendRequestDialog";
    private Student currentUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = SendRequestDialogArgs.fromBundle(requireArguments());
        disposable = new CompositeDisposable();
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.dialog);

    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSendRequestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        FirebaseDataBaseClient.getInstance().getCurrentUser().addOnSuccessListener(snapshot -> {
            currentUser = snapshot.getValue(Student.class);
            Glide.with(getContext())
                    .load(currentUser.getUser().getProfileImg())
                    .centerCrop()
                    .error(R.drawable.no_user)
                    .into(binding.senderImage);

            Glide.with(getContext())
                    .load(args.getInstructor().getUser().getProfileImg())
                    .centerCrop()
                    .error(R.drawable.no_user)
                    .into(binding.receiverImage);
        });

        binding.cancel.setOnClickListener(v -> dismiss());
        binding.send.setOnClickListener(v -> {
            clearErrors();
            if (valid()) {
                String notificationId = FirebaseDatabase.getInstance().getReference().push().getKey();
                NotificationData notificationData =
                        new NotificationData(
                                System.currentTimeMillis(),
                                notificationId,
                                binding.learningTopic.getText().toString(),
                                binding.requestBody.getText().toString(),
                                NotificationType.REQUEST,
                                currentUser.getUser().getFirstName() + " " + currentUser.getUser().getLastName(),
                                currentUser.getUser().getUID(),
                                args.getInstructor().getUser().getUID(),
                                Integer.parseInt(binding.numberOfHoursInput.getText().toString()));

                PushNotification pushNotification = new PushNotification(notificationData, args.getInstructor().getUser().getNotificationToken());
                sendNotification(pushNotification);
                Toasty.success(getContext(),getString(R.string.request_sent)).show();
                dismiss();
            }
        });
    }

    private void clearErrors() {
        binding.learningTopicContainer.setErrorEnabled(false);
        binding.numberOfHoursInputContainer.setErrorEnabled(false);
        binding.requestBodyContainer.setErrorEnabled(false);
    }

    private boolean valid() {
        if (binding.learningTopic.getText().toString().isEmpty()) {
            binding.learningTopicContainer.setErrorEnabled(true);
            binding.learningTopicContainer.setError(getString(R.string.this_field_cannot_be_empty));
            return false;
        }
        if (binding.numberOfHoursInput.getText().toString().isEmpty()) {
            binding.numberOfHoursInputContainer.setErrorEnabled(true);
            binding.numberOfHoursInputContainer.setError(getString(R.string.this_field_cannot_be_empty));
            return false;
        }
        if (binding.requestBody.getText().toString().isEmpty()) {
            binding.requestBodyContainer.setErrorEnabled(true);
            binding.requestBodyContainer.setError(getString(R.string.this_field_cannot_be_empty));
            return false;
        }
        return true;
    }

    private void sendNotification(PushNotification notification) {
        Disposable d = RetrofitClient.getApi()
                .postNotification(notification)
                .subscribe(responseBodyResponse -> {

                            Log.d(TAG, "sendNotification: body " + responseBodyResponse.body().string());
                        },
                        throwable -> Log.d(TAG, "sendNotification: error" + throwable.getMessage()));
        disposable.add(d);
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.dispose();
    }

}
