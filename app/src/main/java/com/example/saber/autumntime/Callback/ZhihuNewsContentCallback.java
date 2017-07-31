package com.example.saber.autumntime.Callback;

import com.example.saber.autumntime.bean.HotNewContent;

/**
 * Created by saber on 2017/3/21.
 */

public interface ZhihuNewsContentCallback {
    /**
     * 获取内容
     */
    public void onZhihuNewsContentLoaded(HotNewContent hotNewContent);

    /**
     * 加载失败
     */
    void onZhihuNewsContentLoadedError();

}
