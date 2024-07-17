package com.tjy.newsapp.components.newsview;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.tjy.newsapp.components.news.News;

import java.util.List;

// 获取新闻，线程调度部分
public class NewsProviderHandler extends Handler {

    static private HandlerThread thread;

    private NewsProviderHandler(Looper looper) {
        super(looper);
    }

    public static NewsProviderHandler getInstance() {
        if(thread == null) {
            thread = new HandlerThread("NewsProvider");
            thread.start();
        }
        return new NewsProviderHandler(thread.getLooper());
    }

    private final int ADD_ON_NEWS_UPDATE_LISTENER = 1;

    @Override
    public void handleMessage(Message message) {
        switch(message.what) {
            case ADD_ON_NEWS_UPDATE_LISTENER:
                NewsProviderImpl.addOnNewsUpdateListener((OnNewsUpdateListener) message.obj);
                break;
        }
    }

    public interface OnNewsUpdateListener {
        void onNewsUpdate(List<News> news);
    }

    public void addOnNewsUpdateListener(OnNewsUpdateListener listener) {
        sendMessage(obtainMessage(ADD_ON_NEWS_UPDATE_LISTENER, listener));
    }
}
