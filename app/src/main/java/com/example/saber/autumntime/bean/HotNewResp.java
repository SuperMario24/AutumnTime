package com.example.saber.autumntime.bean;

import java.util.List;

/**
 * Created by saber on 2017/3/20.
 */

public class HotNewResp {
    private String date;
    private List<HotNew> stories;//名字要和返回的数据一样！
    private List<TopNew> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<HotNew> getStories() {
        return stories;
    }

    public void setStories(List<HotNew> stories) {
        this.stories = stories;
    }

    public List<TopNew> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopNew> top_stories) {
        this.top_stories = top_stories;
    }
}
