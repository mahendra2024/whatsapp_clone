package com.example.waclone;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.example.waclone.Adapter.chatAdapter;
import com.example.waclone.databinding.ActivityChatDetailsBinding;
import com.example.waclone.models.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatDetails extends AppCompatActivity {

    ActivityChatDetailsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getUid();
        String recieveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar).into(binding.profileImage);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chatDetails.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final chatAdapter chatAdapt = new chatAdapter(messageModels , this , recieveId);

        binding.chatRecyclerView.setAdapter(chatAdapt);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);



        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId ;


        database.getReference().child("chats").child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        messageModels.clear();
                        for(DataSnapshot snapshot1: snapshot.getChildren()){
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());
                            messageModels.add(model);
                        }
                        chatAdapt.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.enterMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId,message);
                if(message.length() == 0){
                    Toast.makeText(chatDetails.this, "Enter the message", Toast.LENGTH_SHORT).show();
                }
                else {
                    model.setTimestamp(new Date().getTime());
                    binding.enterMessage.setText("");

                    database.getReference().child("chats").child(senderRoom).push().setValue(model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("chats").child(receiverRoom).push().setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                }
                            });
                }
            }
        });

    }
}