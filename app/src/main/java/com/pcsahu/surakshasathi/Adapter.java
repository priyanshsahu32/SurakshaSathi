package com.pcsahu.surakshasathi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    List<Message> messageList;
    public Adapter(List<Message> messageList){
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from( parent.getContext() ).inflate( R.layout.chat_item,null );
        MyViewHolder myViewHolder = new MyViewHolder( chatview );
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message.getSendBy().equals( Message.send_by_me )){
            holder.leftchatview.setVisibility( View.GONE );
            holder.rightchatview.setVisibility( View.VISIBLE );
            holder.righttextview.setText( message.getMessage() );

        }else{
            holder.leftchatview.setVisibility( View.VISIBLE );
            holder.rightchatview.setVisibility( View.GONE );
            holder.lefttextview.setText( message.getMessage() );
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftchatview,rightchatview;
        TextView lefttextview,righttextview;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            leftchatview = itemView.findViewById( R.id.left_chatview );
            lefttextview  = itemView.findViewById( R.id.left_textview );
            rightchatview = itemView.findViewById( R.id.right_chatview );
            righttextview = itemView.findViewById( R.id.right_textview );
        }
    }
}
