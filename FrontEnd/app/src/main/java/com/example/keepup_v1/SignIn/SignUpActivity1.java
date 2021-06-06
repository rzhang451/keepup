package com.example.keepup_v1.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.example.keepup_v1.R;
import com.example.keepup_v1.funcs.ConnectQueue;
import com.example.keepup_v1.http.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity1 extends AppCompatActivity {
    public static String email;

    private TextView tv_keepup;
    public EditText et_email;
    private Button bt_code;
    private EditText et_code;
    private Button bt_next;
    private Button bt_signIn;
    private TextView tv_notation;

    RequestQueue mQueue = MyApplication.requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        tv_keepup = findViewById(R.id.tv_signup_keepup);
        tv_keepup.setTypeface(Typeface.createFromAsset(getAssets(),"font/Krungthep.ttf"));
        tv_keepup.setEnabled(false);

        et_email = findViewById(R.id.et_signup_email);
        et_code = findViewById(R.id.et_signup_code);

        bt_code = findViewById(R.id.bt_signup_code);
        bt_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                if(email!=null){
                    getCode(email);
                }
            }
        });

        tv_notation = findViewById(R.id.tv_signup_notion);
        tv_notation.setText(null);

        bt_next = findViewById(R.id.bt_signup_next);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                final String code = et_code.getText().toString();
                if((email!=null)&&(code!=null)){
                    nextStep(email,code);
                }
            }
        });

        bt_signIn = findViewById(R.id.bt_signup_signIn);
        bt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity1.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getCode(String email){
        String URLline = Constants.SIGN_UP_EMAIL_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            tv_notation.setText(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity1.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("email",email);
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }

    private void nextStep(String email, String code){
        String URLline = Constants.SIGN_UP_CODE_URL;
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("code").equals("200")){
                                String msg = jsonObject.getString("msg");
                                tv_notation.setText(msg);
                                Intent intent = new Intent(SignUpActivity1.this, SignUpActivity2.class);
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
                        Toast.makeText(SignUpActivity1.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("email",email);
                paramMap.put("code",code);
                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }
}
