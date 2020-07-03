package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet {

    public static final String TAG = "Tweet";

    public String body;
    public String createdAt;
    public long id;
    public User user;
    public List<String> pictureUrls;

    // empty constructor for parceler library
    public Tweet() {}


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = getRelativeTimestamp(jsonObject.getString("created_at"));
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");

        try {
            tweet.pictureUrls = getImageUrls(jsonObject.getJSONObject("entities"));
        } catch(JSONException e) {
            tweet.pictureUrls = new ArrayList<>();
        }

        return tweet;
    }


    private static List<String> getImageUrls(JSONObject jsonObject) throws JSONException {
        List<String> urls = new ArrayList<>();

        if (jsonObject == null) {
            return urls;
        }

        JSONArray jsonArray = jsonObject.getJSONArray("media");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            String url = obj.getString("media_url_https");
            if (url.contains("media")) {
                urls.add(url);
            }
        }
        return urls;
    }


    private static String getRelativeTimestamp(String rawDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawDate).getTime();
            int abbreviated_flag = DateUtils.FORMAT_ABBREV_RELATIVE;
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, abbreviated_flag).toString();
            if (relativeDate.contains("min")) {
                relativeDate = relativeDate.substring(0, relativeDate.length() - 9) + "m";
            } else if (relativeDate.contains("hr")) {
                relativeDate = relativeDate.substring(0, relativeDate.length() - 8) + "h";
            } else if (relativeDate.contains("sec")) {
                relativeDate = relativeDate.substring(0, relativeDate.length() - 9) + "s";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }



}
