package com.appstone.newsapiapplication;

import org.json.JSONObject;

public class Source {

    public  String id;
    public  String name;


    public static Source parsejsonObjectSource(JSONObject jsonObject){
        Source item = new Source();

        item.id = jsonObject.optString("id");
        item.name = jsonObject.optString("name");
        return item;
    }
}
