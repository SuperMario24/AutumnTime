package com.example.saber.autumntime.Views;

import com.example.saber.autumntime.bean.DoubanSummaryResp;

/**
 * Created by saber on 2017/7/31.
 */

public interface ILoadDoubanSummaryView {

    void onDoubanSummaryLoaded(DoubanSummaryResp doubanSummaryResp);

    void onDoubanSummaryLoadedError();
    /**
     * 显示加载状态
     */
    void showLoading();

    /**
     * 加载成功
     */
    void onLoadedSuccess();

    /**
     * 加载失败
     */
    void onLoadedError();

}
