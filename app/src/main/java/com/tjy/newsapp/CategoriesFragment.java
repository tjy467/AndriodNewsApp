package com.tjy.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

// "分类"菜单
public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }
}
