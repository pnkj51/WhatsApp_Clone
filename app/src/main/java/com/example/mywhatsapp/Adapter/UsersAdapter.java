package com.example.mywhatsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywhatsapp.ChatDetailActivity;
import com.example.mywhatsapp.Models.Users;
import com.example.mywhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list,Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UsersAdapter.ViewHolder holder, int position) {

        Users users = list.get(position);

        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.ic_launcher_foreground).into(holder.image);
        holder.userName.setText(users.getUsername());

        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid()+users.getUserId())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                holder.lastMessage.setText(snapshot1.child("msg").getValue(String.class));

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context, ChatDetailActivity.class);
                i.putExtra("userId",users.getUserId());
                i.putExtra("profilePic",users.getProfilepic());
                i.putExtra("userName",users.getUsername());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        //Log.d("TAG", String.valueOf(list.size()));
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView userName, lastMessage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userName);
            lastMessage = itemView.findViewById(R.id.lastMessage);

        }
    }
}
