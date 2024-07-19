package com.java.tanjingyu.components.news;

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
    private String content;
    private String image;
    private String video;
    private Date publishTime;
    private String organization;
    private String publisher;
    private String category;
    private String newsId;

    public News() {}

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

    public String getContent() {
        return content;
    }

    public News setContent(String content) {
        this.content = content;
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
        this.video = video.trim();
        return this;
    }

    static private final long HOUR = 60 * 60 * 1000;
    static private final long DAY = 24 * HOUR;

    // 根据时间差返回显示的时间
    public String getFormatPublishTime() {
        long delta = new Date().getTime() - publishTime.getTime();
        if(delta < DAY) {
            int count = (int) Math.ceil((double) delta / HOUR);
            return count + "小时前";
        } else {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return format.format(publishTime);
        }
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

    public String getNewsId() {
        return newsId;
    }

    public News setNewsId(String newsId) {
        this.newsId = newsId;
        return this;
    }

    public boolean hasRead() {
        long count = News.count(News.class, "news_id = ?", new String[] { newsId });
        return count > 0;
    }
}
