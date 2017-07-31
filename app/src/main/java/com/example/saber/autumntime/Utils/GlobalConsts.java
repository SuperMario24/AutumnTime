package com.example.saber.autumntime.Utils;

/**
 * Created by saber on 2017/3/20.
 */

public class GlobalConsts {

    //拍摄照片的requestCode
    public static final int TAKE_PHOTO = 1;
    //选择照片的requestCode
    public static final int SELECT_PHOTO = 2;

    //知乎的最新消息
    public static final String ZHIHUNEWS_TITLE_URL = "http://news-at.zhihu.com/api/4/news/latest";
    //知乎消息的具体内容
    public static final String ZHIHUNEWS_CONTENT_URL = "http://news-at.zhihu.com/api/4/news/";
    //知乎的过往消息
    public static final String ZHIHUNEWS_BRFORE_URL = "http://news-at.zhihu.com/api/4/news/before/";
    //果壳精选
    public static final String GUOKR_ARTICLES = "http://apis.guokr.com/handpick/article.json?retrieve_type=by_since&category=all&limit=25&ad=1";
    //果壳精选内容
    public static final String GUOKR_CONTENT_URL = "http://jingxuan.guokr.com/pick/";
    // 豆瓣一刻
    // 根据日期查询消息列表
    // eg:https://moment.douban.com/api/stream/date/2016-08-11
    public static final String DOUBAN_MOMENT_URL = "https://moment.douban.com/api/stream/date/";

    // 获取文章具体内容
    // eg:https://moment.douban.com/api/post/100484
    public static final String DOUBAN_ARTICLE_DETAIL = "https://moment.douban.com/api/post/";

    //新闻类型0-知乎，1-果壳，2-豆瓣
    public static final int TYPE_ZHIHU = 0;
    public static final int TYPE_GUOKR = 1;
    public static final int TYPE_DOUBAN = 2;

}
