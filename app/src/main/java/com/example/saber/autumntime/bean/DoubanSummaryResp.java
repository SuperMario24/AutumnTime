package com.example.saber.autumntime.bean;

import java.util.List;

/**
 * Created by saber on 2017/7/31.
 */

public class DoubanSummaryResp {

    private String count;
    private List<DoubanTitle> posts;
    private int offset;
    private String date;
    private int total;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DoubanTitle> getPosts() {
        return posts;
    }

    public void setPosts(List<DoubanTitle> posts) {
        this.posts = posts;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
