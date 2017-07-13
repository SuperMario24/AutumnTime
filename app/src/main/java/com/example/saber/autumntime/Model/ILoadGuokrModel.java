package com.example.saber.autumntime.Model;

import com.example.saber.autumntime.Callback.GuokrContentCallback;
import com.example.saber.autumntime.Callback.GuokrSummaryCallback;

/**
 * Created by saber on 2017/7/13.
 */

public interface ILoadGuokrModel {

    /**
     * 获取果壳精选的标题
     * @param callback
     */
    public void loadGuokrSummary(GuokrSummaryCallback callback);

    /**
     * 获取果壳精选的内容
     * @param callback
     */
    public void loadGuokrContent(int id,GuokrContentCallback callback);

}
