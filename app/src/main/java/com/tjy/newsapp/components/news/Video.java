package com.tjy.newsapp.components.news;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Video {
    private URL url;

    public Video(String url) {
        try {
            this.url = new URL(url);
        } catch(MalformedURLException e) {
            Log.e("IMAGE", "Failed to parse the url:" + url);
        }
    }
}
