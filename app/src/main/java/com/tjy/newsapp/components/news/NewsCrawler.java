package com.tjy.newsapp.components.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;

// 抓取新闻
public class NewsCrawler {
    public static final int PAGE_SIZE = 15;
    private static final String QUERY_URL = "https://api2.newsminer.net/svc/news/queryNewsList";

    // 从网页返回的 json 文件中提取新闻内容
    private static ArrayList<News> extractNewsFromJson(JSONObject jsonObject) throws JSONException {
        ArrayList<News> result = new ArrayList<>();
        JSONArray data = jsonObject.getJSONArray("data");
        int length = data.length();
        for(int i = 0; i < length; i++) {
            JSONObject newsObject = data.getJSONObject(i);
            JSONArray organizations = newsObject.getJSONArray("organizations");
            String organization = "";
            if(organizations.length() > 0) {
                organization = organizations.getJSONObject(0).getString("mention");
            }
            String newsID = newsObject.getString("newsID");
            News news = new News()
                    .setTitle(newsObject.getString("title"))
                    .setPublishTime(newsObject.getString("publishTime"))
                    .setCategory(newsObject.getString("category"))
                    .setOrganization(organization)
                    .setPublisher(newsObject.getString("publisher"))
                    .setImage(newsObject.getString("image"))
                    .setVideo(newsObject.getString("video"))
                    .setNewsID(newsID);
            NewsRecord newsRecord = new NewsRecord(newsID, newsObject.getString("content"));
            news.save();
            newsRecord.save();
            result.add(news);
        }
        return result;
    }

    // 拉取时间区间内的新闻，限定在 page 页
    public static ArrayList<News> crawl(Date startDate, Date endDate, int page) throws IOException {
        URL url = null;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String startDateString = format.format(startDate);
            String endDateString = format.format(endDate);
            String completeUrl = QUERY_URL
                    + "?size=" + PAGE_SIZE
                    + "&startDate=" + URLEncoder.encode(startDateString, "UTF-8")
                    + "&endDate=" + URLEncoder.encode(endDateString, "UTF-8")
                    + "&words="
                    + "&categories="
                    + "&page=" + page;
            Log.i("NewsCrawler", "crawling:" + completeUrl);
            url = new URL(completeUrl);
        } catch (MalformedURLException ignored) {}
        assert url != null;
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonData = new StringBuilder();
        while(true) {
            String line = reader.readLine();
            if(line == null) break;
            jsonData.append(line);
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonData.toString());
            return extractNewsFromJson(jsonObject);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // 拉取时间区间内的新闻
    public static ContinuingCrawling crawl(Date startDate, Date endDate) {
        return new ContinuingCrawling(startDate, endDate);
    }
}
