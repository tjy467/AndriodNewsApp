package com.java.tanjingyu.components.newsfragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;

import com.java.tanjingyu.R;
import com.java.tanjingyu.components.newsprovider.RequestForm;
import com.java.tanjingyu.components.newsprovider.NewsProviderHandler;
import com.java.tanjingyu.components.newsprovider.NewsProviderWeb;
import com.java.tanjingyu.components.newsview.NewsRecyclerViewSmart;

// "新闻"菜单
public class NewsFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        NewsRecyclerViewSmart recyclerView = view.findViewById(R.id.news_recycler);
        NewsProviderHandler providerHandler = new NewsProviderHandler(new NewsProviderWeb());
        providerHandler.setRequestForm(new RequestForm());
        recyclerView.bindNewsProviderHandler(providerHandler);
        recyclerView.autoRefresh();

        // 设置搜索栏
        SearchBar searchBar = new SearchBar();
        PopupWindow window = searchBar.create(getContext(), (ViewGroup) view);
        View searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(_view -> window.showAtLocation(view, Gravity.TOP, 0, 0));
        searchBar.setOnSearchClickListener(new SearchBar.OnSearchClickListener() {
            @Override
            public void onSearchClick(RequestForm requestForm) {
                providerHandler.setRequestForm(requestForm);
                recyclerView.autoRefresh();
            }
        });
        return view;
    }
}
