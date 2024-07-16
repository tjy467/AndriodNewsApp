package com.tjy.newsapp.components.news;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Image {
    private URL url;

    // 从字符串中提取 url
    private static String extractUrl(String urls) {
        urls = urls.trim();
        if(urls.startsWith("[") && urls.endsWith("]")) {
            urls = urls.substring(1, urls.length() - 1);
        }
        String[] strings = urls.split(",");
        for(String url: strings) {
            if(url.isBlank()) continue;
            return url.trim();
        }
        return "";
    }

    public Image(String urls) {
        String url = extractUrl(urls);
        try {
            if(url.isEmpty()) this.url = null;
            else this.url = new URL(url);
        } catch(MalformedURLException e) {
            Log.e("Image Loader", "Failed to parse the url:" + urls + "what the heck");
        }
    }
}
