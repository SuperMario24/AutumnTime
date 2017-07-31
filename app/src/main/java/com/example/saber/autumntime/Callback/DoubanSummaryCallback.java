package com.example.saber.autumntime.Callback;

import com.example.saber.autumntime.bean.DoubanSummaryResp;

/**
 * Created by saber on 2017/7/31.
 */

public interface DoubanSummaryCallback {

    void onDoubanSummaryLoaded(DoubanSummaryResp doubanSummaryResp);

    void onDoubanSummaryLoadedError();

}
