package com.tjy.newsapp.components;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Stack;

// 多页切换管理器，保存一个视图的栈，允许滑动和返回
public class ViewPager2Manager {
    private final Stack<View> viewStack;
    private Adapter adapter;

    public ViewPager2Manager() {
        viewStack = new Stack<>();
    }

    public ViewPager2 create(Context context) {
        ViewPager2 viewPager2 = new ViewPager2(context);
        adapter = new Adapter();
        viewPager2.setAdapter(adapter);
        return viewPager2;
    }

    public void push(View view) {
        viewStack.push(view);
        adapter.notifyItemInserted(adapter.getItemCount());
    }

    public void pop() {
        viewStack.pop();
        adapter.notifyItemRemoved(adapter.getItemCount() - 1);
    }

    @SuppressWarnings("all")
    private class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView;
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            return new ViewHolder(layout);
        }

        // 设置新闻简略
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.layout.addView(viewStack.get(position));
        }

        @Override
        public int getItemCount() {
            return viewStack.size();
        }
    }
}
