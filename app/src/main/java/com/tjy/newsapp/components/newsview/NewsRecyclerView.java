package com.tjy.newsapp.components.newsview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tjy.newsapp.R;
import com.tjy.newsapp.components.news.News;

import java.util.ArrayList;

// 滚动展示新闻
public class NewsRecyclerView extends RecyclerView {
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
        public ImageView imageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textTitle = itemView.findViewById(R.id.text_title);
            textPublisher = itemView.findViewById(R.id.text_publisher);
            textPublishTime = itemView.findViewById(R.id.text_publish_time);
            textOrganization = itemView.findViewById(R.id.text_organization);
            imageView = itemView.findViewById(R.id.imageView);
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
            Glide.with(holder.itemView).load(news.getImage()).into(holder.imageView);
            holder.itemView.setOnClickListener(view -> {
                TextView textView = view.findViewById(R.id.text_title);
                android.util.Log.i("DEBUG!!", news.getNewsID());
            });
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }
    }
}
