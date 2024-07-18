package com.tjy.newsapp.components.newsview;

public interface NewsProvider {

    // 设置新闻更新监听器
    void setOnNewsUpdateListener(NewsProviderHandler.OnNewsUpdateListener listener);

    // 下拉刷新
    void refreshNews();

    // 上拉加载
    void loadMoreNews();
}
