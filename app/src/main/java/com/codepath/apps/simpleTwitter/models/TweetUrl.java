package com.codepath.apps.simpleTwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class TweetUrl {

    public String url;
    public String expandedUrl;
    public String displayUrl;

    public static TweetUrl fromJson(JSONObject jsonObject) throws JSONException {
        TweetUrl tweetUrl = new TweetUrl();
        tweetUrl.url = jsonObject.getString("url");
        tweetUrl.expandedUrl = jsonObject.getString("expanded_url");
        tweetUrl.displayUrl = jsonObject.getString("display_url");

        return tweetUrl;
    }
}
