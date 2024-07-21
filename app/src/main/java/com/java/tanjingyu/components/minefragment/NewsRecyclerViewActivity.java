package com.java.tanjingyu.components.minefragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.java.tanjingyu.R;
import com.java.tanjingyu.components.newsprovider.NewsProviderDb;
import com.java.tanjingyu.components.newsprovider.NewsProviderHandler;
import com.java.tanjingyu.components.newsview.NewsRecyclerViewSmart;

// 我的收藏 / 历史记录界面
public class NewsRecyclerViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_news_recycler_view);
        TextView textTitle = findViewById(R.id.activity_title);
        NewsRecyclerViewSmart recyclerView = findViewById(R.id.recycler_view);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String className = intent.getStringExtra("className");
        textTitle.setText(title);
        recyclerView.bindNewsProviderHandler(new NewsProviderHandler(new NewsProviderDb(className)));
        recyclerView.autoRefresh();
    }
}
