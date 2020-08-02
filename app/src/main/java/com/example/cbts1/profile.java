package com.example.cbts1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class profile extends AppCompatActivity {
    private TextView pname,post;
    private TextView pemail;
    private String stat;
    private TextView statoff,staton;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    private DatabaseReference databaseReference;
    Button bt1;

    public void status(String status){
        databaseReference = firebaseDatabase.getReference("Users").child(auth.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);
        databaseReference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pname = findViewById(R.id.profileName);
        pemail = findViewById(R.id.profileEmail);
        staton=findViewById(R.id.staton);
        statoff=findViewById(R.id.statoff);
        post=findViewById(R.id.postid);
        auth=FirebaseAuth.getInstance();
        bt1=findViewById(R.id.button);
        firebaseDatabase=FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Users").child(auth.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                profilee profilee = datasnapshot.getValue(profilee.class);
                assert profilee != null;
                pname.setText(profilee.getUsername());
                pemail.setText(profilee.getEmail());
                post.setText(profilee.getPost());
                stat=profilee.getStatus();
                if(stat == "online")
                {
                    staton.setVisibility(View.VISIBLE);
                    staton.setText(stat);
                    statoff.setVisibility(View.GONE);
                }
                else if(stat=="offline")
                {
                    statoff.setVisibility(View.VISIBLE);
                    statoff.setText(stat);
                    staton.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this, LoginActivity.class));

            }
        });
    }
    public void Reset(View view) {
        startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
    }
}






