package com.java.tanjingyu.components.news;

import android.util.Log;

import com.java.tanjingyu.components.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

// 抓取新闻
public class NewsCrawler {

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
                    .setContent(newsObject.getString("content"))
                    .setPublishTime(newsObject.getString("publishTime"))
                    .setCategory(newsObject.getString("category"))
                    .setOrganization(organization)
                    .setPublisher(newsObject.getString("publisher"))
                    .setImage(newsObject.getString("image"))
                    .setVideo(newsObject.getString("video"))
                    .setNewsId(newsID);
            result.add(news);
        }
        return result;
    }

    // 拉取时间区间内的新闻，限定在 page 页
    public static ArrayList<News> crawl(RequestForm requestForm, int page) throws IOException {
        URL url = null;
        try {
            String requestUrl = requestForm.getRequestUrl(page);
            Log.i("NewsCrawler", "crawling:" + requestUrl);
            url = new URL(requestUrl);
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
    public static ContinuingCrawling crawl(RequestForm requestForm) {
        return new ContinuingCrawling(requestForm);
    }
}
