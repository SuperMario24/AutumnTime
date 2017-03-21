package com.example.saber.autumntime.Presenter.impl;

import android.graphics.Bitmap;

import com.example.saber.autumntime.Callback.BitmapCallback;
import com.example.saber.autumntime.Model.ILoadBitmapModel;
import com.example.saber.autumntime.Model.Impl.LoadBitmapModelImpl;
import com.example.saber.autumntime.Presenter.ILoadBitmapPresenter;
import com.example.saber.autumntime.Views.ILoadView;

/**
 * Created by saber on 2017/3/21.
 */

public class LoadBitmapPresenterImpl implements ILoadBitmapPresenter {

    private ILoadView view;
    private ILoadBitmapModel loadBitmapModel;

    public LoadBitmapPresenterImpl(ILoadView view) {
        this.view = view;
        loadBitmapModel = new LoadBitmapModelImpl();
    }

    @Override
    public void loadBitmap(String url) {
        loadBitmapModel.loadBitmap(url, new BitmapCallback() {
            @Override
            public void onBitmapLoaded(Bitmap bm) {
                view.showZhihuImage(bm);
            }
        });
    }
}
