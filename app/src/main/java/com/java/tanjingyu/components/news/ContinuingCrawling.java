package com.java.tanjingyu.components.news;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.java.tanjingyu.NewsApplication;
import com.java.tanjingyu.R;
import com.java.tanjingyu.components.record.News;

import java.io.IOException;
import java.util.ArrayList;

// 暂存上次访问的参数
public class ContinuingCrawling {
    private final RequestForm requestForm;
    private int page;
    private boolean hasNext;

    ContinuingCrawling(RequestForm requestForm) {
        this.requestForm = requestForm;
        page = 1;
        hasNext = true;
    }

    public ArrayList<News> next() {
        ArrayList<News> result;
        try {
            result = NewsCrawler.crawl(requestForm, page);
        } catch(IOException e) {
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(NewsApplication.getContext(), R.string.error_web, Toast.LENGTH_LONG).show());
            return new ArrayList<>();
        }
        page++;
        if(result.size() < RequestForm.PAGE_SIZE) hasNext = false;
        return result;
    }

    public boolean hasNext() {
        return hasNext;
    }
}
