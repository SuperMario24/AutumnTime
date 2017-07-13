package com.example.saber.autumntime.Presenter.impl;

import com.example.saber.autumntime.Callback.GuokrContentCallback;
import com.example.saber.autumntime.Callback.GuokrSummaryCallback;
import com.example.saber.autumntime.Model.ILoadGuokrModel;
import com.example.saber.autumntime.Model.Impl.LoadGuokrModelImpl;
import com.example.saber.autumntime.Presenter.ILoadGuokrPresenter;
import com.example.saber.autumntime.Views.ILoadGuokrContentView;
import com.example.saber.autumntime.Views.ILoadGuokrSummaryView;
import com.example.saber.autumntime.bean.GuokrSummaryResp;

/**
 * Created by saber on 2017/7/13.
 */

public class LoadGuokrPresenterImpl implements ILoadGuokrPresenter {

    private ILoadGuokrSummaryView view;
    private ILoadGuokrContentView contentView;
    private ILoadGuokrModel model;

    public LoadGuokrPresenterImpl(ILoadGuokrSummaryView view) {
        this.view = view;
        model = new LoadGuokrModelImpl();
    }

    public LoadGuokrPresenterImpl(ILoadGuokrContentView view) {
        this.contentView = view;
        model = new LoadGuokrModelImpl();
    }



    /**
     * 获取Guokr精选summary
     */
    @Override
    public void loadGuokrSummary() {
        model.loadGuokrSummary(new GuokrSummaryCallback() {
            @Override
            public void onGuokrSummaryLoaded(GuokrSummaryResp guokrSummaryResp) {
                view.onGuokrSummaryLoaded(guokrSummaryResp);
            }
        });
    }

    /**
     * 获取果壳精选的详细内容
     * @param id
     */
    @Override
    public void loadGuokrContent(int id) {
        model.loadGuokrContent(id, new GuokrContentCallback() {
            @Override
            public void onGuokrContentLoaded(String data) {
                contentView.onGuokrNewsContent(data);
            }
        });
    }
}
