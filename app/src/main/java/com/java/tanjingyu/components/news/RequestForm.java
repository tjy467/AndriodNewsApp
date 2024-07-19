package com.java.tanjingyu.components.news;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestForm {
    public static final int PAGE_SIZE = 15;
    private static final String QUERY_URL = "https://api2.newsminer.net/svc/news/queryNewsList";

    private final String keyWord;
    private final String category;
    private final String startDate;
    private final String endDate;

    public RequestForm(String category, String keyWord, String startDate, String endDate) {
        this.category = category;
        this.keyWord = keyWord;
        this.startDate = startDate;
        if(endDate.isEmpty()) {
            Date now = new Date();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            this.endDate = format.format(now);
        } else {
            this.endDate = endDate;
        }
    }

    public RequestForm(String category) {
        this(category, "", "", "");
    }

    public RequestForm() {
        this("", "", "", "");
    }

    public String getRequestUrl(int page) {
        return QUERY_URL
                + "?size=" + PAGE_SIZE
                + "&startDate=" + startDate
                + "&endDate=" + endDate
                + "&words=" + keyWord
                + "&categories=" + category
                + "&page=" + page;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String getCategory() {
        return category;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
