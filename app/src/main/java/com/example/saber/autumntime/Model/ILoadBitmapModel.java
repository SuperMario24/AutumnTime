package com.example.saber.autumntime.Model;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.saber.autumntime.Callback.BitmapCallback;

/**
 * Created by saber on 2017/3/20.
 */

public interface ILoadBitmapModel {
    public void loadBitmap(String url,BitmapCallback callback);
    public void loadBitmap(Bitmap bm, BitmapCallback callback);

    public void compressBitmap(Bitmap bm,BitmapCallback callback);
    //保存Bitmap
    void saveBitmap(Context context,Bitmap bitmap);
}
