package com.graduationproject.zakerly.meetings.meetingshome;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.constants.MeetingAttendeesTypes;
import com.graduationproject.zakerly.core.constants.NotificationType;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.PushNotification;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.network.retrofit.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    List<User> users;
    Fragment fragment;
    private static final String TAG = "ContactsListAdapter";


    public ContactsListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_meeting_contacts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView firstLetter;
        private final TextView contactName;
        private final ImageButton voiceCall;
        private final ImageButton videoCall;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            firstLetter = itemView.findViewById(R.id.user_first_letter);
            contactName = itemView.findViewById(R.id.contact_name);
            voiceCall = itemView.findViewById(R.id.audio_call);
            videoCall = itemView.findViewById(R.id.video_call);
        }

        public void bind(User user) {
            if (user == null) return;
            firstLetter.setText(Character.toString(user.getFirstName().charAt(0)));
            contactName.setText((user.getFirstName() + " " + user.getLastName()));

            String notificationId = FirebaseDataBaseClient.getInstance().getRandomKey();
            NotificationData notification = new NotificationData(System.currentTimeMillis(),
                    notificationId, "Voice meeting request",
                    "Voice meeting request",
                    NotificationType.MESSAGE,
                    FireBaseAuthenticationClient.getInstance().getCurrentUser().getDisplayName(),
                    user.getFirstName() + " " + user.getLastName(),
                    FireBaseAuthenticationClient.getInstance().getCurrentUser().getUid(),
                    user.getUID(),
                    "",
                    0);
            voiceCall.setOnClickListener(v -> {
                notification.setNotificationType(NotificationType.VOICE_MEETING);
                PushNotification p = new PushNotification(notification, user.getNotificationToken());
                sendNotification(p);
            });
            videoCall.setOnClickListener(v -> {
                notification.setNotificationType(NotificationType.VIDEO_MEETING);
                PushNotification p = new PushNotification(notification, user.getNotificationToken());
                sendNotification(p);
            });
        }


        private void sendNotification(PushNotification notification) {

            Disposable d = RetrofitClient.getApi()
                    .postNotification(notification)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBodyResponse -> {
                                if (responseBodyResponse.isSuccessful()) {
                                    NavHostFragment.findNavController(fragment).navigate(
                                            ContactsListFragmentDirections.actionVideoContactsListFragmentToMeetingRequestingFragment(notification.getData(), MeetingAttendeesTypes.SENDER)
                                    );
                                }
                            },
                            throwable -> Log.d(TAG, "sendNotification: error" + throwable.getMessage()));
        }
    }
}
