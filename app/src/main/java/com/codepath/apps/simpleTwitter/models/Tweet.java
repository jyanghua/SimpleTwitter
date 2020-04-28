package com.codepath.apps.simpleTwitter.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    public String body;
    public String createdAt;
    public long id;
    public User user;
    public int retweetCount;
    public int favoriteCount;
    public List<TweetUrl> tweetUrls;
    public Media media;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("full_text");
        tweet.createdAt = getFormattedTimestamp(jsonObject.getString("created_at"));
        tweet.id = jsonObject.getLong("id");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.favoriteCount = jsonObject.getInt("favorite_count");

        try {
            JSONArray temp = jsonObject.getJSONObject("entities").getJSONArray("urls");
            tweet.tweetUrls = new ArrayList<>() ;
            for (int i = 0; i < temp.length(); i++) {
                JSONObject tempObj = (JSONObject) temp.get(i);
                tweet.tweetUrls.add(TweetUrl.fromJson(tempObj));
            }

        } catch (Exception e) {
//            Log.e("Url", "test", e);
        }

        try {
            tweet.media = Media.fromJson((JSONObject) jsonObject.getJSONObject("extended_entities").getJSONArray("media").get(0));
            tweet.body = tweet.body.replace(tweet.media.url, "");
        } catch (Exception e) {
//            Log.e("Media", "No extended entity", e);
        }
        if (tweet.tweetUrls != null) {
            tweet.body = formatBodyUrls(tweet.body, tweet.tweetUrls);
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public static String getFormattedTimestamp(String createdAt) {
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public static String formatBodyUrls(String body, List<TweetUrl> tweetUrls) {
        String formatted = body;
        for (int i = 0; i < tweetUrls.size(); i++) {
            formatted = formatted.replace(tweetUrls.get(i).url, String.format("<a href='%s'>%s</a>", tweetUrls.get(i).expandedUrl, tweetUrls.get(i).displayUrl));
        }
        return formatted;
    }

}
