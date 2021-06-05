package com.example.keepup_v1.funcs;

import java.io.Serializable;

public class MessageData implements Serializable{
    private String mId ;
    private String mAvatar ;
    private String mUsername ;
    private String mDate ;
    private String mMessage ;
    private String mPhoto ;
    private String mThumb ;
    private String mLike ;

    public MessageData() {
    }

    public MessageData(String id, String avatar, String username, String date, String message, String photo, String thumb, String like) {
        this.mId = id;
        this.mAvatar = avatar;
        this.mUsername = username;
        this.mDate = date;
        this.mMessage = message;
        this.mPhoto = photo;
        this.mThumb = thumb;
        this.mLike = like;
    }

    public String getmId(){return mId;};
    public void setmId(String id){this.mId = id;}
    public String getmAvatar(){return mAvatar;};
    public void setmAvatar(String avatar){this.mAvatar = avatar;}
    public String getmUsername(){return mUsername;};
    public void setmUsername(String username){this.mUsername = username;}
    public String getmMessage(){return mMessage;};
    public void setmDate(String date){this.mDate = date;}
    public String getmDate(){return mDate;};
    public void setmMessage(String message){this.mMessage = message;}
    public String getmPhoto(){return mPhoto;};
    public void setmPhoto(String photo){this.mPhoto = photo;}
    public String getmThumb(){return mThumb;};
    public void setmThumb(String thumb){this.mThumb = thumb;}
    public String getmLike(){return mLike;};
    public void setmLike(String like){this.mLike = like;}
}
