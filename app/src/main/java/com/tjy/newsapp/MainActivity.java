package com.tjy.newsapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tjy.newsapp.components.news.News;
import com.tjy.newsapp.components.newsview.NewsProviderHandler;

import java.util.List;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnItemSelectedListener {
    private Fragment newsFragment, categoriesFragment, mineFragment, currentFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        initFragments();
    }

    // 切换菜单
    private void navigatesTo(Fragment fragment) {
        if(currentFragment == fragment) return;
        manager.beginTransaction()
                .hide(currentFragment)
                .show(fragment)
                .commit();
        currentFragment = fragment;
    }

    // 切换菜单的回调函数
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();
        assert title != null;
        Fragment fragment = manager.findFragmentByTag(title.toString());
        navigatesTo(fragment);
        return true;
    }

    // 初始化菜单栏
    private void initFragments() {
        newsFragment = new NewsFragment();
        categoriesFragment = new CategoriesFragment();
        mineFragment = new MineFragment();
        manager = this.getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.main_view, newsFragment, this.getString(R.string.menu_news))
                .add(R.id.main_view, categoriesFragment, this.getString(R.string.menu_categories))
                .add(R.id.main_view, mineFragment, this.getString(R.string.menu_mine))
                .show(newsFragment)
                .hide(categoriesFragment)
                .hide(mineFragment)
                .commit();
        currentFragment = newsFragment;

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(this);
    }
}
