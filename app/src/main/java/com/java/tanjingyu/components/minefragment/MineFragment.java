package com.java.tanjingyu.components.minefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.java.tanjingyu.R;

// "我的"菜单
public class MineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }
}
