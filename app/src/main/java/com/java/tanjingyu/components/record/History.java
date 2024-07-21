package com.java.tanjingyu.components.record;

import com.java.tanjingyu.components.newsprovider.NewsProviderDb;
import com.orm.SugarRecord;

@SuppressWarnings("all")
public class History extends SugarRecord<History> implements NewsProviderDb.HasNewsId {
    private String newsId;

    public History() {}

    public History(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return newsId;
    }
}
