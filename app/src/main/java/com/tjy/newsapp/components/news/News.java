package com.tjy.newsapp.components.news;

import android.util.Log;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// 新闻数据，不含正文
public class News extends SugarRecord<News> {
    private String title;
    private String image;
    private String video;
    private Date publishTime;
    private String organization;
    private String publisher;
    private String category;
    private String newsID;
    private boolean hasRead;

    public News() {
        hasRead = false;
    }

    @Override
    public int hashCode() {
        return newsID.hashCode();
    }

    // 从字符串中提取 url
    private static String extractUrl(String urls) {
        urls = urls.trim();
        if(urls.startsWith("[") && urls.endsWith("]")) {
            urls = urls.substring(1, urls.length() - 1);
        }
        String[] strings = urls.split(",");
        for(String url: strings) {
            if(url.isBlank()) continue;
            return url.trim();
        }
        return "";
    }

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public News setImage(String image) {
        this.image = extractUrl(image);
        return this;
    }

    public String getVideo() {
        return video;
    }

    public News setVideo(String video) {
        this.video = extractUrl(video);
        return this;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public News setPublishTime(String publishTime) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            this.publishTime = format.parse(publishTime);
        } catch (ParseException e) {
            Log.e("NEWS", "Failed to parse date string.");
        }
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public News setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public News setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public News setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getNewsID() {
        return newsID;
    }

    public News setNewsID(String newsID) {
        this.newsID = newsID;
        return this;
    }

    public boolean hasRead() {
        return hasRead;
    }

    public News setRead(boolean hasRead) {
        this.hasRead = hasRead;
        return this;
    }
}
