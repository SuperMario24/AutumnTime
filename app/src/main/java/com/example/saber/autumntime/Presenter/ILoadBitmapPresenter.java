package com.example.saber.autumntime.Presenter;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by saber on 2017/3/20.
 */

public interface ILoadBitmapPresenter {
    //加载图片
    public void loadBitmap(String url);
    public void loadBitmap(Bitmap bm);
    //压缩图片
    public void compressBitmap(Bitmap bitmap);
    //保存图片
    void saveBitmap(Context context,Bitmap bitmap);
}
