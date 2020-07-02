package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;
    // pass in context and list of tweets


    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }


    // bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data at position
        Tweet tweet = tweets.get(position);

        // bind tweet with view holder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // clean all elements of recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // add list of tweets to dataset
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }


    // define viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvUsername;
        TextView tvTimestamp;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.tweet_profile_iv);
            tvBody = itemView.findViewById(R.id.tweet_body_tv);
            tvUsername = itemView.findViewById(R.id.tweet_username_tv);
            tvTimestamp = itemView.findViewById(R.id.tweet_timestamp);
            ivImage = itemView.findViewById(R.id.tweet_image);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvUsername.setText(tweet.user.username);
            tvTimestamp.setText(tweet.createdAt);

            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .into(ivProfileImage);

            if (tweet.pictureUrls.size() > 0) {
                ivImage.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.pictureUrls.get(0))
                        .into(ivImage);
            } else {
                ivImage.setVisibility(View.GONE);
            }
        }

    }

}
