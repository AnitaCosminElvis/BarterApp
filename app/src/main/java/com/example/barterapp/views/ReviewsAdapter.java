package com.example.barterapp.views;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.data.UserReview;
import com.example.barterapp.utility.OperationsUtility;

import java.util.ArrayList;

import static com.example.barterapp.utility.OperationsUtility.*;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private Context                                     mContext;
    private ArrayList<UserReview>                       mReviewsList;
    private ReviewsAdapter.ItemClickListener            mClickListener;


    public ReviewsAdapter(Context context, ArrayList<UserReview> reviewsList){
        mContext = context;
        mReviewsList = reviewsList;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView             mRatingValueTextView;
        public TextView             mReviewTextView;
        public TextView             mFromAliasTextView;
        public RatingBar            mNegativeRatingBar;
        public RatingBar            mPositiveRatingBar;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mRatingValueTextView = itemView.findViewById(R.id.tv_user_review_value);
            mReviewTextView = itemView.findViewById(R.id.tv_user_review_text);
            mFromAliasTextView = itemView.findViewById(R.id.tv_user_review_alias);
            mNegativeRatingBar = itemView.findViewById(R.id.rb_user_review_negative_val);
            mPositiveRatingBar = itemView.findViewById(R.id.rb_user_review_pozitive_val);
            mReviewTextView.setMovementMethod(new ScrollingMovementMethod());
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_review_item, parent, false);
        return new ReviewsAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewViewHolder holder, int position) {
        UserReview currentReview = mReviewsList.get(position);
        float userRatingValue = currentReview.getmRatingValue();
        holder.mRatingValueTextView.setText(String.valueOf(userRatingValue));
        holder.mReviewTextView.setText(currentReview.getmReviewText());
        holder.mFromAliasTextView.setText(currentReview.getmFromAlias());
        if (0 > currentReview.getmRatingValue()) {
            holder.mPositiveRatingBar.setRating(0);
            holder.mNegativeRatingBar.setRating(inverseFloatValueSign(currentReview.getmRatingValue()));
        }else{
            holder.mNegativeRatingBar.setRating(0);
            holder.mPositiveRatingBar.setRating(currentReview.getmRatingValue());
        }
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    public void setProductsList(ArrayList<UserReview> reviewssList){
        mReviewsList = reviewssList;
    }

    // sets the click listener
    public void setClickListener(ReviewsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public UserReview getProductByPosition(int position){
        return mReviewsList.get(position);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int adapterPosition);
    }
}
