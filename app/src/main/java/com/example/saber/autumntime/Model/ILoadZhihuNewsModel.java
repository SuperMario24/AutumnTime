package com.example.saber.autumntime.Model;

import com.example.saber.autumntime.Callback.ZhihuNewsCallback;
import com.example.saber.autumntime.Callback.ZhihuNewsContentCallback;

/**
 * Created by saber on 2017/3/20.
 * model层的接口中一般都包括回调接口
 */

public interface ILoadZhihuNewsModel {
    /**
     * 获取知乎日报的标题
     * @param callback
     */
    public void loadZhihuNewsModel(ZhihuNewsCallback callback);

    /**
     * 获取知乎日报的内容
     */
    public  void loadZhihuNewsContent(int id,ZhihuNewsContentCallback callback);

    /**
     * 获取知乎日报以往的标题
     */
    public void loadZhihuNewsBefore(long time,ZhihuNewsCallback callback);
}
