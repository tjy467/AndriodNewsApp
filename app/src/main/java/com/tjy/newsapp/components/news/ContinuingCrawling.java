package com.tjy.newsapp.components.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

// 处理返回的新闻数量过多的情况，进行缓存
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

    public ArrayList<News> next() throws IOException {
        ArrayList<News> result = NewsCrawler.crawl(startDate, endDate, page);
        page++;
        if(result.size() < NewsCrawler.PAGE_SIZE) hasNext = false;
        return result;
    }

    public boolean hasNext() {
        return hasNext;
    }
}
