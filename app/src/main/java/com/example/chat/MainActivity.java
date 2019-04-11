package com.example.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMessageList();
        createRecyclerView();
    }

    private void createMessageList() {
        messageList = new ArrayList<>();
        messageList.add(new TextModel("arafat"));
        messageList.add(new TextModel("kamal"));
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
