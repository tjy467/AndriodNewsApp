package com.java.tanjingyu.components.record;

import com.java.tanjingyu.components.newsprovider.NewsProviderDb;
import com.orm.SugarRecord;

@SuppressWarnings("all")
public class Star extends SugarRecord<Star> implements NewsProviderDb.HasNewsId {
    private String newsId;

    public Star() {}

    public Star(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return newsId;
    }
}
