package com.java.tanjingyu.components.newsprovider;

import android.os.Handler;
import android.os.Looper;

import com.java.tanjingyu.components.record.News;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// 获取新闻，数据库来源
public class NewsProviderDb extends NewsProvider {
    public interface HasNewsId {
        String getNewsId();
    }

    private final Class<? extends SugarRecord<?>> type;
    private Iterator<? extends HasNewsId> iterator;

    // 通过反射获取类型
    @SuppressWarnings("all")
    public NewsProviderDb(String className) {
        try {
            type = (Class<? extends SugarRecord<?>>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<News> query() {
        ArrayList<News> newsList = new ArrayList<>();
        for(int i = 0; i < RequestForm.PAGE_SIZE; i++) {
            if(!iterator.hasNext()) break;
            String newsId = iterator.next().getNewsId();
            List<News> list = News.find(News.class, "news_id = ?", newsId);
            assert list.size() == 1;
            newsList.add(list.get(0));
        }
        return newsList;
    }

    @Override
    @SuppressWarnings("all")
    public void refreshNews(RequestForm requestForm) {
        iterator = (Iterator<? extends HasNewsId>)
                SugarRecord.findAsIterator(type, null, null, null, "ID DESC", null);
        ArrayList<News> list = query();
        if(listener != null) {
            new Handler(Looper.getMainLooper()).post(() -> listener.onNewsRefresh(list));
        }
    }

    @Override
    public void loadMoreNews() {
        ArrayList<News> list = query();
        if(listener != null) {
            new Handler(Looper.getMainLooper()).post(() -> listener.onNewsLoadMore(list));
        }
    }
}
