package com.graduationproject.zakerly.navigation.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.ItemChat;
import com.graduationproject.zakerly.core.util.Util;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeChatAdapter extends RecyclerView.Adapter<HomeChatAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemChat> itemChats;

    public HomeChatAdapter(Context context, ArrayList<ItemChat> itemChats) {
        this.context = context;
        this.itemChats = itemChats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemChat itemChat = itemChats.get(position);
        holder.mName.setText(itemChat.getName());
        holder.mLastMsg.setText(itemChat.getLastMsg());
        holder.mDate.setText(Util.getTimeFromStamp(itemChat.getMsgTime()));
        Glide.with(holder.itemView).load(itemChat.getImage()).error(R.drawable.baseline_account_circle_24).into(holder.mImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,HomeChatFragment.class);
                intent.putExtra("name",itemChat.getName());
                intent.putExtra("image",itemChat.getImage());
                intent.putExtra("uid",itemChat.getUid());
                context.startActivity(intent);

            }
        });
    }
public void changeData(ArrayList<ItemChat> itemChats){
        this.itemChats = itemChats;
        notifyDataSetChanged();
}
    @Override
    public int getItemCount() {
        return itemChats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView mImage;
        TextView mName,mLastMsg,mDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.user_image);
            mName = itemView.findViewById(R.id.notification_sender);
            mLastMsg = itemView.findViewById(R.id.notification_body);
            mDate = itemView.findViewById(R.id.notification_time);
        }
    }
}
