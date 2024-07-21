package com.java.tanjingyu.components.record;

import com.orm.SugarRecord;

@SuppressWarnings("all")
public class History extends SugarRecord<History> {
    private String newsID;

    public History() {}

    public History(String newsID) {
        this.newsID = newsID;
    }
}
