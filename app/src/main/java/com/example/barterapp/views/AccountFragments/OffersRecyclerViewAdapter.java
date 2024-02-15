package com.example.barterapp.views.AccountFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Offer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Offer} and makes a call to the
 * specified {@link OffersFragment.OnOfferInteractionListener}.
 */
public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Offer>                                mValues;
    private final OffersFragment.OnOfferInteractionListener mListener;

    /**
     * Instantiates a new Offers recycler view adapter.
     *
     * @param values   the values
     * @param listener the listener
     */
    public OffersRecyclerViewAdapter(ArrayList<Offer> values,OffersFragment.OnOfferInteractionListener listener) {
        mListener = listener;
        mValues = values;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_offers_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String alias = mValues.get(position).getmFromAlias();
        String uri = mValues.get(position).getmProductImgUri();

        if (alias.isEmpty()) alias = mValues.get(position).getmContactEmail();

        holder.mAliasTextView.setText(alias);

        if (!uri.isEmpty()) Picasso.get().load(uri).fit().centerCrop().tag(this).into(holder.mProductImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnOfferInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Set values.
     *
     * @param values the values
     */
    public void setValues(ArrayList<Offer> values){ mValues = values;}

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View               mView;
        public final TextView           mAliasTextView;
        public final ImageView          mProductImageView;
        public Offer                    mItem;

        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAliasTextView = view.findViewById(R.id.tv_offer_alias);
            mProductImageView = view.findViewById(R.id.iv_offer_img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAliasTextView.getText() + "'";
        }
    }
}
