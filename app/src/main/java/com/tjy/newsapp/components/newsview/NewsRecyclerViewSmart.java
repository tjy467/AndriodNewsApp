package com.tjy.newsapp.components.newsview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tjy.newsapp.components.news.News;

import java.util.ArrayList;

// 添加上拉下拉功能
public class NewsRecyclerViewSmart extends SmartRefreshLayout implements NewsProviderHandler.OnNewsUpdateListener {
    private final NewsRecyclerView newsRecyclerView;

    public NewsRecyclerViewSmart(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        newsRecyclerView = new NewsRecyclerView(context);
        addView(newsRecyclerView);
        setRefreshHeader(new ClassicsHeader(context));
        setRefreshFooter(new ClassicsFooter(context));
    }

    // 绑定新闻来源，设置回调函数
    public void bindNewsProviderHandler(NewsProviderHandler handler) {
        handler.setOnNewsUpdateListener(this);
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                handler.refreshNews();
            }
        });
        setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                handler.loadMoreNews();
            }
        });
        handler.refreshNews();
    }

    // 下拉刷新
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onNewsRefresh(ArrayList<News> newsList) {
        Log.i("NewsView", "news refresh callback called with size: " + newsList.size());
        newsRecyclerView.newsList = newsList;
        newsRecyclerView.adapter.notifyDataSetChanged();
        finishRefresh();
    }

    // 上拉加载
    @Override
    public void onNewsLoadMore(ArrayList<News> newsList) {
        Log.i("NewsView", "news load more callback called with size: " + newsList.size());
        for(News news: newsList) {
            newsRecyclerView.newsList.add(news);
            newsRecyclerView.adapter.notifyItemInserted(newsRecyclerView.adapter.getItemCount());
        }
        finishLoadMore();
    }
}
