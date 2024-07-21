package com.java.tanjingyu.components.newsprovider;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import com.java.tanjingyu.components.record.News;
import com.java.tanjingyu.components.news.RequestForm;

import java.util.ArrayList;

// 获取新闻，将主线程请求转发到子线程
public class NewsProviderHandler {
    static private HandlerThread thread = null;
    private final Handler handler;
    private RequestForm requestForm;

    public NewsProviderHandler(NewsProvider newsProvider) {
        thread = new HandlerThread("NewsProvider");
        thread.start();
        handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message message) {
                switch(message.what) {
                    case ADD_ON_NEWS_UPDATE_LISTENER:
                        newsProvider.setOnNewsUpdateListener((OnNewsUpdateListener) message.obj);
                        break;
                    case REFRESH_NEWS:
                        newsProvider.refreshNews((RequestForm) message.obj);
                        break;
                    case LOAD_MORE_NEWS:
                        newsProvider.loadMoreNews();
                        break;
                }
            }
        };
    }

    public void setRequestForm(RequestForm requestForm) {
        this.requestForm = requestForm;
    }

    private static final int ADD_ON_NEWS_UPDATE_LISTENER = 1;
    private static final int REFRESH_NEWS = 2;
    private static final int LOAD_MORE_NEWS = 3;

    public interface OnNewsUpdateListener {
        void onNewsRefresh(ArrayList<News> newsList);
        void onNewsLoadMore(ArrayList<News> newsList);
    }

    public void setOnNewsUpdateListener(OnNewsUpdateListener listener) {
        Message message = handler.obtainMessage(ADD_ON_NEWS_UPDATE_LISTENER, listener);
        handler.sendMessage(message);
    }

    public void refreshNews() {
        Message message = handler.obtainMessage(REFRESH_NEWS, requestForm);
        handler.sendMessage(message);
    }

    public void loadMoreNews() {
        handler.sendEmptyMessage(LOAD_MORE_NEWS);
    }
}
