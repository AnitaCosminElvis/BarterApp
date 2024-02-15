package com.example.barterapp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

 import  com.example.barterapp.R;
import com.example.barterapp.data.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The type Products adapter.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context                 mContext;
    private ArrayList<Product>      mProductsList;
    private ItemClickListener       mClickListener;
    private String                  mCategory;

    /**
     * initializing class members
     *
     * @param context     the context
     * @param productList the product list
     * @param category    the category
     * @return null
     */
    public ProductsAdapter(Context context, ArrayList<Product> productList, String category){
        mContext = context;
        mProductsList = productList;
        mCategory = category;
    }

    /**
     * The type Product view holder.
     */
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView     mTitleTextView;
        public ImageView    mProductImageView;

        /**
         * Instantiates a new Product view holder.
         *
         * @param itemView the item view
         */
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.tv_product_title);
            mProductImageView = itemView.findViewById(R.id.iv_product_image);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(),mCategory);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = mProductsList.get(position);
        holder.mTitleTextView.setText(currentProduct.getmTitle());
        String imgUri = currentProduct.getImgUriPath();
        if (!imgUri.isEmpty())
            Picasso.get().load(imgUri).fit().centerCrop().tag(mContext).into(holder.mProductImageView);
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    /**
     * Set products list.
     *
     * @param productsList the products list
     */
    public void setProductsList(ArrayList<Product> productsList){
        mProductsList = productsList;
    }

    /**
     * Sets click listener.
     *
     * @param itemClickListener the item click listener
     */
// sets the click listener
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * Get product by position product.
     *
     * @param position the position
     * @return the product
     */
    public Product getProductByPosition(int position){
        return mProductsList.get(position);
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
         * @param category        the category
         */
        void onItemClick(View view, int adapterPosition, String category);
    }
}
