package com.example.chat;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    //constant
    private static final String TAG = "Signin";

    //member variable
    private EditText email, password;
    private Button signinBtn, signupBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signinBtn = findViewById(R.id.signin_btn);
        signupBtn = findViewById(R.id.signup_btn);
        mAuth = FirebaseAuth.getInstance();

        signup();
        signin();
    }

    private void signin() {

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();
                Toast.makeText(Signin.this, "Signin is on progress!", Toast.LENGTH_SHORT).show();
                mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Signin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
            }
        });
    }


    private void signup() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, Signup.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "onStart: " + currentUser);
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.d(TAG, "updateUI: " + currentUser);
        if (currentUser != null) {
            startActivity(new Intent(Signin.this, MainActivity.class));
            Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        }
    }
}
