package com.example.keepup_v1.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepup_v1.R;
import com.example.keepup_v1.funcs.CommentData;
import com.example.keepup_v1.funcs.FriendData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.LinearViewHolder> {
    private Context mContext;
    private List<FriendData> mData = new ArrayList<>();
    private OnClickListener mListener;

    public FollowAdapter(Context context, List<FriendData> data, OnClickListener listener){
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public FollowAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAdapter.LinearViewHolder holder, int position) {
        FriendData mInfo = mData.get(position);
        holder.tv_user.setText(mInfo.mUsername);
        holder.iv_avatar.setImageBitmap(bitmap(mInfo.mAvatar));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(mInfo.mId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_avatar;
        private TextView tv_user;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user = itemView.findViewById(R.id.tv_follow_username);
            iv_avatar = itemView.findViewById(R.id.iv_follow_avatar);
        }
    }

    public Bitmap bitmap(String urlpath){

        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public interface OnClickListener{
        void onClick(String id);
    }
}
