package com.example.cbts1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cbts1.QueryReservation.OnlineReservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    ImageView menu;
    String poststr;
    String g1="driver";
    String g2="passenger";
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu=findViewById(R.id.menu);
        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(auth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profilee profilee = snapshot.getValue(profilee.class);
                assert profilee != null;
                poststr=profilee.getPost();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.inflate(R.menu.menu);
                popup.show();

            }
        });
    }
    public void postlocation(View view) {
        if(poststr.toLowerCase().trim().equals(g1.toLowerCase().trim())){
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }
        else {
            Toast.makeText(MainActivity.this, "You are not a DRIVER Please choose Correctly", Toast.LENGTH_LONG).show();
        }
    }

    public void getlocation(View view) {
        if(poststr.toLowerCase().trim().equals(g2.toLowerCase().trim())) {
            startActivity(new Intent(getApplicationContext(), MapsActivity1.class));
        }
        else{
            Toast.makeText(MainActivity.this, "You are not a PASSENGER Please choose Correctly", Toast.LENGTH_LONG).show();

        }
    }
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Profile:
                startActivity(new Intent(MainActivity.this, profile.class));
                return true;

            case R.id.Support:
                startActivity(new Intent(MainActivity.this, OnlineReservation.class));
                return true;

            case R.id.aboutus:
                openaboutapp();
                return true;
            case R.id.Contact:

                openaboutdialog();
                return true;
        }
        return true;
    }
    private void openaboutapp() {
        AboutApp_dialog aboutApp_dialog=new AboutApp_dialog();
        aboutApp_dialog.show(getSupportFragmentManager(),"example about app");
    }

    private void openaboutdialog() {
        About_dialog about_dialog=new About_dialog();
        about_dialog.show(getSupportFragmentManager(),"example dialog");
    }



}

