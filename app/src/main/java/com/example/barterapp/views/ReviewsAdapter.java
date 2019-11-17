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

import java.util.ArrayList;

/**
 * The type Reviews adapter.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private Context                                     mContext;
    private ArrayList<UserReview>                       mReviewsList;
    private ReviewsAdapter.ItemClickListener            mClickListener;


    /**
     * Instantiates a new Reviews adapter.
     *
     * @param context     the context
     * @param reviewsList the reviews list
     */
    public ReviewsAdapter(Context context, ArrayList<UserReview> reviewsList){
        mContext = context;
        mReviewsList = reviewsList;
    }

    /**
     * The type Review view holder.
     */
    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The M rating value text view.
         */
        public TextView             mRatingValueTextView;
        /**
         * The M review text view.
         */
        public TextView             mReviewTextView;
        /**
         * The M from alias text view.
         */
        public TextView             mFromAliasTextView;
        /**
         * The M negative rating bar.
         */
        public RatingBar            mNegativeRatingBar;
        /**
         * The M positive rating bar.
         */
        public RatingBar            mPositiveRatingBar;

        /**
         * Instantiates a new Review view holder.
         *
         * @param itemView the item view
         */
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
            holder.mNegativeRatingBar.setRating(2 + currentReview.getmRatingValue());
        }else{
            holder.mNegativeRatingBar.setRating(0);
            holder.mPositiveRatingBar.setRating(currentReview.getmRatingValue());
        }
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    /**
     * Set products list.
     *
     * @param reviewssList the reviewss list
     */
    public void setProductsList(ArrayList<UserReview> reviewssList){
        mReviewsList = reviewssList;
    }

    /**
     * Sets click listener.
     *
     * @param itemClickListener the item click listener
     */
// sets the click listener
    public void setClickListener(ReviewsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * Get product by position user review.
     *
     * @param position the position
     * @return the user review
     */
    public UserReview getProductByPosition(int position){
        return mReviewsList.get(position);
    }

    /**
     * The interface Item click listener.
     */
// parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        /**
         * On item click.
         *
         * @param view            the view
         * @param adapterPosition the adapter position
         */
        void onItemClick(View view, int adapterPosition);
    }
}
