package com.graduationproject.zakerly.navigation.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.Message;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.util.Util;
import com.graduationproject.zakerly.databinding.FragmentChatBinding;
import com.graduationproject.zakerly.databinding.FragmentHomeChatBinding;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ChatFragment extends Fragment {

    FragmentChatBinding binding ;
    ImageView mImageBack, mImageVideoCall , mImageAdd,mImageSend;
    CircleImageView mImageProfile;
    TextView mName;
    EditText mEditTxtTypeMsg;
    String receiverName,receiverImage,receiverUID,senderUID;
    String senderRoom,receiverRoom;
    ArrayList<Message> messages;
    public static String rImage;
    public static String sImage;
    DatabaseReference reference , chatReference;
    FirebaseAuth auth;
    FirebaseDatabase database;
    RecyclerView mRecyclerView;
    MessagesAdapter adapter;
    LinearLayoutManager linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater,container,false);
        database= FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        senderUID =auth.getUid();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setUpValues();
        initListener();


        reference=database.getReference().child("").child(senderUID);
        chatReference = database.getReference().child("chats").child(senderRoom).child("messages");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sImage =snapshot.child("").getValue().toString();
                rImage= receiverImage;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initListener() {
        binding.chatSend.setOnClickListener(view -> {
            String message = binding.chatEtSend.getText().toString();
            if (message.isEmpty()){
                Toasty.error(requireContext(),"Please Enter Message . . ",Toasty.LENGTH_LONG).show();
                return;
            }
            binding.chatEtSend.setText("");
            Date date = new Date();
            Message messages = new Message(senderUID, date.getTime(),message);
            database = FirebaseDatabase.getInstance();
            database.getReference()
                    .child("chats")
                    .child(senderRoom)
                    .child("messages")
                    .push()
                    .setValue(messages)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            database.getReference()
                                    .child("chats")
                                    .child(receiverRoom)
                                    .child("messages")
                                    .push()
                                    .setValue(messages)
                                    .addOnCompleteListener(task1 -> {

                                    });
                        }
                    });

        });
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Message message = dataSnapshot.getValue(Message.class);
                    messages.add(message);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toasty.error(requireContext(),error.getMessage());
            }
        });
        binding.chatVideoCall.setOnClickListener(view -> {


        });
    }

    private void setUpValues() {
        receiverName = getActivity().getIntent().getStringExtra("name");
        receiverImage= getActivity().getIntent().getStringExtra("image");
        receiverUID = getActivity().getIntent().getStringExtra("uid");
        senderRoom = senderUID + receiverUID;
        receiverRoom = receiverUID+senderUID;
        Glide.with(requireContext()).load(receiverImage).error(R.drawable.baseline_account_circle_24).into(mImageProfile);
        mName.setText(receiverName);
        linearLayout.setStackFromEnd(true);
        adapter=new MessagesAdapter(getContext(),messages);
        linearLayout = new LinearLayoutManager(getContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void initViews() {
        mImageBack =binding.chatBack;
        mImageVideoCall = binding.chatVideoCall;
        mImageAdd = binding.chatAdd;
        mImageSend= binding.chatSend;
        mImageProfile = binding.chatImage;
        mName = binding.chatName;
        mEditTxtTypeMsg = binding.chatEtSend;
        messages = new ArrayList<>();
        mRecyclerView = binding.chatMessageRecyclerView;
    }
}