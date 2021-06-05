package com.example.keepup_v1.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.adapters.CourseAdapter;
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.bean.CourseBean;
import com.example.keepup_v1.http.Constants;
import com.example.keepup_v1.views.MaxListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {
    String url= Constants.BASE_URL;
    //@BindView(R.id.search_course)
    MaxListView search_course;
    //@BindView(R.id.search_news)
    MaxListView search_news;
    //@BindView(R.id.search_user)
    ListView search_user;
    //@BindView(R.id.test)
    String usr_id=MyApplication.user_id;
    Button test;
    private CourseAdapter search_courseAdapter;
    private List<CourseBean> search_courseDatas = new ArrayList<>();
    String content;
    protected int getResourceId() {
        return R.layout.activity_search;
    }
    Intent intent1;
    Intent intent2;
    private static RequestQueue mQueue= MyApplication.requestQueue;
    String name;
    String duration;
    String image;
    String way;
    @Override
    public void initView(){
        super.initView();
        //@BindView(R.id.search_course)
        search_course = findViewById(R.id.search_course);
        //@BindView(R.id.search_news)
        search_news = findViewById(R.id.search_news);
        //@BindView(R.id.search_user)
        search_user = findViewById(R.id.search_user);
        //@BindView(R.id.test)
        test = findViewById(R.id.test);
    }
    @Override
    public void initListener() {
        super.initListener();

    }

    @Override
    public void initData() {
        //        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setDatas();
//            }
//        });
        search_courseAdapter = new CourseAdapter(SearchActivity.this, search_courseDatas, R.layout.adapter_course);
        search_course.setAdapter(search_courseAdapter);
        intent1=new Intent();//回传的Intent
        intent2=getIntent();
        content=intent2.getStringExtra("info");
        searchCourse(content);
//        setDatas();
        search_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(SearchActivity.this, VideoActivity.class);
                it.putExtra("info", search_courseDatas.get(position));
                startActivity(it);
            }
        });
    }

//
//    private void setDatas() {
//        search_courseDatas.clear();
//        for (int i = 0; i < 3; i++) {
////            int random = new Random().nextInt(110);
//            search_courseDatas.add(new CourseBean("", "50:30", "我是测试数据我是测试数据" ));
//        }
//        search_courseAdapter.notifyDataSetChanged();
//    }

    private void searchCourse(String content){


        String URLline = url + "/search?key="+content;
            Log.d("Required Server", URLline);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("code").equals("200")) {
                                    JSONArray courseDatas = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < courseDatas.length(); i++) {
                                        JSONObject dataobj = courseDatas.getJSONObject(i);
                                        name = dataobj.getString("name");
                                        duration = dataobj.getString("duration");
                                        image=dataobj.getString("cover");
                                        way=dataobj.getString("way");
                                        Log.d("11",name);
                                        search_courseDatas.add(new CourseBean("http://172.20.10.3:3000"+image,duration, "Name:"+name,way));
                                    }
                                    search_courseAdapter.notifyDataSetChanged();
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
                            Toast.makeText(SearchActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }) ;
            mQueue.add(stringRequest);
//            search_courseDatas.clear();

    }
    }
