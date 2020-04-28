package com.codepath.apps.simpleTwitter.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String profileImageUrl;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = String.format("@%s", jsonObject.getString("screen_name"));
        user.profileImageUrl = jsonObject.getString("profile_image_url_https").replaceFirst("_normal.", "_bigger.");
        return user;
    }
}
