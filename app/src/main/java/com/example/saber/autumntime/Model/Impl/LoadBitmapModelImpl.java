package com.example.saber.autumntime.Model.Impl;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.saber.autumntime.Application.MyApplication;
import com.example.saber.autumntime.Callback.BitmapCallback;
import com.example.saber.autumntime.Model.ILoadBitmapModel;

/**
 * Created by saber on 2017/3/21.
 */

public class LoadBitmapModelImpl implements ILoadBitmapModel{

    private RequestQueue requestQueue;

    @Override
    public void loadBitmap(String url, final BitmapCallback callback) {

        requestQueue = MyApplication.getRequestQueue();

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //加载图片
                callback.onBitmapLoaded(bitmap);
            }
        },0,0,Bitmap.Config.RGB_565,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(ir);
    }
}
