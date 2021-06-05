package com.example.keepup_v1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.adapters.MediaAdapter;
import com.example.keepup_v1.funcs.MessageData;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaActivity extends AppCompatActivity {
    public static String usr_id = MyApplication.user_id;
    public static String message_id;
    private List<MessageData> messageData = new ArrayList<>();
    private static Integer length = 0;
    private static RequestQueue mQueue = MyApplication.requestQueue;
    private static MediaAdapter mediaAdapter;

    private Button bt_global;
    private Button bt_friend;
    private Button bt_mine;
    private ImageButton ibt_add;
    private String scale;

    //private RecyclerView rv_media;
    private ListView lv_media;
    private SwipeRefreshLayout srl_media;

    private RadioGroup rg_media;
    private RadioButton rb_home;
    private RadioButton rb_running;
    private RadioButton rb_media;
    private RadioButton rb_agenda;
    private RadioButton rb_profile;
    private Drawable draw_home;
    private Drawable draw_running;
    private Drawable draw_media;
    private Drawable draw_agenda;
    private Drawable draw_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        scale = "all";
        //getData();
        lv_media = findViewById(R.id.lv_media_media);
        //rv_media.setLayoutManager(new LinearLayoutManager(MediaActivity.this));
        mediaAdapter = new MediaAdapter(MediaActivity.this, messageData, R.layout.message_layout, Constants.BASE_URL, new MediaAdapter.OnItemClickListener(){
            @Override
            public void onClick(String id) {
                message_id = id;
                Intent intent = new Intent(MediaActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        }, new MediaAdapter.OnItemEnterLisener() {
            @Override
            public void onEnter(String id, String comment) {
                sendComment(id, comment);
            }
        }, new MediaAdapter.OnItemClickLikeListener() {
            @Override
            public void onClickLike(String id, String state) {
                sendLike(id,state);
            }
        });
        lv_media.setAdapter(mediaAdapter);
        getData();
        //setList();

        srl_media = findViewById(R.id.srl_media_media);
        srl_media.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try{
                    Thread.sleep(1500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getData();
            }
        });

        bt_global = findViewById(R.id.bt_media_global);
        bt_friend = findViewById(R.id.bt_media_friend);
        bt_mine = findViewById(R.id.bt_media_mine);
        bt_global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale = "all";
                getData();
            }
        });
        bt_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale = "follow";
                getData();
            }
        });
        bt_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale = "self";
                getData();
            }
        });

        ibt_add = findViewById(R.id.ibt_media_add);
        ibt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaActivity.this, AddMessageActivity.class);
                startActivity(intent);
            }
        });

        rg_media = findViewById(R.id.media_radioGroup);

        rb_home = findViewById(R.id.rb_media_main);
        Drawable draw_home = getResources().getDrawable(R.drawable.bt_home);
        draw_home.setBounds(0,0,50,50);
        rb_home.setCompoundDrawables(null,draw_home,null,null);

        rb_running = findViewById(R.id.rb_media_running);
        Drawable draw_running = getResources().getDrawable(R.drawable.bt_running);
        draw_running.setBounds(0,0,50,50);
        rb_running.setCompoundDrawables(null,draw_running,null,null);

        rb_media = findViewById(R.id.rb_media_media);
        Drawable draw_media = getResources().getDrawable(R.drawable.bt_media);
        draw_media.setBounds(0,0,50,50);
        rb_media.setCompoundDrawables(null,draw_media,null,null);

        rb_agenda = findViewById(R.id.rb_media_agenda);
        Drawable draw_agenda = getResources().getDrawable(R.drawable.bt_agenda);
        draw_agenda.setBounds(0,0,50,50);
        rb_agenda.setCompoundDrawables(null,draw_agenda,null,null);

        rb_profile = findViewById(R.id.rb_media_my);
        Drawable draw_profile = getResources().getDrawable(R.drawable.bt_profile);
        draw_profile.setBounds(0,0,50,50);
        rb_profile.setCompoundDrawables(null,draw_profile,null,null);

        rg_media.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            Intent intentMain = new Intent(MediaActivity.this,MainActivity.class);
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_media_main:
                        MainActivity.JUMP_FRAGMENT = "main";
                        startActivity(intentMain);
                        Log.d("jump","ok");
                        break;
                    case R.id.rb_media_running:
                        Intent intentRunning = new Intent(MediaActivity.this,RunningActivity.class);
                        startActivity(intentRunning);
                        break;
                    case R.id.rb_media_media:
                        break;
                    case R.id.rb_media_agenda:
                        MainActivity.JUMP_FRAGMENT = "agenda";
                        startActivity(intentMain);
                        break;
                    case R.id.rb_media_my:
                        MainActivity.JUMP_FRAGMENT = "profile";
                        startActivity(intentMain);
                        break;
                }
            }
        });
    }

    public void getData(){
        String URLline = Constants.MEDIA_CONTENT_URL +scale+"/"+usr_id;
        Log.d("Required Server", URLline);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {

                    String usr;
                    String date;
                    String content;
                    String like1;
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("receive", response.toString());
                            if (jsonObject.getString("code").equals("200")) {
                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                length = dataArray.length();
                                Log.d("length", length.toString());
                                messageData.clear();
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    MessageData data = new MessageData();
                                    data.setmId(dataobj.getString("id"));
                                    data.setmAvatar(Constants.BASE_URL + dataobj.getString("avatar"));
                                    data.setmUsername(dataobj.getString("usr"));
                                    data.setmDate(dataobj.getString("date"));
                                    data.setmMessage(dataobj.getString("content"));
                                    String like = dataobj.getString("like_user");
                                    if (like.contains(usr_id)) {
                                        data.setmThumb("1");
                                    } else {
                                        data.setmThumb("0");
                                    }
                                    data.setmLike(dataobj.getString("like"));
                                    data.setmPhoto(Constants.BASE_URL + dataobj.getString("pic"));
                                    messageData.add(data);
                                }
                                mediaAdapter.notifyDataSetChanged();
                            } else {
                                String msg = jsonObject.getString("msg");
                                Toast.makeText(MediaActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MediaActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);
    }

    public void sendComment(String msg_id, String comment){
        String URLline = Constants.MEDIA_COMMENT_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(MediaActivity.this,msg,Toast.LENGTH_LONG).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MediaActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(MediaActivity.this,msg,Toast.LENGTH_LONG).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        //mediaAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MediaActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
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

    /*
    public void sendFavorite(String msg_id, String state){
        String URLline = url + "/media/favorite";
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            Toast.makeText(MediaActivity.this,msg,Toast.LENGTH_LONG).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MediaActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
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
    */

//    public void setList(){
//        adapterData.clear();
//        for(int i = 0;i<length;i++){
//            MessageData msgData = messageData.get(i);
//            adapterData.add(msgData);
//            //Log.d("content",msgData.toString());
//        }
//        adapter.notifyDataSetChanged();
//    }
}