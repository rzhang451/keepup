package com.example.keepup_v1.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.SignIn.LoginActivity;
import com.example.keepup_v1.activity.ChangeHeadPicActivity;
import com.example.keepup_v1.activity.MyCourseActivity;
import com.example.keepup_v1.activity.MyhealthyActivity;
import com.example.keepup_v1.activity.UserSettingActivity;
import com.example.keepup_v1.activity.VideoActivity;
import com.example.keepup_v1.base.BaseFragment;
import com.example.keepup_v1.http.Constants;
import com.example.keepup_v1.utils.SPUtil;
import com.example.keepup_v1.utils.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class MeFragment extends BaseFragment implements View.OnClickListener {


    //@BindView(R.id.ll_account)
    private LinearLayout ll_account;

    //@BindView(R.id.ll_vip)
    private LinearLayout ll_vip;

    //@BindView(R.id.ll_order)
    private LinearLayout ll_order;

    //@BindView(R.id.health)
    private LinearLayout health;


    //@BindView(R.id.ll_location)
    private LinearLayout ll_location;

    //@BindView(R.id.ll_out)
    private LinearLayout ll_out;


    //@BindView(R.id.tv_zhuan_ye)
    private TextView tv_email;

    //@BindView(R.id.tv_user_name)
    private TextView tv_user_name;
    private TextView my_course;
    //@BindView(R.id.ll_system)
    private LinearLayout ll_system;
    private TextView tv_address;
    private Button change_avatar;
    private TextView tv_follower;
    private TextView tv_intro;
    private static RequestQueue mQueue= MyApplication.requestQueue;
    String url= Constants.BASE_URL;
    String usr_id=MyApplication.user_id;


    @Override
    public int getLayoutResID() {

        return R.layout.fragment_my;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        ll_account = view.findViewById(R.id.ll_account);
        ll_vip = view.findViewById(R.id.ll_vip);
        ll_order = view.findViewById(R.id.ll_order);
        health = view.findViewById(R.id.health);
        ll_location = view.findViewById(R.id.ll_location);
        ll_out = view.findViewById(R.id.ll_out);
        tv_email = view.findViewById(R.id.tv_email);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        ll_system = view.findViewById(R.id.ll_system);
        tv_address=view.findViewById(R.id.tv_address);
        tv_follower=view.findViewById(R.id.tv_follower);
        tv_intro=view.findViewById(R.id.tv_intro);
        change_avatar = view.findViewById(R.id.changeavatar);
        tv_user_name.setText("Logged in");
//        tv_zhuan_ye.setText((String) SPUtils.get(getActivity(),"name",""));
        my_course=view.findViewById(R.id.my_course);
        getinfo();
    }

    @Override
    public void initListener() {
        super.initListener();
        ll_account.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        ll_out.setOnClickListener(this);
        ll_system.setOnClickListener(this);
        ll_location.setOnClickListener(this);
        health.setOnClickListener(this);
        change_avatar.setOnClickListener(this);
        my_course.setOnClickListener(this);
    }
    private void getinfo(){
        String URLline = url + "/profile?id="+usr_id;
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
                                    tv_user_name.setText(dataobj.getString("username"));
                                    tv_email.setText(dataobj.getString("email"));
                                    tv_address.setText(dataobj.getString("location"));
//                                    tv_follower.setText(dataobj.getString("followers"));
                                    tv_intro.setText(dataobj.getString("miniIntro"));
                                }
//                                Log.d("Response Sendback",name );
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
                });
        mQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.rv_user_data:
//                Intent intent = new Intent(getActivity(),LoginActivity.class);
//                startActivity(intent);
//                break;

//            case R.id.ll_vip:
//                Intent ll_vip = new Intent(getActivity(), AddWeightActivity.class);
//                startActivity(ll_vip);
//                break;

            case R.id.ll_location:
                Intent ll_location = new Intent(getActivity(), UserSettingActivity.class);
                startActivity(ll_location);
                break;

            case R.id.ll_out:
                SPUtil.getInstance().putString("name","");
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                //getActivity().finish();
                break;

            case R.id.health:
                Intent health = new Intent(getActivity(), MyhealthyActivity.class);
                startActivity(health);
                break;
            case R.id.changeavatar:
                Intent change = new Intent(getActivity(), ChangeHeadPicActivity.class);
                startActivity(change);
            break;
            case R.id.my_course:
                Intent course = new Intent(getActivity(), MyCourseActivity.class);
                startActivity(course);
        }
    }


}
