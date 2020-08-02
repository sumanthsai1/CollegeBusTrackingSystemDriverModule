package com.example.test_covid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class profile extends AppCompatActivity {
    private TextView pname;
    private TextView pemail;
    private String stat;
    private TextView statoff,staton;
    FirebaseDatabase firebaseDatabase;
    private Button Reset;
    FirebaseAuth auth;
    private DatabaseReference databaseReference;

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
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(auth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                profilee profilee = datasnapshot.getValue(profilee.class);
                assert profilee != null;
                pname.setText(profilee.getUsername());
                pemail.setText(profilee.getEmail());
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
    }
    public void Reset(View view) {
        startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
    }
}






