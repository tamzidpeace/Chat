package com.example.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //memeber variable
    private ArrayList<TextModel> messageList;
    //constant
    private static final String TAG = "MyAdapter";

    MyAdapter(ArrayList<TextModel> messageList) {
        this.messageList = messageList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView message, from;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            from = itemView.findViewById(R.id.from);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_list, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TextModel textModel = messageList.get(i);
        myViewHolder.message.setText(textModel.getMessage());
        myViewHolder.from.setText(textModel.getFrom());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + messageList.size());
        return messageList.size();
    }

}
