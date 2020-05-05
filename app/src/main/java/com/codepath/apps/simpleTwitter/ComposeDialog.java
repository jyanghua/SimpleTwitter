package com.codepath.apps.simpleTwitter;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpleTwitter.models.Tweet;
import com.codepath.apps.simpleTwitter.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import okhttp3.Headers;

public class ComposeDialog extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "DialogFragment";
    public static final int MAX_TWEET_LENGTH = 280;

    ImageButton ibCancelTweet;
    Button btnTweet;
    EditText etCompose;
    ImageView ivComposeProfile;
    User profile;

    TwitterClient client;

    static ComposeDialog newInstance(User profile) {
        ComposeDialog d = new ComposeDialog();

        // Supply profile input as an argument
        Bundle args = new Bundle();
        args.putParcelable("profile", Parcels.wrap(profile));
        d.setArguments(args);

        return d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        profile = Parcels.unwrap(getArguments().getParcelable("profile"));
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ComposeDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_compose, container, false);

        client = TwitterApp.getRestClient(getContext());

        ibCancelTweet = view.findViewById(R.id.ibCancelTweet);
        btnTweet = view.findViewById(R.id.btnTweet);
        etCompose = view.findViewById(R.id.etCompose);
        ivComposeProfile = view.findViewById(R.id.ivComposeProfile);

        ibCancelTweet.setOnClickListener(this);
        btnTweet.setOnClickListener(this);

        assert profile != null;
        Glide.with(this)
                .load(profile.profileImageUrl)
                .circleCrop()
                .into(ivComposeProfile);

        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tweetContent = etCompose.getText().toString();
                btnTweet.setEnabled(!tweetContent.isEmpty() && tweetContent.length() < 280);
                if (!tweetContent.isEmpty() && tweetContent.length() < MAX_TWEET_LENGTH) {
                    btnTweet.setAlpha(1.0f);
                } else {
                    btnTweet.setAlpha(0.5f);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    // Set click listener on buttons
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ibCancelTweet:
                // Close the dialog and return back to the parent activity
                dismiss();
                break;
            case R.id.btnTweet:
                String tweetContent = etCompose.getText().toString();
                // Make an API call to Twitter to publish the tweet
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess to publish tweet");
                        try {
                            Tweet tweet = Tweet.fromJson(json.jsonObject);
                            Log.i(TAG, "Published tweet says: " + tweet);
                            Toast.makeText(getContext(), "Tweet published", Toast.LENGTH_LONG).show();
                            // Close the dialog and return back to the parent activity
                            dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to publish tweet", throwable);
                        Toast.makeText(getContext(), "Unable to publish tweet", Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }

}
