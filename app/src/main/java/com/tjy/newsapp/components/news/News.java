package com.tjy.newsapp.components.news;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class News {
    private String title;
    private Image image;
    private Video video;
    private NewsContent content;
    private Date publishTime;
    private String organization;
    private String publisher;
    private String category;
    private String newsID;

    public News() {}

    @Override
    public int hashCode() {
        return newsID.hashCode();
    }

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public News setImage(String image) {
        this.image = new Image(image);
        return this;
    }

    public Video getVideo() {
        return video;
    }

    public News setVideo(String video) {
        this.video = new Video(video);
        return this;
    }

    public NewsContent getContent() {
        return content;
    }

    public News setContent(String content) {
        this.content = new NewsContent(content);
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
}
