package com.example.keepup_v1.funcs;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.keepup_v1.MyApplication;

public class ConnectQueue extends Application {
    public static RequestQueue requestQueue;

    @Override
    public void onCreate(){
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
    }
}
