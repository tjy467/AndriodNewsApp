package com.tjy.newsapp.components.newsview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tjy.newsapp.components.news.News;

import java.util.List;

public class NewsView extends SmartRefreshLayout implements NewsProviderHandler.OnNewsUpdateListener {
    NewsRecyclerView newsRecyclerView;

    public NewsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        newsRecyclerView = new NewsRecyclerView(context);
        addView(newsRecyclerView);
        setRefreshHeader(new ClassicsHeader(context));
        setRefreshFooter(new ClassicsFooter(context));
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                NewsProviderHandler.getInstance().getLatestNews();
            }
        });
        setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });

        NewsProviderHandler.getInstance().addOnNewsUpdateListener(this);
    }

    // 插入抓取的新闻
    @Override
    public void onNewsUpdate(List<News> newsList) {
        Log.i("NewsView", "news update callback called with size: " + newsList.size());
        for(News news: newsList) {
            newsRecyclerView.newsList.add(news);
            newsRecyclerView.adapter.notifyItemInserted(newsRecyclerView.adapter.getItemCount());
        }
        finishRefresh();
    }
}
