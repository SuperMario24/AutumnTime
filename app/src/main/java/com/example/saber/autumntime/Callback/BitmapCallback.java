package com.example.saber.autumntime.Callback;

import android.graphics.Bitmap;

/**
 * Created by saber on 2017/3/20.
 */

public interface BitmapCallback {
    public void onBitmapLoaded(Bitmap bm);
    public void onBitmapCompressed(Bitmap bm);
}
