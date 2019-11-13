package com.example.barterapp.views.AccountFragments;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.example.barterapp.views.AccountFragments.ReviewsFragment.OnHistoryInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Offer} and makes a call to the
 * specified {@link OnHistoryInteractionListener}.
 */
public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Offer>                        mValues;
    private final OnHistoryInteractionListener      mListener;

    public ReviewsRecyclerViewAdapter(ArrayList<Offer> values, OnHistoryInteractionListener listener) {
        mListener = listener;
        mValues = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mFromAlias.setText(mValues.get(position).getmFromAlias());
        holder.mToAlias.setText(mValues.get(position).getmToAlias());
        String uri = mValues.get(position).getmProductImgUri();
        if (!uri.isEmpty()) Picasso.get().load(uri).fit().centerCrop().tag(this).into(holder.mProductImageView);

        if (mValues.get(position).ismIsAccepted()) {
            holder.mOfferStateImageView.setImageResource(R.drawable.ic_accept_violet_50dp);
        }else{
            holder.mOfferStateImageView.setImageResource(R.drawable.ic_cancel_orange_50dp);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnHistoryInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setValues(ArrayList<Offer> values){
        mValues = values;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View           mView;
        public final TextView       mToAlias;
        public final TextView       mFromAlias;
        public final ImageView      mProductImageView;
        public final ImageView      mOfferStateImageView;

        public Offer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mToAlias = view.findViewById(R.id.tv_review_to);
            mFromAlias = view.findViewById(R.id.tv_review_from);
            mProductImageView = view.findViewById(R.id.iv_review_prod_img);
            mOfferStateImageView = view.findViewById(R.id.iv_review_state);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "Review" + "'";
        }
    }
}
