package com.example.keepup_v1.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keepup_v1.R;
import com.example.keepup_v1.funcs.MessageData;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MediaAdapter extends CommonAdapter<MessageData>{
    public String message_id;
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_message;
    private ImageView iv_photo;
    private ImageButton ibt_thumb;
    private TextView tv_like;
    private EditText et_comment;
    private LinearLayout ll_message;
    private int mLayoutId;

    public MediaAdapter(Context context, List<MessageData> data, int layoutId, String url, OnItemClickListener listener,
                        OnItemEnterLisener enterListener, OnItemClickLikeListener likeListener){
        super(context,data,layoutId,url, listener,enterListener,likeListener);
    }

    @Override
    public void convert(ViewHolder holder, MessageData messageData, int position) {
        holder.iv_avatar = holder.getView(R.id.iv_media_avatar);
        holder.tv_name = holder.getView(R.id.tv_media_name);
        holder.tv_date = holder.getView(R.id.tv_media_date);
        holder.tv_message = holder.getView(R.id.tv_media_message);
        holder.ibt_thumb = holder.getView(R.id.ibt_media_thumb);
        holder.tv_like = holder.getView(R.id.tv_media_like);
        holder.et_comment = holder.getView(R.id.et_media_comment);
        holder.iv_photo = holder.getView(R.id.iv_media_photo);
        holder.ll_message = holder.getView(R.id.ll_message);

        Log.d("position",String.valueOf(position));
        MessageData mMessage = mDatas.get(position);
        Log.d("mData",mMessage.getmMessage().toString());
        if (mMessage.getmAvatar() != null) {
            holder.setImageBitmap(holder.iv_avatar.getId(),bitmap(mMessage.getmAvatar()));
            //Glide.with(mContext).load(mUrl + "/public/upload/image/profile/" + mMessage.getmAvatar()).into(iv_avatar);
        }
        holder.tv_name.setText(mMessage.getmUsername());
        holder.tv_date.setText(mMessage.getmDate());
        holder.tv_message.setText(mMessage.getmMessage());
        holder.tv_like.setText(mMessage.getmLike());
        if (mMessage.getmThumb() == "1") {
            holder.ibt_thumb.setBackground(mContext.getResources().getDrawable(R.drawable.like_pressed));
        } else {
            holder.ibt_thumb.setBackground(mContext.getResources().getDrawable(R.drawable.like_unpressed));
        }
        if (mMessage.getmPhoto() != null) {
            holder.setImageBitmap(holder.iv_photo.getId(),bitmap(mMessage.getmPhoto()));
            //Log.d("error!", mMessage.getmPhoto());
            //Glide.with(mContext).load(mUrl + "/public/image/avatar/" + mMessage.getmPhoto()).into(iv_photo);

        }
        holder.setOnClickListener(R.id.ll_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(mMessage.getmId());
            }
        });

        holder.et_comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH) || (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && v.getText() != null && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    mEnterListener.onEnter(mMessage.getmId(), holder.et_comment.getText().toString());
                    holder.et_comment.setText(null);
                }
                return true;
            }
        });
        holder.ibt_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    mLikeListener.onClickLike(mMessage.getmId(), mMessage.getmThumb());
                    Integer number;
                    if (mMessage.getmThumb() == "1") {
                        number = Integer.valueOf(mMessage.getmLike()) - 1;
                        messageData.setmLike(number.toString());
                        holder.tv_like.setText(mMessage.getmLike());
                        messageData.setmThumb("0");
                        holder.ibt_thumb.setBackground(mContext.getResources().getDrawable(R.drawable.like_unpressed));
                    } else {
                        number = Integer.valueOf(mMessage.getmLike()) + 1;
                        messageData.setmLike(number.toString());
                        holder.tv_like.setText(mMessage.getmLike());
                        messageData.setmThumb("1");
                        holder.ibt_thumb.setBackground(mContext.getResources().getDrawable(R.drawable.like_pressed));
                    }


                }
            }
        });
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

    public interface OnItemClickListener{
        void onClick(String id);
    }

    public interface OnItemEnterLisener{
        void onEnter(String id, String comment);
    }

    public interface OnItemClickLikeListener{
        void onClickLike(String id, String state);
    }

}