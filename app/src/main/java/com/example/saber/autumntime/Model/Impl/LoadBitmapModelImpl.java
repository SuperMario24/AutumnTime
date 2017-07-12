package com.example.saber.autumntime.Model.Impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.saber.autumntime.Application.MyApplication;
import com.example.saber.autumntime.Callback.BitmapCallback;
import com.example.saber.autumntime.Model.ILoadBitmapModel;
import com.example.saber.autumntime.Utils.BitmapUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by saber on 2017/3/21.
 */

public class LoadBitmapModelImpl implements ILoadBitmapModel{

    private static final String TAG = "LoadBitmapModelImpl";

    private RequestQueue requestQueue;

    @Override
    public void loadBitmap(String url, final BitmapCallback callback) {

        requestQueue = MyApplication.getRequestQueue();

        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //加载图片
                callback.onBitmapLoaded(bitmap);
            }
        },0,0,Bitmap.Config.RGB_565,new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(ir);
    }

    @Override
    public void loadBitmap(final Bitmap bm, final BitmapCallback callback) {
        BitmapUtils.loadBlurBitmap(bm, 8, new BitmapCallback() {
            @Override
            public void onBitmapLoaded(Bitmap bm) {
                callback.onBitmapLoaded(bm);
            }

            @Override
            public void onBitmapCompressed(Bitmap bm) {

            }
        });
    }

    @Override
    public void compressBitmap(final Bitmap bm, final BitmapCallback callback) {
        AsyncTask<Void,Void,Bitmap> task = new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = compressBitmap(bm);
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                callback.onBitmapCompressed(bitmap);
            }
        };
        task.execute();
    }

    /**
     * 压缩bitmap
     * @param bitmap
     * @return
     */
    private Bitmap compressBitmap(Bitmap bitmap) {
        /**
         * 对质量进行压缩
         */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);//这里压缩50%，把压缩后的数据存放到baos中，无法通过此方法对图片无限压缩，直到多少kb
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());//图片转化为字节流了

        //开始执行采样率缩放
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//开始读入图片，此时把options.inJustDecodeBounds 设回true了
        Bitmap bm = BitmapFactory.decodeStream(bais,null,options);
        options.inJustDecodeBounds = false;

        int width = options.outWidth;
        int height = options.outHeight;

        float hh = 1280f;
        float ww = 720f;

        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;
        if(width > height && width > ww){//如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth/ww);
        }else if(width < height && height > hh){//如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight/hh);
        }
        if(be <= 1){
            be = 1;
        }

        options.inSampleSize = be;//设置缩放比例

        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bais = new ByteArrayInputStream(baos.toByteArray());
        bm = BitmapFactory.decodeStream(bais,null,options);

        return bm;
    };

    /**
     * 保存Bitmap
     * @param bitmap
     */
    @Override
    public void saveBitmap(Context context, final Bitmap bitmap) {
        //TODO
        final File file = new File(context.getExternalCacheDir(),"avatar.jpg");
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

                InputStream inputStream = bais;
                FileOutputStream fos = null;
                //写入文件
                byte[] buf = new byte[1024*8];
                int len = 0;
                try {
                    fos = new FileOutputStream(file);
                    while((len = inputStream.read(buf)) != -1){
                        fos.write(buf,0,len);
                    }
                    fos.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                        if(inputStream != null){
                            inputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG,"save bitmap success");

            }
        }.start();



    }

}
