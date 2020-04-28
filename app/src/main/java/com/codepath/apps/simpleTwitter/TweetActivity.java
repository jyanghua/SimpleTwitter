package com.codepath.apps.simpleTwitter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTwitter.models.Media;
import com.codepath.apps.simpleTwitter.models.Tweet;

import org.parceler.Parcels;

public class TweetActivity extends AppCompatActivity {

    public static final String TAG = "TweetActivity";

    TextView tvTwName;
    TextView tvTwScreenName;
    TextView tvTwBody;
    TextView tvTwTimestamp;
    TextView tvTwRetweetCount;
    TextView tvTwFavoriteCount;

    ImageButton ibTwComment;
    ImageButton ibTwRetweet;
    ImageButton ibTwFavorite;

    ImageView ivTwProfileImage;
    ImageView ivTwTweetImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        final Context context = this;

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbarTweet);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tweet");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Display logo and set clickable
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvTwName = findViewById(R.id.tvTwName);
        tvTwScreenName = findViewById(R.id.tvTwScreenName);
        tvTwBody = findViewById(R.id.tvTwBody);
        tvTwTimestamp = findViewById(R.id.tvTwTimestamp);
        tvTwRetweetCount = findViewById(R.id.tvTwRetweetCount);
        tvTwFavoriteCount = findViewById(R.id. tvTwFavoriteCount);

        ibTwComment = findViewById(R.id. ibTwComment);
        ibTwRetweet = findViewById(R.id. ibTwRetweet);
        ibTwFavorite = findViewById(R.id. ibTwFavorite);

        ivTwProfileImage = findViewById(R.id.ivTwProfileImage);
        ivTwTweetImage = findViewById(R.id.ivTwTweetImage);

        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvTwName.setText(tweet.user.name);
        tvTwScreenName.setText(tweet.user.screenName);
        tvTwBody.setText(Html.fromHtml(tweet.body));
        tvTwTimestamp.setText(tweet.fullTimestamp);
        tvTwRetweetCount.setText(String.valueOf(tweet.retweetCount));
        tvTwFavoriteCount.setText(String.valueOf(tweet.favoriteCount));

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .circleCrop()
                .into(ivTwProfileImage);

        if (tweet.media != null && tweet.media.type.equals(Media.PHOTO)){
            ivTwTweetImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(tweet.media.mediaUrl)
                    .centerCrop()
                    .into(ivTwTweetImage);
        } else {
            ivTwTweetImage.setVisibility(View.GONE);
        }
    }
}
