package com.java.tanjingyu.components.newsprovider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.java.tanjingyu.components.record.News;

import java.util.ArrayList;

// 获取新闻，网络来源
public class NewsProviderWeb extends NewsProvider {
    private ContinuingCrawling crawling;

    public NewsProviderWeb() {
        listener = null;
        crawling = null;
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
