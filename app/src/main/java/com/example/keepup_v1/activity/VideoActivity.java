package com.example.keepup_v1.activity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.SignIn.SignUpActivity1;
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.bean.CourseBean;
import com.example.keepup_v1.bean.UserInfo;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class VideoActivity extends BaseActivity {
    String url= Constants.BASE_URL;
    String usr_id=MyApplication.user_id;
    //@BindView(R.id.video)
    VideoView video;

    //@BindView(R.id.video_name)
    TextView video_name;

    //@BindView(R.id.video_type)
    TextView video_type;

    //@BindView(R.id.video_time)
    TextView video_time;

    //@BindView(R.id.video_level)
    TextView video_level;

    //@BindView(R.id.video_goal)
    TextView video_goal;

    //@BindView(R.id.video_consumption)
    TextView video_consumption;

    //@BindView(R.id.video_follow)
    Button video_follow;

    //@BindView(R.id.video_intro)
    TextView video_intro;
//    String video_url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//     String video_url = "http://172.20.10.3:3000/video/planche.mp4";
    String video_url;
    String vurl;
    ProgressDialog pd;
    private static RequestQueue mQueue= MyApplication.requestQueue;
    protected int getResourceId() {
        return R.layout.activity_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CourseBean bean = (CourseBean) getIntent().getSerializableExtra("info");
//        video = (VideoView)findViewById(R.id.video);

        //@BindView(R.id.video)
        video = findViewById(R.id.video);

        //@BindView(R.id.video_name)
        video_name = findViewById(R.id.video_name);
        video_name.setText(bean.getName());

        //@BindView(R.id.video_type)
        video_type = findViewById(R.id.video_type);
        video_type.setText( MyApplication.user_id);
        //@BindView(R.id.video_time)
        video_time = findViewById(R.id.video_time);

        //@BindView(R.id.video_level)
        video_level = findViewById(R.id.video_level);

        //@BindView(R.id.video_goal)
        video_goal = findViewById(R.id.video_goal);

        //@BindView(R.id.video_consumption)
        video_consumption = findViewById(R.id.video_consumption);

        //@BindView(R.id.video_follow)
        video_follow = findViewById(R.id.video_follow);

        //@BindView(R.id.video_intro)
        video_intro = findViewById(R.id.video_intro);

        pd = new ProgressDialog(VideoActivity.this);
        pd.setMessage("Buffering video please wait...");
        pd.show();
        getinfo(bean.getName());
        vurl=bean.getWay();
        System.out.println(bean.getWay());
        Uri uri = Uri.parse(vurl);
        Log.d("msg", String.valueOf(uri));
        MediaController localMediaController = new MediaController(this);
        video.setMediaController(localMediaController);
        video.setVideoURI(uri);
        video.start();
//        getinfo(bean.getName());

        video_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favo( "444444");
            }

        });
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener()

            {
                @Override
                public void onPrepared (MediaPlayer mp){
                pd.dismiss();
            }
            });
        }

//    protected void initData(){
//
//        Uri uri = Uri.parse(video_url);
//        Log.d("msg", String.valueOf(uri));
//        MediaController localMediaController = new MediaController(this);
//
//        video.setMediaController(localMediaController);
//        video.setVideoURI(uri);
//
//        video.start();
//        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
//
//        {
//            @Override
//            public void onPrepared (MediaPlayer mp){
//                pd.dismiss();
//            }
//        });
//    }
        private void getinfo(String name) {
        String URLline = url + "/course?name="+name;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals("200")) {
                                JSONArray videoDatas = jsonObject.getJSONArray("data");
                                for (int i = 0; i < videoDatas.length(); i++) {
                                    JSONObject dataobj = videoDatas.getJSONObject(i);
                                    video_level.setText(dataobj.getString("difficulty"));
                                    video_goal.setText(dataobj.getString("goal"));
                                    video_consumption.setText(dataobj.getString("consumption"));
                                    video_intro.setText(dataobj.getString("intro"));
                                    video_time.setText(dataobj.getString("time"));
                                    video_type.setText(dataobj.getString("type"));
                                    video_url=url+dataobj.getString("way");
//                                    Uri uri = Uri.parse(video_url);
//                                    Log.d("msg", String.valueOf(uri));
//                                    video.setVideoURI(uri);
//                                    video.start();

                                }
//                                Log.d("Response Sendback",name );
//                                Uri uri = Uri.parse(video_url);
//                                Log.d("msg", String.valueOf(uri));
//                                MediaController localMediaController = new MediaController(this);
//
//                                video.setMediaController(localMediaController);
//                                video.setVideoURI(uri);
//
//                                video.start();
                            } else {
                                Log.d("Response Sendback", "error");
                                String msg = jsonObject.getString("msg");
//                                tv_notation.setText(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VideoActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);
//        Uri uri = Uri.parse(video_url);
//        Log.d("msg", String.valueOf(uri));
//        MediaController localMediaController = new MediaController(this);
//
//        video.setMediaController(localMediaController);
//        video.setVideoURI(uri);
//
//        video.start();
    }

private void favo(String video_id){
        String URLline = url + "/search/add_favor";
    Log.d("Required Server", URLline);
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("code").equals("200")){
                            Log.d("Response Sendback", "succes");
                        }else{
                            Log.d("Response Sendback", "error");
                            String msg = jsonObject.getString("msg");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VideoActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                }
            }){
        @Override
        protected Map<String,String> getParams() throws AuthFailureError {
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("id",usr_id);
            paramMap.put("course_id",video_id);
            return paramMap;
        }
    };
    mQueue.add(stringRequest);
}
    protected void initData(){
        super.initData();
//        pd = new ProgressDialog(VideoActivity.this);
//        pd.setMessage("Buffering video please wait...");
//        pd.show();
//        video_url=url+"/video/stretch.mp4";
//        Uri uri = Uri.parse(video_url);
//        Log.d("msg", String.valueOf(uri));
//        MediaController localMediaController = new MediaController(this);
//
//        video.setMediaController(localMediaController);
//        video.setVideoURI(uri);
//
//        video.start();
//        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
//
//        {
//            @Override
//            public void onPrepared (MediaPlayer mp){
//                pd.dismiss();
//            }
//        });
    }




}