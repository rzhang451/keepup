package com.example.keepup_v1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.keepup_v1.base.BaseActivity;
import com.example.keepup_v1.fragment.MeFragment;
import com.example.keepup_v1.http.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyhealthyActivity extends BaseActivity {

    String usr_id=MyApplication.user_id;
    String url=Constants.BASE_URL;
    EditText user_height;
    EditText user_weight;
    EditText user_bmi;
    Button confirm;
    private MeFragment meFragment;
    @Override
    protected int getResourceId() {
        return R.layout.activity_myhealthy;
    }

    private static RequestQueue mQueue = MyApplication.requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_height = findViewById(R.id.user_name);
        user_weight = findViewById(R.id.user_sex);
        user_bmi = findViewById(R.id.user_intro);
        confirm = findViewById(R.id.confirm);
    }

    protected void initView(){
        super.initView();
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            if(StringUtils.getEditTextData(user_name).isEmpty()&&StringUtils.getEditTextData(user_sex).isEmpty()&&StringUtils.getEditTextData(user_intro).isEmpty()&&StringUtils.getEditTextData(user_email).isEmpty()&&StringUtils.getEditTextData(user_address).isEmpty()) {
//               //
//            }
//            else{
//
//                setinfo();
//            }
//            }
//    });
    }
    private void getinfo() {
        String URLline = url + "/profile/id?id="+usr_id;
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
                                    user_height.setHint(dataobj.getString("height"));
                                    user_weight.setHint(dataobj.getString("weight"));

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
                        Toast.makeText(MyhealthyActivity.this, "Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }) ;
        mQueue.add(stringRequest);
    }
    private void setinfo(){
        final String height = user_height.getText().toString();
        final String weight = user_weight.getText().toString();

        String URLline = url + "/profile/health";
        Log.d("Required Server", URLline);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("code").equals("200")){
                                JSONArray dataArray = jsonObject.getJSONArray("data");
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
                        Toast.makeText(MyhealthyActivity.this,"Failed to Connect, Please Try Again", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put(user_height.getText().toString(),height);
                paramMap.put(user_weight.getText().toString(),weight);

                return paramMap;
            }
        };
        mQueue.add(stringRequest);
    }
}
