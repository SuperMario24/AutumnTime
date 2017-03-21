package com.example.saber.autumntime.Model;

import com.example.saber.autumntime.Callback.ZhihuNewsCallback;

/**
 * Created by saber on 2017/3/20.
 * model层的接口中一般都包括回调接口
 */

public interface ILoadZhihuNewsModel {
    public void loadZhihuNewsModel(ZhihuNewsCallback callback);
}
