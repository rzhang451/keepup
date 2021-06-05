package com.example.keepup_v1.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.activity.MyCourseActivity;
import com.example.keepup_v1.activity.SearchActivity;
import com.example.keepup_v1.activity.VideoActivity;
import com.example.keepup_v1.adapters.CourseAdapter;
import com.example.keepup_v1.base.BaseFragment;
import com.example.keepup_v1.bean.CourseBean;
import com.example.keepup_v1.http.Constants;
import com.example.keepup_v1.views.MaxListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

public class MainFragment extends
        BaseFragment {
    //    @BindView(R.id.video)
//    LinearLayout video;
    //@BindView(R.id.search)
    private Button search;
    //@BindView(R.id.search_content)
    private EditText search_content;
    //@BindView(R.id.category_cb1)
    private CheckBox category_cb1;
    //@BindView(R.id.category_cb2)
    private CheckBox category_cb2;
    //@BindView(R.id.category_cb3)
    private CheckBox category_cb3;
    //@BindView(R.id.category_cb4)
    private CheckBox category_cb4;
    //@BindView(R.id.recommend_lv)
    private MaxListView recommendLv;
    //@BindView(R.id.collect_lv)
    private MaxListView collectLv;
    String url= Constants.BASE_URL;
    String usr_id=MyApplication.user_id;
    private CourseAdapter recommendAdapter;
    private CourseAdapter collectAdapter;
    private List<CourseBean> recommendDatas = new ArrayList<>();
    private List<CourseBean> courseDatas = new ArrayList<>();
    private static RequestQueue mQueue= MyApplication.requestQueue;
    String name;
    String duration;
    String image;
    String way;
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_three;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        //@BindView(R.id.search)
        search = view.findViewById(R.id.search);
        //@BindView(R.id.search_content)
        search_content = view.findViewById(R.id.search_content);
        //@BindView(R.id.category_cb1)
        category_cb1 = view.findViewById(R.id.category_cb1);
        //@BindView(R.id.category_cb2)
        category_cb2 = view.findViewById(R.id.category_cb2);
        //@BindView(R.id.category_cb3)
        category_cb3 = view.findViewById(R.id.category_cb3);
        //@BindView(R.id.category_cb4)
        category_cb4 = view.findViewById(R.id.category_cb4);
        //@BindView(R.id.recommend_lv)
        recommendLv = view.findViewById(R.id.recommend_lv);
        //@BindView(R.id.collect_lv)
        collectLv = view.findViewById(R.id.collect_lv);
    }


    public void initListener() {
        super.initListener();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search = new Intent(getActivity(), SearchActivity.class);
                search.putExtra("info",search_content.getText().toString());
                startActivity(search);
            }
        });
    }



    @Override
    public void initData() {
        recommendAdapter = new CourseAdapter(getActivity(), recommendDatas, R.layout.adapter_course);
        recommendLv.setAdapter(recommendAdapter);
        collectAdapter = new CourseAdapter(getActivity(), courseDatas, R.layout.adapter_course);
        collectLv.setAdapter(collectAdapter);
//        setDatas();
        String URLline1 = url + "/mainpage/recommend?id="+usr_id;
        Log.d("Required Server", URLline1);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("code").equals("200")) {
                                JSONArray coursesDatas = jsonObject.getJSONArray("data");
                                for (int i = 0; i < coursesDatas.length(); i++) {
                                    JSONObject dataobj = coursesDatas.getJSONObject(i);
                                    name = dataobj.getString("name");
                                    duration = dataobj.getString("duration");
                                    image=dataobj.getString("cover");
                                    way=dataobj.getString("way");
                                    Log.d("11",name);
                                    recommendDatas.add(new CourseBean(url+image,duration, "Name:"+name,way));
                                }
                                recommendAdapter.notifyDataSetChanged();
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
                        Toast.makeText(getActivity(), "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }) ;
        mQueue.add(stringRequest);
        recommendAdapter.notifyDataSetChanged();
//        String URLline = url + "/mainpage/favorite?id="+usr_id;
//        Log.d("Required Server", URLline);
//        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, URLline,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.getString("code").equals("200")) {
//                                JSONArray coursesDatas = jsonObject.getJSONArray("data");
//                                for (int i = 0; i < coursesDatas.length(); i++) {
//                                    JSONObject dataobj = coursesDatas.getJSONObject(i);
//                                    name = dataobj.getString("name");
//                                    duration = dataobj.getString("duration");
//                                    image=dataobj.getString("cover");
//                                    way=dataobj.getString("way");
//                                    Log.d("11",name);
//                                    courseDatas.add(new CourseBean("http://172.20.10.3:3000"+image,duration, "Name:"+name,way));
//                                }
//                                collectAdapter.notifyDataSetChanged();
//                            } else {
//                                Log.d("Response Sendback", "error");
//                                String msg = jsonObject.getString("msg");
////                                tv_notation.setText(msg);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
//                    }
//                }) ;
//        mQueue.add(stringRequest);
//        collectAdapter.notifyDataSetChanged();
        recommendLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), VideoActivity.class);
                it.putExtra("info", recommendDatas.get(position));
                startActivity(it);
            }
        });
        collectLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), VideoActivity.class);
                it.putExtra("info", courseDatas.get(position));
                startActivity(it);
            }
        });
    }

    @Optional
    @OnClick({R.id.category_cb1, R.id.category_cb2, R.id.category_cb3, R.id.category_cb4,R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.category_cb1:
                changeDatas("leg");
                if(category_cb1.isChecked()==true);{
                Log.d("msg","111");
            }
                break;
                case R.id.category_cb2:
                    changeDatas("1");
            break;
                    case R.id.category_cb3:
                changeDatas("2");
            break;
                case R.id.category_cb4:
                changeDatas(category_cb4.getText().toString());
                break;
        }

    }

    private void setDatas() {
        recommendDatas.clear();
        courseDatas.clear();
        for (int i = 0; i < 6; i++) {
            int random = new Random().nextInt(110);
            recommendDatas.add(new CourseBean("", "50:30", "我是测试数据我是测试数据" + random,way));
        }
        recommendAdapter.notifyDataSetChanged();

    }
    private void changeDatas(String x) {
        recommendDatas.clear();
        String URLline = url + "/search/label?type="+x;
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
                                }
                                recommendDatas.add(new CourseBean("http://172.20.10.3:3000"+image, "2", name,way));
                                recommendAdapter.notifyDataSetChanged();
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
//                        Toast.makeText(VideoActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);

    }

}
