package com.example.saber.autumntime.Views;

import com.example.saber.autumntime.bean.HotNew;

import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public interface ILoadView {
    /**
     * 显示知乎新闻列表
     * @param hotNews
     */
    public void showZhihuNews(List<HotNew> hotNews);

    /**
     * 显示前一天知乎新闻列表
     * @param hotNews
     */
    public void showBeforeZhihuNews(List<HotNew> hotNews);
    

}
