package com.tjy.newsapp.components.newsview;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.tjy.newsapp.NewsApplication;
import com.tjy.newsapp.R;
import com.tjy.newsapp.components.news.ContinuingCrawling;
import com.tjy.newsapp.components.news.News;
import com.tjy.newsapp.components.news.NewsCrawler;
import com.tjy.newsapp.components.newsview.NewsProviderHandler.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

// 获取新闻，实现部分
class NewsProviderImpl {
    static final ArrayList<OnNewsUpdateListener> listeners = new ArrayList<>();
    static final HashSet<News> newsList = new HashSet<>();
    static ContinuingCrawling crawling = null;

    public static void addOnNewsUpdateListener(OnNewsUpdateListener listener) {
        listeners.add(listener);
    }

    private static void updateNews(List<News> news) {
        for(final OnNewsUpdateListener listener: listeners) {
            new Handler(Looper.getMainLooper()).post(() -> listener.onNewsUpdate(news));
        }
    }

    // 获取最新一天的新闻并更新时间区间
    public static void getLatestNews() {
        Log.i("NewsProvider", "get latest news");
        if(crawling == null || !crawling.hasNext()) {
            final long DAY = 24 * 60 * 60 * 1000;
            Date endDate = new Date();
            Date startDate = new Date(endDate.getTime() - 2 * DAY);
            crawling = NewsCrawler.crawl(startDate, endDate);
        }
        ArrayList<News> list;
        try {
            list = crawling.next();
        } catch(IOException e) {
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(NewsApplication.getContext(), R.string.error_web, Toast.LENGTH_LONG).show());
            return;
        }
        ArrayList<News> result = new ArrayList<>();
        for(News news: list) {
            if(!newsList.contains(news)) {
                newsList.add(news);
                result.add(news);
            }
        }
        Log.i("NewsProvider", "crawling ok, get " + result.size() + " news.");
        if(!result.isEmpty()) updateNews(result);
    }
}
