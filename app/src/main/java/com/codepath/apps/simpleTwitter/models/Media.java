package com.codepath.apps.simpleTwitter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Media {

    public static final String PHOTO = "photo";
    public static final String VIDEO = "video";
    public static final String GIF = "animated_gif";

    public String id;
    public String type;
    public String mediaUrl;

    public static Media fromJson(JSONObject jsonObject) throws JSONException {
        Media media = new Media();
        media.id = jsonObject.getString("id_str");
        media.type = jsonObject.getString("type");
        switch (media.type) {
            case PHOTO:
                media.mediaUrl = jsonObject.getString("media_url_https");
                break;
            case VIDEO:
                JSONObject temp = (JSONObject) jsonObject.getJSONObject("video_info").getJSONArray("variants").get(1);
                media.mediaUrl = temp.getString("url");
                break;
            case GIF:
                media.mediaUrl = jsonObject.getJSONObject("video_info").getJSONObject("variants").getString("url");
                break;
        }
        return media;
    }
}
