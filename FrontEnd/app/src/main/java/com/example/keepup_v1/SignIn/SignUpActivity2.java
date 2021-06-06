package com.example.keepup_v1.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.keepup_v1.MyApplication;
import com.example.keepup_v1.activity.MainActivity;
import com.example.keepup_v1.R;
import com.example.keepup_v1.bean.UserInfo;
import com.example.keepup_v1.funcs.ConnectQueue;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity2 extends AppCompatActivity {
    public static String usr_id = MyApplication.user_id;

    private EditText et_pwd1;
    private EditText et_pwd2;
    private Button bt_signUp;
    private TextView tv_notation;

    RequestQueue mQueue = MyApplication.requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        et_pwd1 = findViewById(R.id.et_signup_password1);
        et_pwd2 = findViewById(R.id.et_signup_password2);

        tv_notation = findViewById(R.id.tv_signup_notion2);
        tv_notation.setText(null);

        bt_signUp = findViewById(R.id.bt_signup_signUp);
        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pwd1 = et_pwd1.getText().toString();
                final String pwd2 = et_pwd2.getText().toString();
                final String email = SignUpActivity1.email;
                if((pwd1!=null)&&(pwd2!=null)){
                    String URLline = Constants.SIGN_UP_PWD_URL;
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
                                                MyApplication.user_id = dataobj.getString("id");
                                            }
                                            Intent intent = new Intent(SignUpActivity2.this, MainActivity.class);
                                            startActivity(intent);
                                        }else{
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
                                    Toast.makeText(SignUpActivity2.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams() throws AuthFailureError {
                            Map<String,String> paramMap = new HashMap<>();
                            paramMap.put("pwd",pwd1);
                            paramMap.put("check",pwd2);
                            paramMap.put("email",email);
                            return paramMap;
                        }
                    };
                    mQueue.add(stringRequest);
                }
            }
        });
    }
}