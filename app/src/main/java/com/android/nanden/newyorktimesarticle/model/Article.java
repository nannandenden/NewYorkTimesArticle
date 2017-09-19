package com.android.nanden.newyorktimesarticle.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanden on 9/19/17.
 */

public class Article implements Serializable {

    private String webUrl;
    private String headline;
    private String thumbNail;

    public Article(JSONObject jsonObject) {
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");
            // some article has image and some does not have
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if (multimedia.length() > 0) {
                // if there is more than one image, just set the first one as thumbnail
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbNail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                this.thumbNail = "";
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Article> fromJsonArray(JSONArray resultJsonArray) {
        List<Article> result = new ArrayList<>();
        for (int i = 0; i < resultJsonArray.length(); i++) {
            try {
                result.add(new Article(resultJsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbNail() {
        return thumbNail;
    }
}
