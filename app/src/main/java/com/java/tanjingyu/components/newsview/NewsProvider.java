package com.java.tanjingyu.components.newsview;

import com.java.tanjingyu.components.news.RequestForm;

// 公用接口，有从网络加载和从存储加载两种类型
public interface NewsProvider {

    // 设置新闻更新监听器
    void setOnNewsUpdateListener(NewsProviderHandler.OnNewsUpdateListener listener);

    // 下拉刷新
    void refreshNews(RequestForm requestForm);

    // 上拉加载
    void loadMoreNews();
}
