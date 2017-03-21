package com.example.saber.autumntime.Application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by saber on 2017/3/20.
 */

public class MyApplication extends Application {
    private static MyApplication app;

    private static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        requestQueue = Volley.newRequestQueue(this);

    }

    public static MyApplication getApp(){
        return app;
    }
    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }

}
