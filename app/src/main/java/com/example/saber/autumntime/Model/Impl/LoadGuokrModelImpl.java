package com.example.saber.autumntime.Model.Impl;

import android.util.Log;

import com.example.saber.autumntime.Callback.GuokrContentCallback;
import com.example.saber.autumntime.Callback.GuokrSummaryCallback;
import com.example.saber.autumntime.Model.ILoadGuokrModel;
import com.example.saber.autumntime.Utils.GlobalConsts;
import com.example.saber.autumntime.bean.GuokrSummaryResp;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by saber on 2017/7/13.
 */

public class LoadGuokrModelImpl implements ILoadGuokrModel {

    private static final String TAG = "LoadGuokrModelImpl";

    private GuokrSummaryResp guokrSummaryResp;

    @Override
    public void loadGuokrSummary(final GuokrSummaryCallback callback) {

        final String url = GlobalConsts.GUOKR_ARTICLES;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "load GuokrSummary Failed");
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String reponseData = response.body().string();
                Log.d(TAG, "GuokrSummary:" + reponseData);

                Gson gson = new Gson();

                guokrSummaryResp = gson.fromJson(reponseData, GuokrSummaryResp.class);

                callback.onGuokrSummaryLoaded(guokrSummaryResp);
            }
        });
    }

    /**
     * 获取果壳精选的详细内容
     * @param callback
     */
    @Override
    public void loadGuokrContent(int id, final GuokrContentCallback callback) {

        String url = GlobalConsts.GUOKR_CONTENT_URL + id;

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String reponseData = response.body().string();
                Log.d(TAG, "GuokrContent:" + reponseData);

                callback.onGuokrContentLoaded(reponseData);

            }
        });


    }
}
