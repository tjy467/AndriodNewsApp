package com.tjy.newsapp.components.newsview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tjy.newsapp.components.news.News;

public class NewsRecyclerView extends RecyclerView implements NewsProvider.OnNewsInsertListener {
    NewsProvider newsProvider;

    public NewsRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NewsRecyclerView setNewsProvider(NewsProvider newsProvider) {
        this.newsProvider = newsProvider;
        return this;
    }

    @Override
    public void onNewsInsert(News news) {

    }
}
