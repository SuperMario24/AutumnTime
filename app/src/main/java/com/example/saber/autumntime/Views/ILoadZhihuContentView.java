package com.example.saber.autumntime.Views;

import com.example.saber.autumntime.bean.HotNewContent;

/**
 * Created by saber on 2017/3/20.
 */

public interface ILoadZhihuContentView {
    /**
     * 显示知乎新闻列表
     * @param hotNewContent
     */
    public void showZhihuNewsContent(HotNewContent hotNewContent);

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
