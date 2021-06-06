package com.example.keepup_v1;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.keepup_v1.http.Constants;
import com.example.keepup_v1.utils.SPUtil;

public class MyApplication extends Application {
    public static String user_id;
    public static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        SPUtil.init(this, Constants.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);

        //noCrashMethod();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    /**
     * f防止程序崩溃闪退
     */

}
