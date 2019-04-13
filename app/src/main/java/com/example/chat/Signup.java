package com.example.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    //constant
    private static final String TAG = "Signup";

    //member variable
    private EditText username, email, password;
    private Button signupBtn;
    private FirebaseAuth mAuth;
    private String mUsername = "", mEmail = "", mPassword = "";
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        signupBtn = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        //myRef = FirebaseDatabase.getInstance().getReference("User");

        signup();

    }

    private void signup() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUsername = username.getText().toString();
                mEmail = email.getText().toString();
                mPassword = password.getText().toString();


                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Log.d(TAG, "onComplete: signup success");
                                    Toast.makeText(Signup.this, "Registaion is on progress!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Signup.this, Signin.class));

                                    // save information in database
                                    Map<String,Object> taskMap = new HashMap<>();
                                    taskMap.put("username", mUsername);
                                    taskMap.put("email", mEmail);
                                    taskMap.put("password", mPassword);
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    myRef = FirebaseDatabase.getInstance().getReference("user").child(user.getUid());
                                    myRef.updateChildren(taskMap);
                                } else {
                                    Log.w(TAG, "onComplete: Authentication failed", task.getException());
                                    Toast.makeText(Signup.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
