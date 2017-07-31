package com.example.saber.autumntime.Model;

import com.example.saber.autumntime.Callback.DoubanSummaryCallback;

/**
 * Created by saber on 2017/7/31.
 */

public interface ILoadDoubanSummaryModel {

    void loadDoubanSummary(String date, DoubanSummaryCallback callback);

}
