package com.example.keepup_v1.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.keepup_v1.R;
import com.example.keepup_v1.activity.VideoActivity;
import com.example.keepup_v1.adapters.CourseAdapter;
import com.example.keepup_v1.base.BaseFragment;
import com.example.keepup_v1.bean.CourseBean;
import com.example.keepup_v1.views.MaxListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class AgendaFragment extends BaseFragment {


    //@BindView(R.id.my_courses)
    private MaxListView my_courses;

    private CourseAdapter my_coursesAdapter;
    private List<CourseBean> my_coursesDatas = new ArrayList<>();

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_second;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        my_courses = view.findViewById(R.id.my_courses);
    }


    @Override
    public void initListener() {
        super.initListener();
    }

    @Override
    public void initData() {
        my_coursesAdapter = new CourseAdapter(getActivity(), my_coursesDatas, R.layout.adapter_course);
        my_courses.setAdapter(my_coursesAdapter);
//        setDatas();
        my_courses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), VideoActivity.class);
                it.putExtra("info", my_coursesDatas.get(position));
                startActivity(it);
            }
        });
    }
//
//    private void setDatas() {
//        my_coursesDatas.clear();
//        for (int i = 0; i < 6; i++) {
//            int random = new Random().nextInt(110);
//            my_coursesDatas.add(new CourseBean("", "50:30", "我是测试数据我是测试数据" + random));
//        }
//        my_coursesAdapter.notifyDataSetChanged();
//
//    }

}
