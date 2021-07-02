package com.graduationproject.zakerly.navigation.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.cache.Realm.RealmQueries;
import com.graduationproject.zakerly.core.constants.UserTypes;
import com.graduationproject.zakerly.core.models.ConnectionModel;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.util.Util;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    ArrayList<NotificationData> notificationData;
    ArrayList<NotificationData> originalData;
    Fragment fragment;

    public NotificationsAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setNotificationData(ArrayList<NotificationData> notificationData) {
        this.notificationData = notificationData;
        this.originalData = notificationData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notificationData.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationData == null ? 0 : notificationData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView senderName, body, time;
        private ImageView senderImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.notification_sender);
            body = itemView.findViewById(R.id.notification_body);
            time = itemView.findViewById(R.id.notification_time);
            senderImage = itemView.findViewById(R.id.user_image);
        }

        public void bind(NotificationData notificationData) {

            senderName.setText(notificationData.getSenderName());
            body.setText(notificationData.getTitle());
            time.setText(Util.getTimeFromStamp(notificationData.getNotificationTime()));
            Glide.with(itemView.getContext())
                    .load(notificationData.getSenderImageUrl())
                    .error(R.drawable.no_user)
                    .centerCrop()
                    .into(senderImage);
            itemView.setOnClickListener(v -> {
                FirebaseUser firebaseUser = FireBaseAuthenticationClient.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    String uid = firebaseUser.getUid();
                    User user = new RealmQueries().getUser(uid);
                    if (user.getType().equals(UserTypes.TYPE_INSTRUCTOR)) {
                        FirebaseDataBaseClient.getInstance()
                                .getConnectionData(notificationData.getSenderUid()).addOnSuccessListener(snapshot -> {
                            NavHostFragment.findNavController(fragment)
                                    .navigate(NotificationsFragmentDirections
                                            .actionNotificationsFragmentToAcceptNotificationsDialog(notificationData, snapshot.getValue(ConnectionModel.class)));
                        });
                    }
                }
            });

        }
    }

    public void filter(String input) {
        if (input == null || input.isEmpty()) {
            notificationData = originalData;
            notifyDataSetChanged();
        } else {
            notificationData = notificationData
                    .stream()
                    .filter(notificationDataItem ->
                            notificationDataItem.getBody().contains(input) ||
                                    notificationDataItem.getSenderName().contains(input) ||
                                    notificationDataItem.getNotificationType().toString().contains(input) ||
                                    Integer.toString(notificationDataItem.getNeededHours()).contains(input) ||
                                    notificationDataItem.getTitle().contains(input))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        notifyDataSetChanged();
    }
}
