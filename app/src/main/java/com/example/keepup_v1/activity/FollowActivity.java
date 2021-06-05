package com.example.keepup_v1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.adapters.FollowAdapter;
import com.example.keepup_v1.funcs.CommentData;
import com.example.keepup_v1.funcs.FriendData;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FollowActivity extends AppCompatActivity {
    public String usr_id = MyApplication.user_id;
    RequestQueue mQueue = MyApplication.requestQueue;

    private RecyclerView rv_follow;
    private RecyclerView rv_subscribe;
    private RecyclerView.Adapter followAdapter;
    private RecyclerView.Adapter subsrcibeAdapter;
    private List<FriendData> followData = new ArrayList<>();
    private List<FriendData> subsrcibeData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        rv_follow = findViewById(R.id.rv_follow_follower);
        rv_follow.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
        followAdapter = new FollowAdapter(FollowActivity.this, followData, new FollowAdapter.OnClickListener() {
            @Override
            public void onClick(String id) {
                Intent intent = new Intent(FollowActivity.this, FriendActivity.class);
                FriendActivity.id = id;
                startActivity(intent);
            }
        });
        rv_follow.setAdapter(followAdapter);

        rv_subscribe = findViewById(R.id.rv_follow_subsribe);
        rv_subscribe.setLayoutManager(new LinearLayoutManager(FollowActivity.this));
        followAdapter = new FollowAdapter(FollowActivity.this, subsrcibeData, new FollowAdapter.OnClickListener() {
            @Override
            public void onClick(String id) {
                Intent intent = new Intent(FollowActivity.this, FriendActivity.class);
                FriendActivity.id = id;
                startActivity(intent);
            }
        });
        rv_subscribe.setAdapter(subsrcibeAdapter);

        String URLline = Constants.FOLLOW_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("code").equals("200")){
                                Log.d("dataobj",jsonObject.toString());
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray followArray = data.getJSONArray("follow");
                                followData.clear();
                                for(int i = 0;i< followArray.length();i++){
                                    JSONObject follow = followArray.getJSONObject(i);
                                    FriendData followinfo = new FriendData();
                                    followinfo.mId = follow.getString("id");
                                    followinfo.mAvatar = follow.getString("avatar");
                                    followinfo.mUsername = follow.getString("name");
                                    followData.add(followinfo);
                                }
                                JSONArray followerArray = data.getJSONArray("follower");
                                for(int i = 0;i<followArray.length();i++){
                                    JSONObject follower = followerArray.getJSONObject(i);
                                    FriendData followerinfo = new FriendData();
                                    followerinfo.mId = follower.getString("id");
                                    followerinfo.mAvatar = follower.getString("avatar");
                                    followerinfo.mUsername = follower.getString("name");
                                    followData.add(followerinfo);
                                }
                                followAdapter.notifyDataSetChanged();
                                subsrcibeAdapter.notifyDataSetChanged();
                            }else{
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(FollowActivity.this,msg,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FollowActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);
    }
}