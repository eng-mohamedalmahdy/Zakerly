package com.graduationproject.zakerly.chat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.ItemChat;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;
import com.graduationproject.zakerly.core.util.Util;

import java.util.ArrayList;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeChatAdapter extends RecyclerView.Adapter<HomeChatAdapter.ViewHolder> {

    ArrayList<ItemChat> itemChats;
    ArrayList<ItemChat> itemChatsOriginal;
    Fragment fragment;

    public HomeChatAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemChat itemChat = itemChats.get(position);
        holder.mName.setText(itemChat.getName());
        holder.mLastMsg.setText(itemChat.getLastMsg());
        holder.mDate.setVisibility(itemChat.getMsgTime() == -1 ? View.GONE : View.VISIBLE);
        holder.mDate.setText(Util.getTimeFromStamp(itemChat.getMsgTime()));
        Glide.with(holder.itemView).load(itemChat.getImage()).error(R.drawable.baseline_account_circle_24).into(holder.mImage);
        holder.itemView.setOnClickListener(view -> {
            FirebaseDataBaseClient.getInstance().getUserByUid(itemChat.getUid()).addOnSuccessListener(dataSnapshot -> {
                User user = dataSnapshot.child("user").getValue(User.class);
                NavHostFragment.findNavController(fragment)
                        .navigate(HomeChatFragmentDirections.actionHomeChatFragmentToChatFragment(user));
            });
        });
    }

    public void changeData(ArrayList<ItemChat> itemChats) {
        this.itemChats = itemChats;
        this.itemChatsOriginal = itemChats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemChats == null ? 0 : itemChats.size();
    }

    public void filter(String input) {
        if (input == null || input.isEmpty()) {
            itemChats = itemChatsOriginal;
            notifyDataSetChanged();
        } else {
            itemChats = itemChats
                    .stream()
                    .filter(itemChatsItem ->
                            itemChatsItem.getName().contains(input) ||
                                    itemChatsItem.getLastMsg().contains(input))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mImage;
        TextView mName, mLastMsg, mDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.user_image);
            mName = itemView.findViewById(R.id.notification_sender);
            mLastMsg = itemView.findViewById(R.id.notification_body);
            mDate = itemView.findViewById(R.id.notification_time);
        }
    }
}




