package com.example.keepup_v1.SignIn;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.activity.MainActivity;
import com.example.keepup_v1.R;
import com.example.keepup_v1.funcs.ConnectQueue;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static String usr_id = MyApplication.user_id;

    private EditText et_profile;
    private Drawable dw_profile;
    private EditText et_password;
    private Drawable dw_password;
    private TextView tv_notation;
    private Button bt_sign_in;
    private Button bt_sign_up;
    private Button bt_forget_pwd;

    RequestQueue mQueue = MyApplication.requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login();

        et_profile = findViewById(R.id.et_login_profile);
        dw_profile = getResources().getDrawable(R.drawable.profile);
        dw_profile.setBounds(0,0,60,60);
        et_profile.setCompoundDrawables(dw_profile,null,null,null);

        et_password = findViewById(R.id.et_login_password);
        dw_password = getResources().getDrawable(R.drawable.password);
        dw_password.setBounds(0,0,60,60);
        et_password.setCompoundDrawables(dw_password,null,null,null);

        tv_notation = findViewById(R.id.tv_login_notion);
        tv_notation.setText(null);

        bt_sign_in = findViewById(R.id.bt_login_signIn);
        bt_sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String email = et_profile.getText().toString();
                final String password = et_password.getText().toString();
                String URLline = Constants.LOGIN_URL;
                Log.d("Required Server", URLline);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject.getString("code").equals("200")){
                                        JSONArray dataArray = jsonObject.getJSONArray("data");
                                        for(int i=0;i<dataArray.length();i++){
                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            MyApplication.user_id= dataobj.getString("id");
                                        }
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Log.d("Response Sendback", "error");
                                        String msg = jsonObject.getString("msg");
                                        tv_notation.setText(msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error",error.toString());
                                Toast.makeText(LoginActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError{
                        Map<String,String> paramMap = new HashMap<>();
                        paramMap.put("email",email);
                        paramMap.put("pwd",password);
                        return paramMap;
                    }
                };
                mQueue.add(stringRequest);
            }
        });

        bt_sign_up = findViewById(R.id.bt_login_sign_up);
        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity1.class);
                startActivity(intent);
            }
        });

        bt_forget_pwd = findViewById(R.id.bt_login_forgetpwd);
        bt_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login(){
        String URLline = Constants.LOGIN_AUTO_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("code").equals("200")) {
                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    usr_id = dataobj.getString("id");
                                }
                                Log.d("Response Sendback", usr_id);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                        Toast.makeText(LoginActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);
    }
}