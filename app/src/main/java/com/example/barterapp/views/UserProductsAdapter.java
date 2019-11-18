package com.example.barterapp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The type User products adapter.
 */
public class UserProductsAdapter extends RecyclerView.Adapter<UserProductsAdapter.UserProductsViewHolder>  {
    private Context                                     mContext;
    private ArrayList<Product>                          mProductList;
    private UserProductsAdapter.ItemClickListener       mClickListener;

    /**
     * Instantiates a new User products adapter.
     *
     * @param context      the context
     * @param productsList the products list
     */
    public UserProductsAdapter(Context context, ArrayList<Product> productsList){
        mContext = context;
        mProductList = productsList;
    }

    /**
     * The type User products view holder.
     */
    public class UserProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView             mTitleTextView;
        public ImageView            mProductPhotoImageView;

        /**
         * Instantiates a new User products view holder.
         *
         * @param itemView the item view
         */
        public UserProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.tv_my_product_title);
            mProductPhotoImageView = itemView.findViewById(R.id.iv_my_product_image);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view,getProductByPosition(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public UserProductsAdapter.UserProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_product_item, parent, false);
        return new UserProductsAdapter.UserProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProductsAdapter.UserProductsViewHolder holder, int position) {
        Product currentProduct = mProductList.get(position);
        holder.mTitleTextView.setText(currentProduct.getmTitle());
        String imgUri = currentProduct.getImgUriPath();
        if (!imgUri.isEmpty())
            Picasso.get().load(imgUri).fit().centerCrop().tag(mContext).into(holder.mProductPhotoImageView);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    /**
     * Set products list.
     *
     * @param productsList the products list
     */
    public void setProductsList(ArrayList<Product> productsList){
        mProductList = productsList;
    }

    /**
     * Sets click listener.
     *
     * @param itemClickListener the item click listener
     */
// sets the click listener
    public void setClickListener(UserProductsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * Get product by position product.
     *
     * @param position the position
     * @return the product
     */
    public Product getProductByPosition(int position){
        return mProductList.get(position);
    }

    /**
     * The interface Item click listener.
     */
// parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        /**
         * On item click.
         *
         * @param view    the view
         * @param product the product
         */
        void onItemClick(View view, Product product);
    }
}
