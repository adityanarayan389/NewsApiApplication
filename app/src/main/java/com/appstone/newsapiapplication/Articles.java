package com.appstone.newsapiapplication;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Articles {
    public  String author;
    public  String title;
    public  String description;
    public  String url;
    public  String urlToImage;
    public  String publishedAt;
    public  String content;


@SerializedName("source")
    public Source source;

    public static Articles parseJsonobject(JSONObject jsonObject){
        Articles item = new Articles();
        item.author = jsonObject.optString("author");
        item.title = jsonObject.optString("title");
        item.description = jsonObject.optString("description");
        item.url = jsonObject.optString("url");
        item.urlToImage = jsonObject.optString("urlToImage");
        item.publishedAt = jsonObject.optString("publishedAt");
        item.content = jsonObject.optString("content");

        JSONObject sourcejsonObject = jsonObject.optJSONObject("source");
        item.source = Source.parsejsonObjectSource(sourcejsonObject);

        return item;
    }
}
