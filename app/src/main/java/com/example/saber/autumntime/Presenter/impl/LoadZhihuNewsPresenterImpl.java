package com.example.saber.autumntime.Presenter.impl;

import com.example.saber.autumntime.Callback.ZhihuNewsCallback;
import com.example.saber.autumntime.Callback.ZhihuNewsContentCallback;
import com.example.saber.autumntime.Model.ILoadBitmapModel;
import com.example.saber.autumntime.Model.ILoadZhihuNewsModel;
import com.example.saber.autumntime.Model.Impl.LoadZhihuNewsModelImpl;
import com.example.saber.autumntime.Presenter.ILoadZhihuPresenter;
import com.example.saber.autumntime.Views.ILoadView;
import com.example.saber.autumntime.Views.ILoadZhihuContentView;
import com.example.saber.autumntime.bean.HotNew;
import com.example.saber.autumntime.bean.HotNewContent;

import java.util.List;

/**
 * Created by saber on 2017/3/20.
 * 在presenter的实现类里创建model，在其实现的接口中调model获取数据的方法
 */

public class LoadZhihuNewsPresenterImpl implements ILoadZhihuPresenter{

    private ILoadView view;
    private ILoadZhihuContentView displayView;
    private ILoadZhihuNewsModel model;
    private ILoadBitmapModel bitmapModel;

    public LoadZhihuNewsPresenterImpl(ILoadView view) {
        this.view = view;
        model = new LoadZhihuNewsModelImpl();
    }

    public LoadZhihuNewsPresenterImpl(ILoadZhihuContentView displayView) {
        this.displayView = displayView;
        model = new LoadZhihuNewsModelImpl();
    }
    /**
     * 获取知乎日报标题，在FragmentZhihu中显示
     */
    @Override
    public void loadZhihuNewsPresenter() {
        model.loadZhihuNewsModel(new ZhihuNewsCallback() {

            @Override
            public void onZhihuNewsLoaded(List<HotNew> hotNews) {
                view.showZhihuNews(hotNews);
            }

        });
    }

    /**
     * 获取知乎日报的内容,在DisPlayActivity中显示
     */
    @Override
    public void loadZhihuContent(int id) {
        model.loadZhihuNewsContent(id, new ZhihuNewsContentCallback() {
            @Override
            public void onZhihuNewsContentLoaded(HotNewContent hotNewContent) {
                displayView.showZhihuNewsContent(hotNewContent);
            }
        });
    }
    /**
     * 获取知乎日报以往的新闻
     */
    @Override
    public void loadZhihuNewsBefore(long time) {
        model.loadZhihuNewsBefore(time, new ZhihuNewsCallback() {
            @Override
            public void onZhihuNewsLoaded(List<HotNew> hotNews) {
                view.showBeforeZhihuNews(hotNews);
            }
        });
    }


}
