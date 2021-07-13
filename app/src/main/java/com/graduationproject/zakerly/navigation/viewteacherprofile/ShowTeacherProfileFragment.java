package com.graduationproject.zakerly.navigation.viewteacherprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.GenericTypeIndicator;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.MeetingAttendeesTypes;
import com.graduationproject.zakerly.core.constants.NotificationType;
import com.graduationproject.zakerly.core.models.ConnectionModel;
import com.graduationproject.zakerly.core.models.Instructor;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.OpinionModel;
import com.graduationproject.zakerly.core.models.PushNotification;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.util.Util;
import com.graduationproject.zakerly.databinding.FragmentShowTeacherProfileBinding;
import com.graduationproject.zakerly.meetings.meetingshome.ContactsListFragmentDirections;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import timber.log.Timber;

public class ShowTeacherProfileFragment extends Fragment {

    private static final String TAG = "ShowTeacherProfileFragment";
    private FragmentShowTeacherProfileBinding binding;
    private ShowTeacherProfileFragmentArgs args;
    private ShowTeacherProfileViewModel viewModel;
    private ShowTeacherProfileAdapter adapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShowTeacherProfileBinding.inflate(inflater, container, false);
        args = ShowTeacherProfileFragmentArgs.fromBundle(requireArguments());
        viewModel = new ShowTeacherProfileViewModelFactory(new ShowTeacherProfileRepository()).create(ShowTeacherProfileViewModel.class);
        binding.setInstructor(args.getInstructor());
        ((MainActivity) requireActivity()).setNavigationVisibility(false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ShowTeacherProfileAdapter();
        binding.teacherProfileRecyclerView.setAdapter(adapter);
        setUpViews();
        initListeners();

    }


    private void initListeners() {
        binding.teacherProfileIcAdd.setOnClickListener(v -> FirebaseDataBaseClient.getInstance()
                .getConnectionData(args.getInstructor().getUser().getUID())
                .addOnSuccessListener(connection -> {
                    ConnectionModel connectionModel = connection.getValue(ConnectionModel.class);
                    if (connectionModel != null) {
                        FirebaseDataBaseClient
                                .getInstance()
                                .getNotification(args.getInstructor().getUser().getUID(), connectionModel.getLatestRequestUid())
                                .addOnSuccessListener(notification -> NavHostFragment.
                                        findNavController(this)
                                        .navigate(ShowTeacherProfileFragmentDirections
                                                .actionProfileStudentFragmentToSendRequestDialog(args.getInstructor(),
                                                        notification.getValue(NotificationData.class), connectionModel)));
                    } else {
                        NavHostFragment.
                                findNavController(this)
                                .navigate(ShowTeacherProfileFragmentDirections
                                        .actionProfileStudentFragmentToSendRequestDialog(args.getInstructor(), null, null));
                    }
                }));

        binding.teacherProfileIcSend.setOnClickListener(v -> {
            if (!binding.feedbackText.getText().toString().isEmpty() || binding.teacherProfileRateInstructor.getRating() <= 0) {
                FirebaseDataBaseClient.getInstance().getProfileImageUrl().addOnSuccessListener(imageSnapshot -> {
                    String imageUrl = imageSnapshot.getValue(String.class);
                    OpinionModel opinionModel = new OpinionModel(imageUrl, binding.feedbackText.getText().toString(), binding.teacherProfileRateInstructor.getRating(), new Date().toString());
                    FirebaseDataBaseClient.getInstance().addFeedback(args.getInstructor().getUser().getUID(), opinionModel);
                    int rate = (int) Math.floor(binding.teacherProfileRateInstructor.getRating());
                    Instructor instructor = args.getInstructor();
                    instructor.setRatesCount(instructor.getRatesCount() + 1);
                    instructor.setRateSum(rate + instructor.getRateSum());
                    instructor.setAverageRate(instructor.getAverageRate());
                    switch (rate) {
                        case 1:
                            instructor.setOneStarCount(instructor.getOneStarCount() + 1);
                            break;
                        case 2:
                            instructor.setTwoStarCount(instructor.getTwoStarCount() + 1);
                            break;
                        case 3:
                            instructor.setThreeStarCount(instructor.getThreeStarCount() + 1);
                            break;
                        case 4:
                            instructor.setFourStarCount(instructor.getFourStarCount() + 1);
                            break;
                        case 5:
                            instructor.setFiveStarCount(instructor.getFiveStarCount() + 1);
                            break;
                    }
                    FirebaseDataBaseClient.getInstance().setUser(args.getInstructor().getUser().getUID(), instructor)
                            .addOnSuccessListener(command -> {
                                Toasty.success(getContext(), R.string.thanks_for_feedback).show();
                                NavHostFragment.findNavController(this).navigateUp();
                            });
                });
            } else {
                Toasty.error(getContext(), R.string.feedback_error).show();
            }
        });
        binding.chat.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(ShowTeacherProfileFragmentDirections.actionShowTeacherProfileFragmentToChatFragment(args.getInstructor().getUser())));
        binding.teacherProfileIcDisconnect.setOnClickListener(v ->
                FirebaseDataBaseClient.getInstance().removeConnections(args.getInstructor().getUser().getUID(), FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid()));
        binding.video.setOnClickListener(v -> {
            User user = args.getInstructor().getUser();
            String notificationId = FirebaseDataBaseClient.getInstance().getRandomKey();
            NotificationData notification = new NotificationData(System.currentTimeMillis(),
                    notificationId, "Voice meeting request",
                    "Voice meeting request",
                    NotificationType.VIDEO_MEETING,
                    FireBaseAuthenticationClient.getInstance().getCurrentUser().getDisplayName(),
                    user.getFirstName() + " " + user.getLastName(),
                    FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid(),
                    user.getUID(),
                    "",
                    0);
            PushNotification p = new PushNotification(notification, user.getNotificationToken());
            Util.sendNotification(p);
            NavHostFragment.findNavController(this).navigate(ShowTeacherProfileFragmentDirections.actionShowTeacherProfileFragmentToMeetingRequestingFragment(notification, MeetingAttendeesTypes.SENDER));

        });
    }

    private void setUpViews() {
        Glide.with(requireContext())
                .load(args.getInstructor().getUser().getProfileImg())
                .placeholder(R.drawable.baseline_account_circle_24)
                .into(binding.teacherProfileImage);

        FirebaseDataBaseClient.getInstance()
                .getConnectionData(args.getInstructor().getUser().getUID())
                .addOnSuccessListener(connection -> {
                    ConnectionModel connectionModel = connection.getValue(ConnectionModel.class);
                    if (connectionModel != null) {
                        if (connectionModel.isCurrentlyConnected()) {
                            setAsConnected();
                        } else {
                            setAsNotConnected();

                        }
                    } else {
                        setAsNotConnected();
                    }
                });

        FirebaseDataBaseClient.getInstance().getConnectionsUser(args.getInstructor().getUser().getUID())
                .addOnSuccessListener(dataSnapshot -> binding.teacherProfileNumOfStudent.setText(Long.toString(dataSnapshot.getChildrenCount())));


        viewModel.getOpinions(args.getInstructor().getUser().getUID()).addOnSuccessListener(snapshot -> {

            ArrayList<OpinionModel> opinions = new ArrayList<>();
            snapshot.getChildren().forEach(connectionDataSnapshot -> opinions.add(connectionDataSnapshot.getValue(OpinionModel.class)));
            Timber.tag(TAG).d("setUpViews: %s", opinions.size());
            adapter.changeData(opinions);
        });
    }

    private void setAsConnected() {
        binding.removeContainer.setVisibility(View.VISIBLE);
        binding.chat.setImageResource(R.drawable.favorite_icon);
        binding.video.setImageResource(R.drawable.videocall_icon);


    }

    private void setAsNotConnected() {
        binding.removeContainer.setVisibility(View.GONE);

        binding.chat.setImageResource(R.drawable.disable_favorite);
        binding.chat.setClickable(false);
        binding.chat.setEnabled(false);
        binding.chat.setFocusable(false);


        binding.video.setImageResource(R.drawable.disable_video_call);
        binding.video.setClickable(false);
        binding.video.setEnabled(false);
        binding.video.setFocusable(false);

        binding.teacherProfileIcSend.setClickable(false);
        binding.teacherProfileIcSend.setClickable(false);
        binding.teacherProfileIcSend.setEnabled(false);
        binding.teacherProfileIcSend.setFocusable(false);

    }

}








