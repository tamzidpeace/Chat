package com.example.chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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

        /*messageList.add(new TextModel("arafat"));
        messageList.add(new TextModel("kamal"));*/
        myRef = FirebaseDatabase.getInstance().getReference();
        //myRef.child("message").setValue("tamzid");
        messageList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                message = (String) dataSnapshot.child("abc").child("2").getValue();
                //message =  dataSnapshot.child("message").getChildren().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                if (message != null) {
                    messageList.add(new TextModel(message));
                    Log.d(TAG, "onDataChange: " + message);
                }
                long num = dataSnapshot.child("abc").getChildrenCount();
                Log.d(TAG, "onDataChange: " + num);
                /*for(DataSnapshot snapshot : dataSnapshot.child("abc").child("2").getChildren()) {
                   TextModel model = dataSnapshot.getValue(TextModel.class);
                   messageList.add(model);
                }*/
                for (int i = 2; i <=num+1; i++) {
                    String no = String.valueOf(i);
                    String message = (String) dataSnapshot.child("abc").child(no).getValue();
                    Log.d(TAG, "onDataChange: " + message);
                    messageList.add(new TextModel(message));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.toException());
            }
        });

        //messageList.add(new TextModel(message));
    }

    private void createRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAapater = new MyAdapter(messageList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAapater);
    }
}
