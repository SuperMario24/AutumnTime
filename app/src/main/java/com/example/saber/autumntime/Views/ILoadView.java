package com.example.saber.autumntime.Views;

import android.graphics.Bitmap;

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
     * 显示知乎新闻图片
     */
    public void showZhihuImage(Bitmap bitmap);

}
