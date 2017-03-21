package com.example.saber.autumntime.Presenter.impl;

import com.example.saber.autumntime.Callback.ZhihuNewsCallback;
import com.example.saber.autumntime.Model.Impl.LoadZhihuNewsModelImpl;
import com.example.saber.autumntime.Presenter.ILoadZhihuPresenter;
import com.example.saber.autumntime.Views.ILoadView;

import com.example.saber.autumntime.Model.ILoadBitmapModel;
import com.example.saber.autumntime.Model.ILoadZhihuNewsModel;
import com.example.saber.autumntime.bean.HotNew;

import java.util.List;

/**
 * Created by saber on 2017/3/20.
 * 在presenter的实现类里创建model，在其实现的接口中调model获取数据的方法
 */

public class LoadZhihuNewsPresenterImpl implements ILoadZhihuPresenter{

    private ILoadView view;
    private ILoadZhihuNewsModel model;
    private ILoadBitmapModel bitmapModel;

    public LoadZhihuNewsPresenterImpl(ILoadView view){
        this.view = view;
        model = new LoadZhihuNewsModelImpl();
    }

    /**
     * 获取知乎日报
     *
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
}
