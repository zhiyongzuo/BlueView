package com.example.zuo81.blueview.model;

/**
 * Created by zuo81 on 2017/12/14.
 */

public class PageModel {
    String title;
    int res;

    public PageModel(String title, int res) {
        this.title = title;
        this.res = res;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
