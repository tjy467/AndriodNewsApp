package com.tjy.newsapp.components.newsview;

import androidx.viewpager2.widget.ViewPager2;

import com.tjy.newsapp.NewsApplication;
import com.tjy.newsapp.components.ViewPager2Manager;

// 管理新闻列表与新闻详情页之间的切换
public class NewsViewManager {
    private ViewPager2Manager manager;
    private final NewsRecyclerViewSmart recyclerView;
    private final NewsDetailView detailView;

    public NewsViewManager() {
        recyclerView = new NewsRecyclerViewSmart(NewsApplication.getContext());
        recyclerView.bindNewsProviderHandler(new NewsProviderHandler(new NewsProviderWeb()));
        detailView = new NewsDetailView(NewsApplication.getContext());
    }

    public void attach(ViewPager2Manager manager) {
        this.manager = manager;
        manager.push(recyclerView);
        manager.push(detailView);
    }

}
