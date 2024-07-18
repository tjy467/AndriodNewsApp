package com.tjy.newsapp.components.news;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.tjy.newsapp.NewsApplication;
import com.tjy.newsapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// 暂存上次访问的参数
public class ContinuingCrawling {
    public final Date startDate, endDate;
    private int page;
    private boolean hasNext;

    ContinuingCrawling(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        page = 1;
        hasNext = true;
    }

    public ArrayList<News> next() {
        ArrayList<News> result;
        try {
            result = NewsCrawler.crawl(startDate, endDate, page);
        } catch(IOException e) {
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(NewsApplication.getContext(), R.string.error_web, Toast.LENGTH_LONG).show());
            return new ArrayList<>();
        }
        page++;
        if(result.size() < NewsCrawler.PAGE_SIZE) hasNext = false;
        return result;
    }

    public boolean hasNext() {
        return hasNext;
    }
}
