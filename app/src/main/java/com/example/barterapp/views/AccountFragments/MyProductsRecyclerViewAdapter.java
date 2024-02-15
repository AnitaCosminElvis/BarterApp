package com.example.barterapp.views.AccountFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Product} and makes a call to the
 * specified {@link MyProductsFragment.OnMyProductInteractionListener}.
 */
public class MyProductsRecyclerViewAdapter extends RecyclerView.Adapter<MyProductsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Product> mValues         = new ArrayList<>();
    private final MyProductsFragment.OnMyProductInteractionListener mListener;

    /**
     * Instantiates a new My products recycler view adapter.
     *
     * @param listener the listener
     */
    public MyProductsRecyclerViewAdapter(MyProductsFragment.OnMyProductInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleTextView.setText(mValues.get(position).getmTitle());
        String uri = mValues.get(position).getImgUriPath();

        if (!uri.isEmpty()) Picasso.get().load(uri).fit().centerCrop().tag(this).into(holder.mImageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.OnMyProductInteractionListener(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Sets values.
     *
     * @param myProducts the my products
     */
    public void setValues(ArrayList<Product> myProducts) {
        mValues = myProducts;
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View       mView;
        public final TextView   mTitleTextView;
        public final ImageView  mImageView;
        public Product          mItem;

        /**
         * Instantiates a new View holder.
         *
         * @param view the view
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleTextView = view.findViewById(R.id.tv_my_product_title);
            mImageView = view.findViewById(R.id.iv_my_product_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "My Products" + "'";
        }
    }
}
