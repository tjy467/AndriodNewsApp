package com.java.tanjingyu.components.newsview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.tanjingyu.NewsDetailActivity;
import com.java.tanjingyu.R;
import com.java.tanjingyu.components.ImageVideoView;
import com.java.tanjingyu.components.News;

import java.util.ArrayList;
// 滚动展示新闻
class NewsRecyclerView extends RecyclerView {
    public ArrayList<News> newsList;
    public NewsItemAdapter adapter;

    public NewsRecyclerView(@NonNull Context context) {
        super(context);
        newsList = new ArrayList<>();
        adapter = new NewsItemAdapter();
        setAdapter(adapter);
        setLayoutManager(new LinearLayoutManager(context));
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @SuppressWarnings("all")
    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView textTitle;
        public TextView textPublisher;
        public TextView textPublishTime;
        public TextView textOrganization;
        public ImageVideoView viewSwitcher;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textTitle = itemView.findViewById(R.id.text_title);
            textPublisher = itemView.findViewById(R.id.text_publisher);
            textPublishTime = itemView.findViewById(R.id.text_publish_time);
            textOrganization = itemView.findViewById(R.id.text_organization);
            viewSwitcher = itemView.findViewById(R.id.view_switcher);
        }
    }

    public class NewsItemAdapter extends RecyclerView.Adapter<NewsViewHolder> {

        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            return new NewsViewHolder(view);
        }

        // 设置新闻简略
        @Override
        public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
            News news = newsList.get(position);
            holder.textTitle.setText(news.getTitle());
            holder.textPublisher.setText(news.getPublisher());
            holder.textPublishTime.setText(news.getFormatPublishTime());
            holder.textOrganization.setText(news.getOrganization());
            holder.viewSwitcher.put(news.getImage(), news.getVideo());

            // 根据是否阅读过设置颜色
            int color = news.hasRead() ? R.color.has_read : R.color.black;
            holder.textTitle.setTextColor(getContext().getColor(color));
            holder.itemView.setOnClickListener(view -> {

                // 存储新闻到本地
                if(!news.hasRead()) news.save();
                holder.textTitle.setTextColor(getContext().getColor(R.color.has_read));
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsId", news.getNewsId());
                getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }
    }
}
