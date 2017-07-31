package com.example.saber.autumntime.Model.Impl;

import android.util.Log;

import com.example.saber.autumntime.Callback.DoubanSummaryCallback;
import com.example.saber.autumntime.Model.ILoadDoubanSummaryModel;
import com.example.saber.autumntime.Utils.GlobalConsts;
import com.example.saber.autumntime.bean.DoubanSummaryResp;
import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by saber on 2017/7/31.
 */

public class LoadDoubanModelImpl implements ILoadDoubanSummaryModel {

    private static final String TAG = "LoadDoubanModelImpl";
    private DoubanSummaryResp doubanSummaryResp;
    /**
     * 加载豆瓣标题
     * @param date
     * @param callback
     */
    @Override
    public void loadDoubanSummary(String date, final DoubanSummaryCallback callback) {


        String url = GlobalConsts.DOUBAN_MOMENT_URL + date;

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onDoubanSummaryLoadedError();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "DoubanSummary:" + responseData);

                Gson gson = new Gson();
                doubanSummaryResp = gson.fromJson(responseData,DoubanSummaryResp.class);

                callback.onDoubanSummaryLoaded(doubanSummaryResp);

            }
        });

    }
}
