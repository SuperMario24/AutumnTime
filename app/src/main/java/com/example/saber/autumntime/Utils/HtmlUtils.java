package com.example.saber.autumntime.Utils;

import android.util.Log;

/**
 * Created by saber on 2017/3/22.
 */

public class HtmlUtils {

    /**
     * 生成知乎日报的html,加上获取的css样式
     */
    public static String createZhihuHtml(String html){

        html = html.replace("<div class=\"img-place-holder\">","");
        html = html.replace("<div class=\"headline\">","");

        //在API中，css的地址给出，复制到本地拼接

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu.css\" type=\"text/css\">";
        String theme = "<body classname=\"\" onload=\"onLoaded()\">";

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<head>\n");
        sb.append("\t<meta charset=\"utf-8\" />");
        sb.append(css);
        sb.append("\n</head>\n");
        sb.append(theme);
        sb.append(html);
        sb.append("</body></html>");
        Log.d("info", "createZhihuHtml:"+sb.toString());
        return sb.toString();
    }



}
