package com.example.saber.autumntime.Callback;

import com.example.saber.autumntime.bean.HotNew;

import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public interface ZhihuNewsCallback {

   /**
    * 获取标题
    * @param hotNews
     */
   public void onZhihuNewsLoaded(List<HotNew> hotNews);

    /**
     * 加载失败
     */
   void onZhihuNewsLoadedFailed();

}
