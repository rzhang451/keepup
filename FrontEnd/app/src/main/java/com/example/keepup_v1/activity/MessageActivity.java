package com.example.keepup_v1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.SignIn.LoginActivity;
import com.example.keepup_v1.adapters.MessageAdapter;
import com.example.keepup_v1.funcs.CommentData;
import com.example.keepup_v1.funcs.ConnectQueue;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    public String msg_id = MediaActivity.message_id;
    public String usr_id = MyApplication.user_id;
    private List<CommentData> comments = new ArrayList<>();
    private RecyclerView.Adapter commentAdapter;
    private String thumb;
    private Integer like;
    RequestQueue mQueue = MyApplication.requestQueue;

    private ImageButton ibt_avatar;
    private TextView tv_username;
    private TextView tv_date;
    private TextView tv_message;
    private ImageView iv_photo;
    private ImageButton ibt_like;
    private TextView tv_like;
    private RecyclerView rcv_comments;
    private EditText et_comment;
    private Button bt_comment_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ibt_avatar = findViewById(R.id.ibt_msg_avatar);
        tv_username = findViewById(R.id.tv_msg_name);
        tv_date = findViewById(R.id.tv_msg_date);
        tv_message = findViewById(R.id.tv_msg_message);
        iv_photo = findViewById(R.id.iv_msg_photo);
        ibt_like = findViewById(R.id.ibt_msg_thumb);
        tv_like = findViewById(R.id.tv_msg_like);
        rcv_comments = findViewById(R.id.rv_msg_comments);
        et_comment = findViewById(R.id.et_msg_comment);
        bt_comment_send = findViewById(R.id.bt_msg_send_comment);
        rcv_comments.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        commentAdapter = new MessageAdapter(MessageActivity.this, comments);
        rcv_comments.setAdapter(commentAdapter);

        getInfo();
        //getComments();

        ibt_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this,FriendActivity.class);
                startActivity(intent);
            }
        });

        bt_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String comment = et_comment.getText().toString();
                sendComment(comment);
            }
        });

        ibt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLike(usr_id,thumb);
                if (thumb == "1") {
                    like = like - 1;
                    tv_like.setText(String.valueOf(like));
                    thumb = "0";
                    ibt_like.setBackground(getResources().getDrawable(R.drawable.like_unpressed));
                } else {
                    like = like + 1;
                    tv_like.setText(String.valueOf(like));
                    thumb = "1";
                    ibt_like.setBackground(getResources().getDrawable(R.drawable.like_unpressed));
                }
            }
        });
    }

    public void getInfo(){
        String URLline = Constants.MESSAGE_GETINFO_URL + msg_id;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("response",response.toString());
                            if(jsonObject.getString("code").equals("200")){
                                Log.d("dataobj",jsonObject.toString());
                                JSONObject data = jsonObject.getJSONObject("data");
                                //Glide.with(MessageActivity.this).load(Constants.BASE_URL+"/public/image/avatar"+dataobj.getString("avatar")).into(ibt_avatar);
                                tv_username.setText(data.getString("username"));
                                FriendActivity.id = data.getString("username");
                                ibt_avatar.setImageBitmap(bitmap(data.getString("avatar")));
                                tv_date.setText(data.getString("date"));
                                tv_message.setText(data.getString("message"));
                                tv_like.setText(data.getString("like_usr"));
                                like = Integer.valueOf(data.getString("like_usr"));
                                if(data.getString("photo")!=null){
                                    iv_photo.setImageBitmap(bitmap(Constants.BASE_URL + data.get("photo")));
                                    //Glide.with(MessageActivity.this).load(Constants.BASE_URL+"/public/image/photo"+dataobj.getString("photo")).into(iv_photo);
                                }
                                if(data.getString("like").contains(usr_id)){
                                    thumb = "1";
                                    ibt_like.setBackground(getResources().getDrawable(R.drawable.like_pressed));
                                }else{
                                    thumb = "0";
                                    ibt_like.setBackground(getResources().getDrawable(R.drawable.like_unpressed));
                                }
                                JSONArray arrayData = data.getJSONArray("comment");
                                for(int i = 0;i<arrayData.length();i++){
                                    JSONObject dataobj = arrayData.getJSONObject(i);
                                    CommentData commentData = new CommentData();
                                    commentData.username = dataobj.getString("username");
                                    commentData.comment = dataobj.getString("comment");
                                    comments.add(commentData);
                                }
                                commentAdapter.notifyDataSetChanged();

                            }else{
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(MessageActivity.this,msg,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MessageActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);
    }
    /*
    public void getComments(){
        String URLline = Constants.MESSAGE_GETCOMMENT_URL + msg_id;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("code").equals("200")){
                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                for(int i=0;i<dataArray.length();i++){
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    comments[i].username=dataobj.getString("username");
                                    comments[i].comment =dataobj.getString("comment");
                                }
                            }else{
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(MessageActivity.this,msg,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MessageActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);
    }
     */

    public void sendComment(String comment){
        String URLline = Constants.MESSAGE_SENDCOMMENT_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("code").equals("200")){
                                commentAdapter.notifyDataSetChanged();
                            }
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(MessageActivity.this,msg,Toast.LENGTH_LONG).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MessageActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("msg_id", msg_id);
                paramMap.put("usr_id",usr_id);
                paramMap.put("comment",comment);
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
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

    public void sendLike(String msg_id, String state){
        String URLline = Constants.MEDIA_LIKE_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(MessageActivity.this,msg,Toast.LENGTH_LONG).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        //mediaAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MessageActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("msg_id", msg_id);
                paramMap.put("usr_id",usr_id);
                paramMap.put("state",state);
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }
}