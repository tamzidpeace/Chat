package com.example.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<TextModel> messageList;

    MyAdapter(List<TextModel> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.message_list, viewGroup, false);
        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        TextModel textModel = messageList.get(i);
        myViewHolder.message.setText(textModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView message;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }
}
