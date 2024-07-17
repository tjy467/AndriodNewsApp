package com.tjy.newsapp.components.newsview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tjy.newsapp.components.news.News;

import java.util.List;

public class NewsView extends NewsRecyclerView implements NewsProviderHandler.OnNewsUpdateListener {

    public NewsView(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    // 插入抓取的新闻
    @Override
    public void onNewsUpdate(List<News> newsList, NewsProviderHandler.UpdateType type) {
        Log.i("NewsView", "news update callback called with size: " + String.valueOf(newsList.size()));
        for(News news: newsList) {
            this.newsList.add(news);
            adapter.notifyItemInserted(adapter.getItemCount());
        }
    }
}
