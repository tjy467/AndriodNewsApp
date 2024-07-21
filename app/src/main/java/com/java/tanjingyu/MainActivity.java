package com.java.tanjingyu;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.java.tanjingyu.components.categoriesfragment.CategoriesFragment;
import com.java.tanjingyu.components.minefragment.MineFragment;
import com.java.tanjingyu.components.newsfragment.NewsFragment;

public class MainActivity extends FragmentActivity implements BottomNavigationView.OnItemSelectedListener {
    private Fragment newsFragment, categoriesFragment, mineFragment, currentFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        manager = this.getSupportFragmentManager();
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(this);

        // 恢复 fragment
        if(savedInstance != null) {
            newsFragment = manager.findFragmentByTag(getString(R.string.menu_news));
            categoriesFragment = manager.findFragmentByTag(getString(R.string.menu_categories));
            mineFragment = manager.findFragmentByTag(getString(R.string.menu_mine));
            currentFragment = manager.findFragmentByTag(savedInstance.getString("currentFragment"));
            
            if(currentFragment == newsFragment) navigationView.setSelectedItemId(R.id.menu_news);
            else if(currentFragment == categoriesFragment) navigationView.setSelectedItemId(R.id.menu_categories);
            else navigationView.setSelectedItemId(R.id.menu_mine);
        } else {
            initFragments();
        }
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
        manager.beginTransaction()
                .add(R.id.main_view, newsFragment, getString(R.string.menu_news))
                .add(R.id.main_view, categoriesFragment, this.getString(R.string.menu_categories))
                .add(R.id.main_view, mineFragment, this.getString(R.string.menu_mine))
                .show(newsFragment)
                .hide(categoriesFragment)
                .hide(mineFragment)
                .commit();
        currentFragment = newsFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("currentFragment", currentFragment.getTag());
        super.onSaveInstanceState(outState);
    }
}
