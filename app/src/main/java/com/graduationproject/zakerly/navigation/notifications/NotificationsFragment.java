package com.graduationproject.zakerly.navigation.notifications;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentNotificationsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment {
    FragmentNotificationsBinding binding;

    RecyclerView notificationsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationsList = binding.notificationsList;
        NotificationsAdapter adapter = new NotificationsAdapter(this);
        notificationsList.setAdapter(adapter);
        FirebaseDataBaseClient
                .getInstance()
                .getAllNotifications()
                .addOnSuccessListener(snapshot -> {
                    ArrayList<NotificationData> data = new ArrayList<>();
                    snapshot.getChildren().forEach(notification -> data.add(notification.getValue(NotificationData.class)));
                    adapter.setNotificationData(data);
                });

        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
