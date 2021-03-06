package com.example.saber.autumntime.Views;

/**
 * Created by saber on 2017/7/13.
 */

public interface ILoadGuokrContentView {

    public void onGuokrNewsContent(String data);

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
