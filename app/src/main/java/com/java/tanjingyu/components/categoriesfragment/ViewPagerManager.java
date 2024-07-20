package com.java.tanjingyu.components.categoriesfragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.java.tanjingyu.components.newsview.NewsRecyclerViewSmart;

import java.util.ArrayList;
import java.util.HashMap;

// 管理多个上方菜单
public class ViewPagerManager {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private final ArrayList<String> tabs;
    private final HashMap<String, NewsRecyclerViewSmart> viewMap;
    private final HashMap<String, TabLayout.Tab> tabMap;
    private Adapter adapter;

    public ViewPagerManager() {
        tabs = new ArrayList<>();
        viewMap = new HashMap<>();
        tabMap = new HashMap<>();
    }

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.viewPager = viewPager;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void bindAll() {
        adapter = new Adapter();
        viewPager.setAdapter(adapter);

        // 同步 TabLayout 与 Viewpager2
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                assert tab.getText() != null;
                String text = tab.getText().toString();
                int position = tabs.indexOf(text);
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String tab = tabs.get(position);
                tabLayout.selectTab(tabMap.get(tab));
                NewsRecyclerViewSmart view = viewMap.get(tab);
                assert view != null;
                view.refreshIfEmpty();
            }
        });
    }

    public void add(String tabText, NewsRecyclerViewSmart view) {
        TabLayout.Tab tab = tabLayout.newTab().setText(tabText);
        tabLayout.addTab(tab);
        tabs.add(tabText);
        viewMap.put(tabText, view);
        tabMap.put(tabText, tab);
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (ConstraintLayout) itemView;
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ConstraintLayout layout = new ConstraintLayout(parent.getContext());
            layout.setLayoutParams(new Constraints.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            return new ViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String tab = tabs.get(position);
            View view = viewMap.get(tab);
            holder.layout.addView(view);
        }

        @Override
        public void onViewRecycled(@NonNull ViewHolder holder) {
            holder.layout.removeAllViewsInLayout();
        }

        @Override
        public int getItemCount() {
            return tabs.size();
        }
    }
}
