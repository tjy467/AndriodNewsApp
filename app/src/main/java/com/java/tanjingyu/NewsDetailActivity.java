package com.java.tanjingyu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.java.tanjingyu.components.news.News;

import java.util.List;

public class NewsDetailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_news_detail);
        Intent intent = getIntent();
        String newsId = intent.getStringExtra("newsId");
        List<News> list = News.find(News.class, "news_id = ?", newsId);
        assert list.size() == 1;
        News news = list.get(0);
        setAttributes(news);
    }

    private void setAttributes(News news) {
        TextView detailTitle = findViewById(R.id.detail_title);
        detailTitle.setText(news.getTitle());
    }
}
