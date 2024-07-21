package com.java.tanjingyu.components;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.tanjingyu.R;
import com.java.tanjingyu.components.record.News;

import java.util.List;

// 新闻详情页面
public class NewsDetailActivity extends AppCompatActivity {

    // 根据 newsId 从 SQLite 中获取数据
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

    // 设置相关属性
    @SuppressLint("SetTextI18n")
    private void setAttributes(News news) {
        TextView detailTitle = findViewById(R.id.detail_title);
        detailTitle.setText(news.getTitle());
        ImageVideoView detailViewSwitcher = findViewById(R.id.detail_view_switcher);
        detailViewSwitcher.put(news.getImage(), news.getVideo());
        TextView detailPublisher = findViewById(R.id.detail_publisher);
        detailPublisher.setText(news.getPublisher());
        TextView detailPublishTime = findViewById(R.id.detail_publish_time);
        detailPublishTime.setText(news.getFormatPublishTime());
        TextView detailContent = findViewById(R.id.detail_content);
        detailContent.setText(news.getContent());
        TextView detailOrganization = findViewById(R.id.detail_organization);
        String organization = news.getOrganization();
        if(organization.isEmpty()) organization = getString(R.string.string_none);
        detailOrganization.setText(getString(R.string.string_source) + organization);
        TextView detailCategory = findViewById(R.id.detail_category);
        detailCategory.setText(getString(R.string.string_category) + news.getCategory());
    }
}
