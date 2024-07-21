package com.java.tanjingyu.components;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.java.tanjingyu.components.record.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

// 通过 HTTP 方式访问 GLM 生成摘要
class GLMHelper implements Runnable {
    private final News news;
    private Thread thread;
    private OnGLMOutputListener listener;

    private static final String API_KEY = "2c51b401005bf9721899cb1f5cefd807.ajS2MWhsA8rMtYgU";
    private static final String API_URL = "https://open.bigmodel.cn/api/paas/v4/chat/completions";
    private static final String PROMPT = "请为下面的新闻总结摘要，不超过50字：";

    public GLMHelper(News news) {
        this.news = news;
        listener = null;
    }

    // 获取请求体
    private String getBody(String content) throws JSONException {
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", PROMPT + content);
        return "{\"model\":\"glm-4\",\"messages\":[" + message + "]}";
    }

    private HttpURLConnection openConnection() throws IOException {
        URL url = null;
        try {
            url = new URL(API_URL);
        } catch (MalformedURLException ignored) {}
        assert url != null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    public interface OnGLMOutputListener {
        void onGLMOutput(@Nullable String output);
    }

    public void setOnGLMOutputListener(OnGLMOutputListener listener) {
        this.listener = listener;
    }

    // 获取返回的结果
    private String extract(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray choices = jsonObject.getJSONArray("choices");
        JSONObject choice = choices.getJSONObject(0);
        String finishReason = choice.getString("finish_reason");

        // 非正常返回
        if(!finishReason.equals("stop")) return null;
        JSONObject message = choice.getJSONObject("message");
        String content = message.getString("content");
        return content.substring(1, content.length() - 1);
    }

    @Override
    public void run() {
        String output = null;
        try {
            HttpURLConnection connection = openConnection();
            String body = getBody(news.getContent());
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes(StandardCharsets.UTF_8));
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while (true) {
                String line = inputReader.readLine();
                if (line == null) break;
                builder.append(line);
            }
            String response = builder.toString();
            output = extract(response);
        } catch (Exception ignored) {

        } finally {
            if (listener != null) {
                String finalOutput = output;
                new Handler(Looper.getMainLooper()).post(() -> listener.onGLMOutput(finalOutput));
            }
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }
}
