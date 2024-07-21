package com.java.tanjingyu.components.newsprovider;

import com.java.tanjingyu.components.news.RequestForm;

// 公用接口
public interface NewsProvider {

    // 设置新闻更新监听器
    void setOnNewsUpdateListener(NewsProviderHandler.OnNewsUpdateListener listener);

    // 下拉刷新
    void refreshNews(RequestForm requestForm);

    // 上拉加载
    void loadMoreNews();
}
