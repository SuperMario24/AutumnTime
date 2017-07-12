package com.example.saber.autumntime.Presenter;

/**
 * Created by saber on 2017/3/20.
 */

public interface ILoadZhihuPresenter {
    /**
     * 获取知乎日报
     */
    public void loadZhihuNewsPresenter();

    /**
     * 获取知乎日报内容
     */
    public void loadZhihuContent(int id);

    /**
     * 获取知乎日报以前的新闻
     * @param time
     */
    public void loadZhihuNewsBefore(long time);
}
