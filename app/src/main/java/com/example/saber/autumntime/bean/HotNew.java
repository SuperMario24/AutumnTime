package com.example.saber.autumntime.bean;

import java.util.ArrayList;

/**
 * Created by saber on 2017/3/20.
 */

public class HotNew{

    private ArrayList<String> images;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public HotNew() {

    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }







}
