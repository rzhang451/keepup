package com.example.keepup_v1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.R;
import com.example.keepup_v1.SignIn.ForgetPwdActivity;
import com.example.keepup_v1.SignIn.LoginActivity;
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.fragment.MeFragment;
import com.example.keepup_v1.http.Constants;
import com.example.keepup_v1.utils.StringUtils;
import com.example.keepup_v1.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class UserSettingActivity extends BaseActivity {
    String url= Constants.BASE_URL;
    String usr_id=MyApplication.user_id;
    EditText user_name;
    EditText user_sex;
    EditText user_intro;
    EditText user_email;
    EditText user_location;
    Button confirm;
    Button change_pwd;
    private MeFragment meFragment;
    String id1;
    @Override
    protected int getResourceId() {
        return R.layout.activity_user_setting;
    }

    private static RequestQueue mQueue = MyApplication.requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_name = findViewById(R.id.user_name);
        user_sex = findViewById(R.id.user_sex);
        user_intro = findViewById(R.id.user_intro);
        user_email = findViewById(R.id.user_email);
        user_location = findViewById(R.id.user_location);
        confirm = findViewById(R.id.confirm);
        change_pwd = findViewById(R.id.change_pwd);
        getinfo();
//        super.initListener();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            if(StringUtils.getEditTextData(user_name).isEmpty()&&StringUtils.getEditTextData(user_sex).isEmpty()&&StringUtils.getEditTextData(user_intro).isEmpty()&&StringUtils.getEditTextData(user_email).isEmpty()&&StringUtils.getEditTextData(user_location).isEmpty()) {
//            }
//            else{
                setinfo();

            }
        });
    }

    protected void initView(){
        super.initView();

    }

    private void getinfo() {
        String URLline = url + "/profile?id="+usr_id;
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
                                    user_name.setText(dataobj.getString("username"));
                                    user_sex.setText(dataobj.getString("sexe"));
                                    user_intro.setText(dataobj.getString("miniIntro"));
                                    user_email.setText(dataobj.getString("email"));
                                    user_location.setText(dataobj.getString("location"));
                                }
//                                Log.d("Response Sendback",id );
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
                        Toast.makeText(UserSettingActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }) ;
        mQueue.add(stringRequest);
    }
    private void setinfo(){
        final String name = user_name.getText().toString();
        final String email = user_email.getText().toString();
        final String sex = user_sex.getText().toString();
        final String intro = user_intro.getText().toString();
        final String location = user_location.getText().toString();
        String URLline = url + "/profile/change";
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
                        Toast.makeText(UserSettingActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("username",name);
                paramMap.put("sexe",sex);
//                paramMap.put("email",user_email.getText().toString());
                paramMap.put("email",email);
                paramMap.put("location",location);
                paramMap.put("intro",intro);
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }
    }

