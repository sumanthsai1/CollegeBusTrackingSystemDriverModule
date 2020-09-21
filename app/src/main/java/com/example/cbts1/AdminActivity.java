package com.example.cbts1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    MaterialEditText username, email, password,post;
    Button btn_register;
    String poststr1="driver1";
    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        post=findViewById(R.id.Post);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_post=post.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_post)){
                    Toast.makeText(AdminActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6 ){
                    Toast.makeText(AdminActivity.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else if(!txt_post.toLowerCase().trim().equals(poststr1.toLowerCase().trim())){
                    Toast.makeText(AdminActivity.this, "Post Should be driver1", Toast.LENGTH_SHORT).show();

                }
                else {
                    register(txt_username, txt_email, txt_password,txt_post);
                }
            }
        });
    }

    private void register(final String username, final String email, final String password, final String post){

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("email",email);
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "status");
                            hashMap.put("Password", password);
                            hashMap.put("Post","Driver1");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(AdminActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

