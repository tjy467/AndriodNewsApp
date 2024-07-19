package com.java.tanjingyu.components.newsview;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.java.tanjingyu.R;
import com.java.tanjingyu.components.news.ContinuingCrawling;
import com.java.tanjingyu.components.News;
import com.java.tanjingyu.components.news.NewsCrawler;
import com.java.tanjingyu.components.news.RequestForm;
import com.java.tanjingyu.components.newsview.NewsProviderHandler.*;

import java.util.ArrayList;
import java.util.Date;

// 获取新闻，实现部分
public class NewsProviderWeb implements NewsProvider {
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
    public void refreshNews(RequestForm requestForm) {
        Log.i("NewsProvider", "refresh news");
        crawling = NewsCrawler.crawl(requestForm);
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
