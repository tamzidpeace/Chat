package com.example.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //constant
    private static final String TAG = "MainActivity";

    // memeber variable
    private RecyclerView recyclerView;
    private MyAdapter mAapater;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TextModel> messageList;
    private DatabaseReference myRef;
    private String message;
    private EditText textField;
    private Button sendBtn;
    private long childNum;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = FirebaseDatabase.getInstance().getReference("abc");
        textField = findViewById(R.id.message_field);
        sendBtn = findViewById(R.id.send_btn);
        mAuth = FirebaseAuth.getInstance();

        createMessageList();
        createRecyclerView();
        sendMessage();
    }

    private void createMessageList() {

        messageList = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                messageList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    message = snapshot.getValue(String.class);
                    Log.d(TAG, "onDataChange: " + message);
                    messageList.add(new TextModel(message));
                    recyclerView.setAdapter(mAapater);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAapater = new MyAdapter(messageList);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    private void sendMessage() {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childNum = messageList.size() + 1;
                String num = String.valueOf(childNum);
                String message = textField.getText().toString();
                myRef.child(num).setValue(message);
                textField.setText("");
                Log.d(TAG, "onClick: " + num);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_signout) {
            Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, Signin.class));
        }

        return true;
    }
}
