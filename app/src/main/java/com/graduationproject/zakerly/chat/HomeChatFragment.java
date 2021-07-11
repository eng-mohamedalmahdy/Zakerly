package com.graduationproject.zakerly.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.ConnectionModel;
import com.graduationproject.zakerly.core.models.ItemChat;
import com.graduationproject.zakerly.core.models.Message;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.databinding.FragmentHomeChatBinding;

import java.util.ArrayList;
import java.util.Objects;

public class HomeChatFragment extends Fragment {
    FragmentHomeChatBinding binding;
    ImageView mImageBack;
    EditText mEtSearch;
    RecyclerView mRecyclerView;
    private HomeChatAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeChatBinding.inflate(inflater, container, false);
        ((MainActivity) requireActivity()).setNavigationVisibility(false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setUpList();
    }

    private void setUpList() {
        adapter = new HomeChatAdapter(this);
        mRecyclerView.setAdapter(adapter);
        FirebaseDataBaseClient.getInstance()
                .getConnectionsForCurrentUser().addOnSuccessListener(dataSnapshot -> {
            ArrayList<ItemChat> users = new ArrayList<>();
            adapter.changeData(users);
            dataSnapshot.getChildren().forEach(connection -> {
                ConnectionModel connectionModel = connection.getValue(ConnectionModel.class);
                String connectedUserUid = connection.getKey();
                if (Objects.requireNonNull(connectionModel).isCurrentlyConnected()) {
                    FirebaseDataBaseClient
                            .getInstance()
                            .getUserByUid(connectedUserUid)
                            .addOnSuccessListener(connectedUser -> {
                                User user = connectedUser.child("user").getValue(User.class);
                                if (user == null) return;
                                String combinedUid = FirebaseDataBaseClient.getInstance().getCombinedUid(user.getUID());
                                ItemChat itemChat = new ItemChat(user.getProfileImg(),
                                        (user.getFirstName() + " " + user.getLastName()),
                                        getString(R.string.no_messages), -1, user.getUID());
                                users.add(itemChat);
                                adapter.notifyDataSetChanged();

                                FirebaseDataBaseClient
                                        .getInstance()
                                        .getLastMessageWithUser(combinedUid)
                                        .addOnSuccessListener(messageSnapshot -> {
                                            Message message = messageSnapshot.getValue(Message.class);
                                            long time = message == null ? -1 : message.getTimeOfSendMsg();
                                            String messageBody = message == null ? getString(R.string.no_messages) : message.getMessage();
                                            itemChat.setMsgTime(time);
                                            itemChat.setLastMsg(messageBody);
                                            adapter.notifyDataSetChanged();
                                        });
                            });
                }
            });
        });
    }

    private void initViews() {
        mRecyclerView = binding.homeChatRecyclerView;
        mImageBack = binding.homeChatBack;
        mEtSearch = binding.homeChatSearch;
        binding.homeChatSearch.addTextChangedListener(new TextWatcher() {
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

        binding.homeChatBack.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

    }
}