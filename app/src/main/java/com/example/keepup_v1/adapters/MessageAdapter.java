package com.example.keepup_v1.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepup_v1.R;
import com.example.keepup_v1.funcs.CommentData;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.LinearViewHolder>{

    private Context mContext;
    private List<CommentData> mData = new ArrayList<>();

    public MessageAdapter(Context context, List<CommentData> data){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public MessageAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.LinearViewHolder holder, int position) {
        CommentData mInfo = mData.get(position);
        holder.tv_user.setText(mInfo.username);
        holder.tv_comment.setText(mInfo.comment);
    }

    @Override
    public int getItemCount() {
        return  mData.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_user;
        private TextView tv_comment;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user = itemView.findViewById(R.id.tv_comment_user);
            tv_comment = itemView.findViewById(R.id.tv_comment_comment);
        }
    }
}
