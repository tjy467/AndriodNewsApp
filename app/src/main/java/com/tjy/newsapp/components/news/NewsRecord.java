package com.tjy.newsapp.components.news;

import com.orm.SugarRecord;

// 关联新闻 ID 与正文，存储在数据库中
public class NewsRecord extends SugarRecord<NewsRecord> {
    public String newsID;
    public String content;

    public NewsRecord() {}

    private static String parseContent(String content) {
        return content;
    }

    public NewsRecord(String newsID, String content) {
        this.newsID = newsID;
        this.content = parseContent(content);
    }
}
