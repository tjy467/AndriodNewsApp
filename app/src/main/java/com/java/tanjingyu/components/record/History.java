package com.java.tanjingyu.components.record;

import com.java.tanjingyu.components.newsprovider.NewsProviderDb;
import com.orm.SugarRecord;

// 历史记录
public class History extends SugarRecord<History> implements NewsProviderDb.HasNewsId {
    private String newsId;

    @SuppressWarnings("all")
    public History() {}

    public History(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return newsId;
    }
}
