package com.tjy.newsapp.components.newsview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tjy.newsapp.components.news.News;

import java.util.List;

// 展示新闻
public class NewsRecyclerView extends RecyclerView implements NewsProviderHandler.OnNewsUpdateListener {

    public NewsRecyclerView(@NonNull Context context) {
        super(context);
    }

    public void setNewsProvider() {

    }

    @Override
    public void onNewsUpdate(List<News> news) {

    }
}
