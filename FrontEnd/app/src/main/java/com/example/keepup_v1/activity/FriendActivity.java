package com.example.keepup_v1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.SignIn.ForgetPwdActivity;
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.http.Constants;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendActivity extends BaseActivity {
    public static String id;
    RequestQueue mQueue = MyApplication.requestQueue;

    private Button bt_follow;
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_addr;
    private TextView tv_number;
    private TextView tv_intro;

    @Override
    protected int getResourceId() {
        return R.layout.activity_friend;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bt_follow = findViewById(R.id.btn_friend_follow);
        iv_avatar = findViewById(R.id.iv_user_head);
        tv_name = findViewById(R.id.tv_user_name);
        tv_sex = findViewById(R.id.tv_friend_sex);
        tv_addr = findViewById(R.id.tv_friend_addr);
        tv_number = findViewById(R.id.tv_friend_number);
        tv_intro = findViewById(R.id.tv_friend_intro);
        getinfo(id);

    }

    public void getinfo(String id){
        String url = Constants.FORGET_PWD_EMAIL_URL + "/" + id;
        Log.d("Required Server", url);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("code").equals("200")){
                        JSONObject info = jsonObject.getJSONObject("data");
                        Glide.with(FriendActivity.this).load(Constants.BASE_URL+"/public/image/avatar/"+ info.getString("avatar"));
                        tv_name.setText(info.getString("name"));
                        tv_sex.setText(info.getString("sex"));
                        tv_addr.setText(info.getString("address"));
                        tv_number.setText(info.getString("follower"));
                        tv_intro.setText(info.getString("intro"));

                    }else {
                        Log.d("msg", jsonObject.getString("msg"));
                        Toast.makeText(FriendActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<>();
                return paramMap;
            }
        };
        mQueue.add(stringRequest);

    }
}