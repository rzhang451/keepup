package com.example.keepup_v1.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.activity.MainActivity;
import com.example.keepup_v1.bean.BaseResponse;
import com.example.keepup_v1.funcs.ConnectQueue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import com.example.keepup_v1.R;
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.http.Constants;
import com.example.keepup_v1.utils.StringUtils;
import com.example.keepup_v1.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ForgetPwdActivity extends BaseActivity {
    Button btn_login;
    Button btn_verify_code;
    EditText etPhoneNum;
    EditText etCode;
    EditText etPassword;
    EditText etVeriPassword;
    ImageView iv_back;

//    MyDatabaseHelper databaseHelper;

    @Override
    protected int getResourceId() {
        return R.layout.activity_forget_pwd;
    }

    RequestQueue mQueue = MyApplication.requestQueue;

    public static String usr_id = MyApplication.user_id;

    @Override
    protected void initView() {
        super.initView();

        btn_login = findViewById(R.id.btn_fpw_login);
        btn_verify_code = findViewById(R.id.btn_verify_code);
        etPhoneNum = findViewById(R.id.et_phoneNum);
        etCode = findViewById(R.id.et_code);
        etPassword = findViewById(R.id.et_password);
        etVeriPassword = findViewById(R.id.et_verify_password);
        iv_back = findViewById(R.id.iv_close);

        ImmersionBar.with(this).titleBar(R.id.toolbar).keyboardEnable(true).init();
        ImmersionBar.with(ForgetPwdActivity.this).keyboardEnable(false).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.btn3).init();
//        databaseHelper = new MyDatabaseHelper(this, "user.db", null, 1);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                if (StringUtils.getEditTextData(etPhoneNum).isEmpty() && StringUtils.getEditTextData(etPassword).isEmpty()) {
                    ToastUtils.showToast(ForgetPwdActivity.this, "Please enter username or password");
                    return;
                }
                if (StringUtils.getEditTextData(etPhoneNum).isEmpty()) {
                    ToastUtils.showToast(ForgetPwdActivity.this, "Please enter username");
                    return;
                }

                if (StringUtils.getEditTextData(etCode).isEmpty()) {
                    ToastUtils.showToast(ForgetPwdActivity.this, "Please enter code");
                    return;
                }
                if (StringUtils.getEditTextData(etPassword).isEmpty()) {
                    ToastUtils.showToast(ForgetPwdActivity.this, "Please enter password");
                    return;
                }
                String uname = StringUtils.getEditTextData(etPhoneNum);
                String pwd = StringUtils.getEditTextData(etPassword);
                String veri = StringUtils.getEditTextData(etVeriPassword);
                String code = StringUtils.getEditTextData(etCode);
                forget(uname, pwd, veri, code);

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPwdActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etPhoneNum.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    sendCode(email);
                } else {
                    Toast.makeText(ForgetPwdActivity.this, "send code error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void forget(String uname, String pwd, String veri, String code) {
        String url = Constants.FORGET_PWD_SEND_URL;
        Log.d("Required Server", url);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("code").equals("200")){
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<dataArray.length();i++){
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            MyApplication.user_id = dataobj.getString("id");
                        }
                        Intent intent = new Intent(ForgetPwdActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("email", uname);
                paramMap.put("pwd", pwd);
                paramMap.put("check", veri);
                paramMap.put("code", code);
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }

    private void sendCode(String email) {
        String url = Constants.FORGET_PWD_EMAIL_URL;
        Log.d("Required Server", url);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("msg",jsonObject.getString("msg"));
                    Toast.makeText(ForgetPwdActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
                Log.d("error","23333");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("email", email);
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }
}