package com.java.tanjingyu.components.record;

import com.orm.SugarRecord;

@SuppressWarnings("all")
public class Star extends SugarRecord<Star> {
    private String newsID;

    public Star() {}

    public Star(String newsID) {
        this.newsID = newsID;
    }
}
