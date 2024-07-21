package com.java.tanjingyu.components;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.tanjingyu.R;
import com.java.tanjingyu.components.record.News;
import com.java.tanjingyu.components.record.Star;

import java.util.List;

// 新闻详情页面
public class NewsDetailActivity extends AppCompatActivity{
    private News news;
    private AppCompatImageView imageStar;
    private boolean star;
    private GLMHelper glmHelper;

    // 根据 newsId 从 SQLite 中获取数据
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_news_detail);
        Intent intent = getIntent();
        String newsId = intent.getStringExtra("newsId");
        List<News> list = News.find(News.class, "news_id = ?", newsId);
        assert list.size() == 1;
        news = list.get(0);
        setAttributes();
        setStarButton();
        setGLMAbstract();
    }

    private void setColor() {
        int color = getColor(star ? R.color.yellow : R.color.white);
        imageStar.setImageTintList(ColorStateList.valueOf(color));
    }

    // 设置相关属性
    @SuppressLint("SetTextI18n")
    private void setAttributes() {
        TextView detailTitle = findViewById(R.id.detail_title);
        detailTitle.setText(news.getTitle());
        ImageVideoView detailViewSwitcher = findViewById(R.id.detail_view_switcher);
        detailViewSwitcher.put(news.getImage(), news.getVideo());
        TextView detailPublisher = findViewById(R.id.detail_publisher);
        detailPublisher.setText(news.getPublisher());
        TextView detailPublishTime = findViewById(R.id.detail_publish_time);
        detailPublishTime.setText(news.getFormatPublishTime());
        TextView detailContent = findViewById(R.id.detail_content);
        detailContent.setText(news.getContent());
        TextView detailOrganization = findViewById(R.id.detail_organization);
        String organization = news.getOrganization();
        if (organization.isEmpty()) organization = getString(R.string.string_none);
        detailOrganization.setText(getString(R.string.string_source) + organization);
        TextView detailCategory = findViewById(R.id.detail_category);
        detailCategory.setText(getString(R.string.string_category) + news.getCategory());
    }

    // 收藏按钮
    private void setStarButton() {
        FloatingActionButton button = findViewById(R.id.button_star);
        imageStar = findViewById(R.id.image_star);
        star = news.isStar();
        setColor();
        String newsId = news.getNewsId();
        button.setOnClickListener(view -> {
            star = !star;
            if(star) {
                new Star(newsId).save();
                Toast.makeText(this, getString(R.string.info_star), Toast.LENGTH_SHORT).show();
            } else {
                Star.deleteAll(Star.class, "news_id = ?", newsId);
                Toast.makeText(this, getString(R.string.info_unstar), Toast.LENGTH_SHORT).show();
            }
            setColor();
        });
    }

    // 大模型摘要
    @SuppressLint("SetTextI18n")
    private void setGLMAbstract() {
        TextView textAbstract = findViewById(R.id.detail_abstract);
        String prefix = getString(R.string.abstract_prefix);
        if(!news.getGLMAbstract().isEmpty()) {
            textAbstract.setText(prefix + news.getGLMAbstract());
            glmHelper = null;
            return;
        }
        glmHelper = new GLMHelper(news);
        glmHelper.setOnGLMOutputListener(output -> {
            if(output == null) {
                String abstractFailed = getString(R.string.abstract_failed);
                Toast.makeText(this, abstractFailed, Toast.LENGTH_LONG).show();
                textAbstract.setText(abstractFailed);
            } else {

                // 存储大模型生成的摘要
                textAbstract.setText(prefix + output);
                News.executeQuery("UPDATE NEWS SET glm_abstract = ? WHERE news_id = ?",
                        output,
                        news.getNewsId());
            }
        });
        glmHelper.start();
    }

    @Override
    public void onDestroy() {
        if(glmHelper != null) glmHelper.stop();
        super.onDestroy();
    }
}
