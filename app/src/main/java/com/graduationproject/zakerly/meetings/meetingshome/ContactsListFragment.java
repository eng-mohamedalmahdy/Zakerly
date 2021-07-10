package com.graduationproject.zakerly.meetings.meetingshome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.core.models.ConnectionModel;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentVideoContactsListBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;


public class ContactsListFragment extends Fragment {

    private FragmentVideoContactsListBinding binding;
    private ContactsListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoContactsListBinding.inflate(inflater, container, false);
        ((MainActivity) requireActivity()).setNavigationVisibility(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ContactsListAdapter(this);

        binding.contactsList.setAdapter(adapter);

        FirebaseDataBaseClient.getInstance()
                .getConnectionsForCurrentUser().addOnSuccessListener(dataSnapshot -> {
            ArrayList<User> users = new ArrayList<>();
            adapter.setUsers(users);
            dataSnapshot.getChildren().forEach(connection -> {
                ConnectionModel connectionModel = connection.getValue(ConnectionModel.class);
                String connectedUserUid = connection.getKey();
                if (Objects.requireNonNull(connectionModel).isCurrentlyConnected()) {
                    FirebaseDataBaseClient
                            .getInstance()
                            .getUserByUid(connectedUserUid)
                            .addOnSuccessListener(connectedUser -> {
                                users.add(connectedUser.child("user").getValue(User.class));
                                adapter.notifyDataSetChanged();
                            });
                }
            });
        });

    }
}