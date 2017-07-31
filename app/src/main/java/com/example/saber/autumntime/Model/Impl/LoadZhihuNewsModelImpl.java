package com.example.saber.autumntime.Model.Impl;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.saber.autumntime.Application.MyApplication;
import com.example.saber.autumntime.Callback.ZhihuNewsCallback;
import com.example.saber.autumntime.Callback.ZhihuNewsContentCallback;
import com.example.saber.autumntime.Model.ILoadZhihuNewsModel;
import com.example.saber.autumntime.Utils.GlobalConsts;
import com.example.saber.autumntime.bean.HotNew;
import com.example.saber.autumntime.bean.HotNewContent;
import com.example.saber.autumntime.bean.HotNewResp;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class LoadZhihuNewsModelImpl implements ILoadZhihuNewsModel{

    private MyApplication app;
    protected RequestQueue requestQueue;
    private HotNewResp hotNewResp;

    public LoadZhihuNewsModelImpl(){
        app = MyApplication.getApp();

    }

    @Override
    public void loadZhihuNewsModel(final ZhihuNewsCallback callback) {
        //创建请求队列
        requestQueue = app.getRequestQueue();
        String url = GlobalConsts.ZHIHUNEWS_TITLE_URL;

        //创建StringRequest对象
        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("FragmentZhihu", s);
                //s就是返回的json格式字符串，解析json
                //--解析json  封装成List<Book>
                Gson gson = new Gson();
                hotNewResp = gson.fromJson(s,HotNewResp.class);
                Log.d("info", hotNewResp.toString());
                List<HotNew> hotNews = new ArrayList<>();
                hotNews = hotNewResp.getStories();


                //执行回调接口，在主线程中执行
                callback.onZhihuNewsLoaded(hotNews);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onZhihuNewsLoadedFailed();
            }

        });
        //把StringRequest添加到请求队列RequestQueue
        requestQueue.add(sr);
    }

    /**
     * 获取知乎日报内容
     * @param callback
     */
    @Override
    public void loadZhihuNewsContent(int id, final ZhihuNewsContentCallback callback) {
        //创建请求队列
        requestQueue = app.getRequestQueue();

        String url = GlobalConsts.ZHIHUNEWS_CONTENT_URL+id;

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                //返回一条数据
                HotNewContent hotNewContent = gson.fromJson(s,HotNewContent.class);

                callback.onZhihuNewsContentLoaded(hotNewContent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onZhihuNewsContentLoadedError();
            }

         });

        //把StringRequest添加到请求队列RequestQueue
        requestQueue.add(sr);

    }

    /**
     * 获取知乎日报以往的标题
     * @param timeInMillis
     * @param callback
     */
    @Override
    public void loadZhihuNewsBefore(long timeInMillis, final ZhihuNewsCallback callback) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(timeInMillis);
        String time = sdf.format(date);

        String url = GlobalConsts.ZHIHUNEWS_BRFORE_URL +time;

        RequestQueue requestQueue = MyApplication.getRequestQueue();
        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                hotNewResp = gson.fromJson(s,hotNewResp.getClass());
                List<HotNew> hotNews = new ArrayList<>();
                hotNews = hotNewResp.getStories();

                callback.onZhihuNewsLoaded(hotNews);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onZhihuNewsLoadedFailed();
            }
        });

     requestQueue.add(sr);


    }
}
