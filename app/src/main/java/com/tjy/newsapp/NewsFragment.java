package com.tjy.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.tjy.newsapp.components.ViewPager2Manager;
import com.tjy.newsapp.components.newsview.NewsViewManager;

// "新闻"菜单
public class NewsFragment extends Fragment {
    ViewPager2Manager pagerManager;
    NewsViewManager viewManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ConstraintLayout layout = view.findViewById(R.id.news_pager);
        pagerManager = new ViewPager2Manager();
        ViewPager2 viewPager = pagerManager.create(getContext());
        layout.addView(viewPager);
        viewManager = new NewsViewManager();
        viewManager.attach(pagerManager);
        return view;
    }
}
