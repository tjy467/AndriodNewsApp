package com.java.tanjingyu.components.record;

import com.java.tanjingyu.components.newsprovider.NewsProviderDb;
import com.orm.SugarRecord;

// 收藏
public class Star extends SugarRecord<Star> implements NewsProviderDb.HasNewsId {
    private String newsId;

    @SuppressWarnings("all")
    public Star() {}

    public Star(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return newsId;
    }
}
