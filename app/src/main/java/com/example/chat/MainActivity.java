package com.example.chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMessageList();
        createRecyclerView();
    }

    private void createMessageList() {

        messageList = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("abc");

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
}
