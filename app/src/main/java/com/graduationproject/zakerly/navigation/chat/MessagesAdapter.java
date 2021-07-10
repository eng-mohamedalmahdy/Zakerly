package com.graduationproject.zakerly.navigation.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.models.Message;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.graduationproject.zakerly.navigation.chat.ChatFragment.rImage;

public class MessagesAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Message> messages;
    int SEND_ITEM=1;
    int RECEIVED_ITEM=2;

    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==1){
            View v= LayoutInflater.from(context).inflate(R.layout.send_message_item,parent,false);
            return new SenderViewHolder(v);
        }else{
            View v= LayoutInflater.from(context).inflate(R.layout.receiver_message_item,parent,false);
            return new ReceiverViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder.getClass()==SenderViewHolder.class){
            SenderViewHolder viewHolder = (SenderViewHolder) holder ;
            viewHolder.messageSend.setText(message.getMessage());
        }else{
           ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.messageReceived.setText(message.getMessage());
            Glide.with(holder.itemView).load(rImage).error(R.drawable.baseline_account_circle_24)
                    .into(((ReceiverViewHolder) holder).messageReceivedImage);
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderID())){
            return SEND_ITEM;
        }else{
            return RECEIVED_ITEM;
        }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView messageSend;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            messageSend = itemView.findViewById(R.id.message_send);
        }
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder{
        CircleImageView messageReceivedImage;
        TextView messageReceived;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            messageReceivedImage = itemView.findViewById(R.id.message_received_image);
            messageReceived = itemView.findViewById(R.id.message_received);
        }
    }

}
