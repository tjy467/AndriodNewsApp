package com.tjy.newsapp.components.newsview;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import com.tjy.newsapp.components.news.News;

import java.util.ArrayList;

// 获取新闻，线程调度部分
public class NewsProviderHandler {

    static private HandlerThread thread = null;
    private final NewsProviderImpl newsProvider;

    public NewsProviderHandler(NewsProviderImpl newsProvider) {
        this.newsProvider = newsProvider;
    }

    private static final int ADD_ON_NEWS_UPDATE_LISTENER = 1;
    private static final int REFRESH_NEWS = 2;
    private static final int LOAD_MORE_NEWS = 3;

    private Handler getHandler() {
        if(thread == null) {
            thread = new HandlerThread("NewsProvider");
            thread.start();
        }
        return new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message message) {
                switch(message.what) {
                    case ADD_ON_NEWS_UPDATE_LISTENER:
                        newsProvider.setOnNewsUpdateListener((OnNewsUpdateListener) message.obj);
                        break;
                    case REFRESH_NEWS:
                        newsProvider.refreshNews();
                        break;
                    case LOAD_MORE_NEWS:
                        newsProvider.loadMoreNews();
                        break;
                }
            }
        };
    }

    public interface OnNewsUpdateListener {
        void onNewsRefresh(ArrayList<News> newsList);
        void onNewsLoadMore(ArrayList<News> newsList);
    }

    public void setOnNewsUpdateListener(OnNewsUpdateListener listener) {
        Message message = getHandler().obtainMessage(ADD_ON_NEWS_UPDATE_LISTENER, listener);
        getHandler().sendMessage(message);
    }

    public void refreshNews() {
        getHandler().sendEmptyMessage(REFRESH_NEWS);
    }

    public void loadMoreNews() {
        getHandler().sendEmptyMessage(LOAD_MORE_NEWS);
    }
}
