package com.example.saber.autumntime.Presenter.impl;

import com.example.saber.autumntime.Callback.DoubanSummaryCallback;
import com.example.saber.autumntime.Model.ILoadDoubanSummaryModel;
import com.example.saber.autumntime.Model.Impl.LoadDoubanModelImpl;
import com.example.saber.autumntime.Presenter.ILoadDoubanPresenter;
import com.example.saber.autumntime.Views.ILoadDoubanSummaryView;
import com.example.saber.autumntime.bean.DoubanSummaryResp;

/**
 * Created by saber on 2017/7/31.
 */

public class LoadDoubanPresenterImpl implements ILoadDoubanPresenter{

    private static final String TAG = "LoadDoubanPresenterImpl";

    private ILoadDoubanSummaryView view;
    private ILoadDoubanSummaryModel loadDoubanSummaryModel;

    public LoadDoubanPresenterImpl(ILoadDoubanSummaryView view) {
        this.view = view;
        loadDoubanSummaryModel = new LoadDoubanModelImpl();
    }


    @Override
    public void loadDoubanSummary(String date) {
        loadDoubanSummaryModel.loadDoubanSummary(date, new DoubanSummaryCallback() {
            @Override
            public void onDoubanSummaryLoaded(DoubanSummaryResp doubanSummaryResp) {
                view.onDoubanSummaryLoaded(doubanSummaryResp);
            }

            @Override
            public void onDoubanSummaryLoadedError() {
                view.onLoadedError();
            }
        });
    }
}
