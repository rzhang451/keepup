package com.example.keepup_v1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MyCourseActivity extends BaseActivity {
    int x;
    String name;
    String duration;
    String image;
    String way;
    @BindView(R.id.my_courses)
    MaxListView my_courses;
    ArrayList<String> datas = new ArrayList<>();
    String url= Constants.BASE_URL;
    private CourseAdapter my_courseAdapter;
    private List<CourseBean> my_courseDatas = new ArrayList<>();
    String content;
    String usr_id=MyApplication.user_id;
    protected int getResourceId() {
        return R.layout.activity_my_course;
    }
    Intent intent1;
    Intent intent2;
    private static RequestQueue mQueue= MyApplication.requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void initListener() {
        super.initListener();

    }

    @Override
    public void initData() {
        my_courseAdapter = new CourseAdapter(MyCourseActivity.this, my_courseDatas, R.layout.adapter_course);
        my_courses.setAdapter(my_courseAdapter);
        intent1=new Intent();//回传的Intent
        intent2=getIntent();
        content=intent2.getStringExtra("info");
        myCourse();
//        System.out.println(datas);
//        for(int i=0;i<x;i++){
//            System.out.println(datas.get(i));
//          getDatas(datas.get(i));
//        }
        my_courses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MyCourseActivity.this, VideoActivity.class);
                it.putExtra("info", my_courseDatas.get(position));
                startActivity(it);
            }
        });
    }


    private void myCourse(){


        String URLline = url + "/mainpage/favorite?id="+usr_id;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals("200")) {
                                x= jsonObject.getInt("result_number");
                                String data = jsonObject.getString("datas");
                                List<String> list = Arrays.asList(data.split(","));
                                if(list.size()!=0) {
                                    for (int i = 0; i < list.size(); i++) {
                                        if (i == 0) {
                                            datas.add(list.get(i).substring(2, 8));
                                        } else {
                                            datas.add(list.get(i).substring(1, 7));
                                        }
                                    }
                                    System.out.println("q" + datas);
                                }
                                else{
                                    Toast.makeText(MyCourseActivity.this, "You don't have course favorite", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.d("Response Sendback", "error");
                                String msg = jsonObject.getString("msg");
//                                tv_notation.setText(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i=0;i<x;i++){
                            System.out.println(datas.get(i));
                            getDatas(datas.get(i));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyCourseActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);

    }

    private void getDatas(String id) {

        String URLline = url + "/course/get_course_info?id="+id;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals("200")) {
                                JSONArray userDatas = jsonObject.getJSONArray("data");
                                for (int i = 0; i < userDatas.length(); i++) {
                                    JSONObject dataobj = userDatas.getJSONObject(i);
                                    name = dataobj.getString("name");
                                    image=dataobj.getString("cover");
                                    duration=dataobj.getString("duration");
                                    way=dataobj.getString("way");
                                    my_courseDatas.add(new CourseBean("http://172.20.10.3:3000"+image, "2", name,way));
                                    System.out.println(my_courseAdapter);
                                }
//                                my_courseDatas.add(new CourseBean("http://172.20.10.3:3000"+image, "2", name));
                                my_courseAdapter.notifyDataSetChanged();
                                Log.d("Response Sendback",name );
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
                        Toast.makeText(MyCourseActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);

    }






}