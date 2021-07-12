package com.graduationproject.zakerly.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.MeetingAttendeesTypes;
import com.graduationproject.zakerly.core.constants.NotificationType;
import com.graduationproject.zakerly.core.models.Message;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.PushNotification;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.util.Util;
import com.graduationproject.zakerly.databinding.FragmentChatBinding;
import com.graduationproject.zakerly.meetings.meetingshome.ContactsListFragmentDirections;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import timber.log.Timber;

public class ChatFragment extends Fragment {
    private static final String TAG = "ChatFragment";
    FragmentChatBinding binding;
    ChatFragmentArgs args;
    ImageView mImageBack, mImageVideoCall, mImageAdd, mImageSend;
    CircleImageView mImageProfile;
    TextView mName;
    EditText mEditTxtTypeMsg;
    ArrayList<Message> messages;
    public static String rImage;
    public static String sImage;
    RecyclerView mRecyclerView;
    MessagesAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = ChatFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        ((MainActivity) requireActivity()).setNavigationVisibility(false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setUpValues();
        initListener();

        FirebaseDataBaseClient
                .getInstance()
                .getChatWithUser(args.getUser().getUID())
                .addOnSuccessListener(messagesDataSnapshot -> {
                    messagesDataSnapshot.getChildren().forEach(messageSnapshot -> messages.add(messageSnapshot.getValue(Message.class)));
                    adapter.setMessages(messages);
                    mRecyclerView.scrollToPosition(messages.size() - 1);

                });

    }

    private void initListener() {
        binding.chatSend.setOnClickListener(view -> {
            String messageBody = binding.chatEtSend.getText().toString();
            if (messageBody.isEmpty()) {
                Toasty.error(requireContext(), "Please Enter Message . . ", Toasty.LENGTH_LONG).show();
                return;
            }
            binding.chatEtSend.setText("");
            String messageId = FirebaseDataBaseClient.getInstance().getRandomKey();
            Message message = new Message(messageId,
                    FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid(),
                    args.getUser().getUID(),
                    FireBaseAuthenticationClient.getInstance().getCurrentUser().getDisplayName(),
                    args.getUser().getFirstName(),
                    System.currentTimeMillis(),
                    messageBody);

            FirebaseDataBaseClient.getInstance().sendMessage(message).addOnSuccessListener(unused ->
                    Util.sendNotification(new PushNotification(message, args.getUser().getNotificationToken()))); });


        FirebaseDataBaseClient.
                getInstance().
                getChat(args.getUser().getUID()).
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        Message message = snapshot.getValue(Message.class);
                        if (messages.isEmpty() || messages.get(messages.size() - 1).getTimeOfSendMsg() != message.getTimeOfSendMsg()) {
                            Log.d(TAG, "onChildAdded: " + message);
                            messages.add(message);
                            adapter.setMessages(messages);
                            mRecyclerView.scrollToPosition(messages.size() - 1);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });
        binding.chatVideoCall.setOnClickListener(view -> {

            User user = args.getUser();
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
            NavHostFragment.findNavController(this).navigate(ContactsListFragmentDirections.actionVideoContactsListFragmentToMeetingRequestingFragment(notification, MeetingAttendeesTypes.SENDER));

        });
        binding.chatBack.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());


    }

    private void setUpValues() {

        Glide.with(requireContext()).load(args.getUser().getProfileImg()).error(R.drawable.baseline_account_circle_24).into(mImageProfile);
        mName.setText((args.getUser().getFirstName()));
        adapter = new MessagesAdapter();
        messages = new ArrayList<>();
        mRecyclerView.setAdapter(adapter);
        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).setStackFromEnd(true);

    }

    private void initViews() {
        mImageBack = binding.chatBack;
        mImageVideoCall = binding.chatVideoCall;
        mImageAdd = binding.chatAdd;
        mImageSend = binding.chatSend;
        mImageProfile = binding.chatImage;
        mName = binding.chatName;
        mEditTxtTypeMsg = binding.chatEtSend;
        mRecyclerView = binding.chatMessageRecyclerView;
    }
}