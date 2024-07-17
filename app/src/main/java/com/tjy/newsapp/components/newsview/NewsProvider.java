package com.tjy.newsapp.components.newsview;

import com.tjy.newsapp.components.news.News;

public interface NewsProvider {
    public interface OnNewsInsertListener {
        public void onNewsInsert(News news);
    }

    public void setOnNewsInsertListener(OnNewsInsertListener listener);
}
