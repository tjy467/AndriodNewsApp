package com.tjy.newsapp.components.newsview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tjy.newsapp.components.news.ContinuingCrawling;
import com.tjy.newsapp.components.news.News;
import com.tjy.newsapp.components.news.NewsCrawler;
import com.tjy.newsapp.components.newsview.NewsProviderHandler.*;

import java.util.ArrayList;
import java.util.Date;

// 获取新闻，实现部分
public class NewsProviderWeb implements NewsProviderImpl {
    private OnNewsUpdateListener listener;
    private ContinuingCrawling crawling;

    public NewsProviderWeb() {
        listener = null;
        crawling = null;
    }

    @Override
    public void setOnNewsUpdateListener(OnNewsUpdateListener listener) {
        this.listener = listener;
    }

    // 刷新最新一周的新闻
    @Override
    public void refreshNews() {
        Log.i("NewsProvider", "refresh news");
        final long WEEK = 7 * 24 * 60 * 60 * 1000;
        Date endDate = new Date();
        Date startDate = new Date(endDate.getTime() - WEEK);
        crawling = NewsCrawler.crawl(startDate, endDate);
        ArrayList<News> list = crawling.next();
        Log.i("NewsProvider", "crawling ok, get " + list.size() + " news.");
        if(listener != null) {
            new Handler(Looper.getMainLooper()).post(() -> listener.onNewsRefresh(list));
        }
    }

    // 加载更多新闻
    @Override
    public void loadMoreNews() {
        Log.i("NewsProvider", "load more news");
        ArrayList<News> list;
        if(crawling == null || !crawling.hasNext()) {
            list = new ArrayList<>();
        } else {
            list = crawling.next();
        }
        Log.i("NewsProvider", "crawling ok, get " + list.size() + " news.");
        if(listener != null) {
            new Handler(Looper.getMainLooper()).post(() -> listener.onNewsLoadMore(list));
        }
    }
}
