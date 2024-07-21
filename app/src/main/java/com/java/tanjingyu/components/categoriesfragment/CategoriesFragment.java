package com.java.tanjingyu.components.categoriesfragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.tanjingyu.R;
import com.java.tanjingyu.components.news.RequestForm;
import com.java.tanjingyu.components.newsview.NewsProviderHandler;
import com.java.tanjingyu.components.newsview.NewsProviderWeb;
import com.java.tanjingyu.components.newsview.NewsRecyclerViewSmart;

// "分类"菜单
public class CategoriesFragment extends Fragment {
    private ViewPagerManager manager;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        manager = new ViewPagerManager();
        manager.setTabLayout(view.findViewById(R.id.tab_categories));
        manager.setViewPager(view.findViewById(R.id.view_pager_categories));
        manager.bindAll();

        String[] categories = getResources().getStringArray(R.array.categories);
        for(String category: categories) {
            NewsProviderHandler handler = new NewsProviderHandler(new NewsProviderWeb());
            handler.setRequestForm(new RequestForm(category));
            NewsRecyclerViewSmart recyclerView = new NewsRecyclerViewSmart(getContext());
            recyclerView.bindNewsProviderHandler(handler);
            manager.add(category, recyclerView);
        }

        initDialog();
        FloatingActionButton button = view.findViewById(R.id.button_categories);
        button.setOnClickListener(_view -> dialog.show());
        return view;
    }

    // dp 转 px
    @SuppressWarnings("all")
    private int dp2px(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    // 动态创建 ChipGroup
    private ChipGroup getChipGroup() {
        ChipGroup chipGroup = new ChipGroup(requireContext());
        chipGroup.setPadding(dp2px(20), dp2px(10), dp2px(20), 0);
        String[] categories = getResources().getStringArray(R.array.categories);
        for(String category: categories) {
            Chip chip = new Chip(getContext());
            chip.setText(category);
            chip.setCheckable(true);
            chipGroup.addView(chip);
            if(manager.isSelected(category)) {
                chipGroup.check(chip.getId());
            }
            chip.setOnCheckedChangeListener((compoundButton, b) -> {
                String text = compoundButton.getText().toString();
                if(b) manager.enable(text);
                else manager.disable(text);
            });
        }
        return chipGroup;
    }

    // 初始化选择分类的对话框
    private void initDialog() {
        ChipGroup chipGroup = getChipGroup();
        dialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.string_edit_categories))
                .setView(chipGroup)
                .create();
    }
}
