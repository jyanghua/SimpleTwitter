package com.codepath.apps.simpleTwitter.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTwitter.R;
import com.codepath.apps.simpleTwitter.TweetActivity;
import com.codepath.apps.simpleTwitter.TwitterApp;
import com.codepath.apps.simpleTwitter.TwitterClient;
import com.codepath.apps.simpleTwitter.models.Media;
import com.codepath.apps.simpleTwitter.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    public final static String TAG = "TweetsAdapter";

    Context context;
    List<Tweet> tweets;

    // Pass in context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get data at position
        final Tweet tweet = tweets.get(position);
        // Bind the tweet with view holder
        holder.bind(tweet);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TweetActivity.class);
                i.putExtra("tweet", Parcels.wrap(tweet));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        TwitterClient client;
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvName;
        TextView tvScreenName;
        TextView tvRetweetCount;
        TextView tvFavoriteCount;
        TextView tvTimestamp;
        ToggleButton tbFavorite;
        ToggleButton tbRetweet;
        ImageView ivTweetImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvRetweetCount = itemView.findViewById(R.id.tvRetweetCount);
            tvFavoriteCount = itemView.findViewById(R.id.tvFavoriteCount);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            ivTweetImage = itemView.findViewById(R.id.ivTweetImage);
            tbFavorite = itemView.findViewById(R.id.tbFavorite);
            tbRetweet = itemView.findViewById(R.id.tbRetweet);
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(Html.fromHtml(tweet.body));
            tvBody.setMovementMethod(LinkMovementMethod.getInstance());
            tvName.setText(tweet.user.name);
            tvScreenName.setText(tweet.user.screenName);
            tvRetweetCount.setText(String.valueOf(tweet.retweetCount));
            tvFavoriteCount.setText(String.valueOf(tweet.favoriteCount));
            tvTimestamp.setText(tweet.relativeTimestamp);

            client = TwitterApp.getRestClient(context);

            if (tweet.retweeted) {
                tbRetweet.setChecked(true);
            } else {
                tbRetweet.setChecked(false);
            }

            if (tweet.favorited) {
                tbFavorite.setChecked(true);
            } else {
                tbFavorite.setChecked(false);
            }

            tbRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tbRetweet.isChecked()) {
                        // Enable the toggle, favorites the tweet
                        client.retweetTweet(tweet.id, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                tbRetweet.setChecked(true);
                                tweet.retweeted = true;
                                Log.i(TAG, "onSuccess favorite: " + tweet.body);
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                tbRetweet.setChecked(false);
                                Log.e(TAG, "onFailure favorite: " + tweet.body + " \nResponse: " + response);
                            }
                        });
                    } else {
                        // Disable the toggle, unfavorites the tweet
                        client.unRetweetTweet(tweet.id, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                tbRetweet.setChecked(false);
                                tweet.retweeted = false;
                                Log.i(TAG, "onSuccess unfavorite: " + tweet.body);
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                tbRetweet.setChecked(true);
                                Log.e(TAG, "onFailure unfavorite: " + tweet.body);
                            }
                        });
                    }
                }
            });

//            tvRetweetCount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (tbRetweet.isChecked()) {
//                        tbRetweet.setChecked(false);
//                    } else {
//                        tbRetweet.setChecked(true);
//                    }
//                }
//            });

            tbFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tbFavorite.isChecked()) {
                        // Enable the toggle, favorites the tweet
                        client.favoriteTweet(tweet.id, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                tbFavorite.setChecked(true);
                                tweet.favorited = true;
                                Log.i(TAG, "onSuccess favorite: " + tweet.body);
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                tbFavorite.setChecked(false);
                                Log.e(TAG, "onFailure favorite: " + tweet.body + " \nResponse: " + response);
                            }
                        });
                    } else {
                        // Disable the toggle, unfavorites the tweet
                        client.unFavoriteTweet(tweet.id, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                tbFavorite.setChecked(false);
                                tweet.favorited = false;
                                Log.i(TAG, "onSuccess unfavorite: " + tweet.body);
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                tbFavorite.setChecked(true);
                                Log.e(TAG, "onFailure unfavorite: " + tweet.body);
                            }
                        });
                    }
                }
            });

//            tvFavoriteCount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (tbFavorite.isChecked()) {
//                        tbFavorite.setChecked(false);
//                    } else {
//                        tbFavorite.setChecked(true);
//                    }
//                }
//            });

            tvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, TweetActivity.class);
                    i.putExtra("tweet", Parcels.wrap(tweet));
                    context.startActivity(i);
                }
            });

            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .circleCrop()
                    .into(ivProfileImage);

            if (tweet.media != null && tweet.media.type.equals(Media.PHOTO)){
                ivTweetImage.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.media.mediaUrl)
                        .centerCrop()
                        .into(ivTweetImage);
            } else {
                ivTweetImage.setVisibility(View.GONE);
            }
        }
    }
}

