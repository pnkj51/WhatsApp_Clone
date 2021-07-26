package com.example.mywhatsapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywhatsapp.Models.MessageModel;
import com.example.mywhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{



    ArrayList<MessageModel> messageModels;
    Context context;
    int SENDER_VIEW_TYPE =1;
    int RECEIVER_VIEW_TYPE =2;

    String receiverId ;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String receiverId) {
        this.messageModels = messageModels;
        this.context = context;
        this.receiverId = receiverId;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        if(viewType==SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){

            return SENDER_VIEW_TYPE;
        }
        else {
           return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete this message")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid()+receiverId;
                                database.getReference().child("chats").child(senderRoom)
                                        .child(messageModel.getMsgId()).setValue(null);
                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return false;
            }
        });


        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMsg());
        }
        else{
            ((ReceiverViewHolder)holder).receiverMsg.setText(messageModel.getMsg());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsg , receiverTime;


        public ReceiverViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.msg_receiver);
            receiverTime = itemView.findViewById(R.id.time_receiver);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg , senderTime;


        public SenderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.msg_sender);
            senderTime = itemView.findViewById(R.id.time_sender);
        }
    }
}
