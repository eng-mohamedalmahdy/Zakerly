package com.graduationproject.zakerly.meetings.requestmeeting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.MeetingAttendeesTypes;
import com.graduationproject.zakerly.core.constants.NotificationType;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.PushNotification;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.network.retrofit.RetrofitClient;
import com.graduationproject.zakerly.core.util.Util;
import com.graduationproject.zakerly.databinding.FragmentMeetingRequestingBinding;
import com.graduationproject.zakerly.meetings.meetingshome.ContactsListFragmentDirections;

import org.jetbrains.annotations.NotNull;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MeetingRequestingFragment extends Fragment {

    private static final String TAG = "MeetingRequestingFragme";
    private FragmentMeetingRequestingBinding binding;
    private MeetingRequestingFragmentArgs args;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = MeetingRequestingFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeetingRequestingBinding.inflate(inflater, container, false);
        ((MainActivity) requireActivity()).setNavigationVisibility(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.meetingTypeIcon.setImageResource(args.getNotification().getNotificationType() == NotificationType.VIDEO_MEETING ? R.drawable.ic_baseline_videocam_24 : R.drawable.ic_baseline_call_24);
        binding.meetingStatusType.setText(args.getAttendeeType() == MeetingAttendeesTypes.SENDER ? R.string.sending : R.string.incoming);
        binding.receiverFirstLetter.setText(Character.toString(
                (args.getAttendeeType() == MeetingAttendeesTypes.SENDER ? args.getNotification().getReceiverName() : args.getNotification().getSenderName()).charAt(0)
        ));
        binding.receiverName.setText(args.getAttendeeType() == MeetingAttendeesTypes.SENDER ?
                args.getNotification().getReceiverName() : args.getNotification().getSenderName());
        binding.cancel.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
            NotificationData notification = args.getNotification();
            notification.setNotificationType(NotificationType.CANCEL);
            FirebaseDataBaseClient.getInstance().getUserByUid(args.getNotification().getSenderUid())
                    .addOnSuccessListener(dataSnapshot -> {
                        PushNotification p = new PushNotification(notification, dataSnapshot.child("user").getValue(User.class).getNotificationToken());
                        Util.sendNotification(p);

                    });
            NavHostFragment.findNavController(this).navigateUp();

        });
        binding.answer.setOnClickListener(v -> {
            try {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL("https://meet.jit.si"))
                        .setRoom(("zakerly" + args.getNotification().getNotificationId()))
                        .setAudioMuted(false)
                        .setVideoMuted(false)
                        .setAudioOnly(args.getNotification().getNotificationType() == NotificationType.VOICE_MEETING)
                        .setWelcomePageEnabled(false)
                        .build();
                NavHostFragment.findNavController(this).navigateUp();
                JitsiMeetActivity.launch(getContext(), options);

                NotificationData notification = args.getNotification();
                notification.setNotificationType(NotificationType.ANSWER);
                FirebaseDataBaseClient.getInstance().getUserByUid(args.getNotification().getSenderUid())
                        .addOnSuccessListener(dataSnapshot -> {
                            PushNotification p = new PushNotification(notification, dataSnapshot.child("user").getValue(User.class).getNotificationToken());
                            Util.sendNotification(p);

                        });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        binding.answer.setVisibility(args.getAttendeeType() == MeetingAttendeesTypes.RECEIVER ? View.VISIBLE : View.GONE);
    }


}