package com.tjy.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tjy.newsapp.components.newsview.NewsProviderHandler;
import com.tjy.newsapp.components.newsview.NewsProviderWeb;
import com.tjy.newsapp.components.newsview.NewsRecyclerViewSmart;

// "新闻"菜单
public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        NewsRecyclerViewSmart recyclerView = view.findViewById(R.id.news_recycler);
        recyclerView.bindNewsProviderHandler(new NewsProviderHandler(new NewsProviderWeb()));
        return view;
    }
}
