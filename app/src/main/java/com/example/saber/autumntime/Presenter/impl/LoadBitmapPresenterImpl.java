package com.example.saber.autumntime.Presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.saber.autumntime.Callback.BitmapCallback;
import com.example.saber.autumntime.Model.ILoadBitmapModel;
import com.example.saber.autumntime.Model.Impl.LoadBitmapModelImpl;
import com.example.saber.autumntime.Presenter.ILoadBitmapPresenter;
import com.example.saber.autumntime.Views.IAvatarView;

/**
 * Created by saber on 2017/3/21.
 */

public class LoadBitmapPresenterImpl implements ILoadBitmapPresenter {

    private IAvatarView view;
    private ILoadBitmapModel loadBitmapModel;

    public LoadBitmapPresenterImpl(IAvatarView view) {
        this.view = view;
        loadBitmapModel = new LoadBitmapModelImpl();
    }

    @Override
    public void loadBitmap(String url) {
        loadBitmapModel.loadBitmap(url, new BitmapCallback() {
            @Override
            public void onBitmapLoaded(Bitmap bm) {

            }

            @Override
            public void onBitmapCompressed(Bitmap bm) {

            }
        });
    }

    @Override
    public void loadBitmap(Bitmap bm) {
        loadBitmapModel.loadBitmap(bm, new BitmapCallback() {
            @Override
            public void onBitmapLoaded(Bitmap bm) {
                view.changeBackground(bm);
            }

            @Override
            public void onBitmapCompressed(Bitmap bm) {

            }
        });
    }

    @Override
    public void compressBitmap(Bitmap bitmap) {
        loadBitmapModel.compressBitmap(bitmap, new BitmapCallback() {
            @Override
            public void onBitmapLoaded(Bitmap bm) {

            }

            //压缩完图片
            @Override
            public void onBitmapCompressed(Bitmap bm) {

                loadBitmapModel.loadBitmap(bm, new BitmapCallback() {
                    //模糊化图片
                    @Override
                    public void onBitmapLoaded(Bitmap bm) {
                        //view回调
                        view.changeBackground(bm);
                    }

                    @Override
                    public void onBitmapCompressed(Bitmap bm) {

                    }
                });
            }
        });
    }

    @Override
    public void saveBitmap(Context context,Bitmap bitmap) {
        loadBitmapModel.saveBitmap(context,bitmap);
    }

}
