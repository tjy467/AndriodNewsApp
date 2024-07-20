package com.java.tanjingyu.components.categoriesfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.java.tanjingyu.R;
import com.java.tanjingyu.components.news.RequestForm;
import com.java.tanjingyu.components.newsview.NewsProviderHandler;
import com.java.tanjingyu.components.newsview.NewsProviderWeb;
import com.java.tanjingyu.components.newsview.NewsRecyclerViewSmart;

// "分类"菜单
public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        ViewPagerManager manager = new ViewPagerManager();
        manager.setTabLayout(view.findViewById(R.id.tab_categories));
        manager.setViewPager(view.findViewById(R.id.view_pager_categories));
        manager.bindAll();

        String[] categories = getResources().getStringArray(R.array.categories);
        for(String category: categories) {
            if(category.equals("无")) continue;
            NewsProviderHandler handler = new NewsProviderHandler(new NewsProviderWeb());
            handler.setRequestForm(new RequestForm(category));
            NewsRecyclerViewSmart recyclerView = new NewsRecyclerViewSmart(getContext());
            recyclerView.bindNewsProviderHandler(handler);
            manager.add(category, recyclerView);
        }
        return view;
    }
}
