package com.java.tanjingyu.components.minefragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.java.tanjingyu.R;
import com.java.tanjingyu.components.record.History;
import com.java.tanjingyu.components.record.News;
import com.java.tanjingyu.components.record.Star;

// "我的"菜单
public class MineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        // 我的收藏
        ConstraintLayout layoutStar = view.findViewById(R.id.layout_star);
        layoutStar.setOnClickListener(_view -> jumpActivity(Star.class.getName(), R.string.string_star));

        // 历史记录
        ConstraintLayout layoutHistory = view.findViewById(R.id.layout_history);
        layoutHistory.setOnClickListener(_view -> jumpActivity(History.class.getName(), R.string.string_history));

        // 清除本地记录
        ConstraintLayout layoutDelete = view.findViewById(R.id.layout_delete);
        layoutDelete.setOnClickListener(_view ->
                new AlertDialog.Builder(getContext())
                .setTitle(R.string.string_delete_record)
                .setNegativeButton(R.string.string_cancel, (dialogInterface, i) -> {})
                .setPositiveButton(R.string.string_ok, (dialogInterface, i) -> {
                    News.deleteAll(News.class);
                    History.deleteAll(History.class);
                    Star.deleteAll(Star.class);
                })
                .create()
                .show());
        return view;
    }

    private void jumpActivity(String className, int titleId) {
        Intent intent = new Intent(getContext(), NewsRecyclerViewActivity.class);
        intent.putExtra("className", className);
        intent.putExtra("title", getString(titleId));
        startActivity(intent);
    }
}
