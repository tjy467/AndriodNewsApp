package com.java.tanjingyu.components.newsprovider;

// 公用接口
public abstract class NewsProvider {
    protected NewsProviderHandler.OnNewsUpdateListener listener;

    // 设置新闻更新监听器
    public void setOnNewsUpdateListener(NewsProviderHandler.OnNewsUpdateListener listener) {
        this.listener = listener;
    }

    // 下拉刷新
    abstract void refreshNews(RequestForm requestForm);

    // 上拉加载
    abstract void loadMoreNews();
}
