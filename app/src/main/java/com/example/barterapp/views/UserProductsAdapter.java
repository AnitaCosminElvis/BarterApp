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

public class UserProductsAdapter extends RecyclerView.Adapter<UserProductsAdapter.UserProductsViewHolder>  {
    private Context                                     mContext;
    private ArrayList<Product>                          mProductList;
    private UserProductsAdapter.ItemClickListener       mClickListener;

    public UserProductsAdapter(Context context, ArrayList<Product> productsList){
        mContext = context;
        mProductList = productsList;
    }

    public class UserProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView             mTitleTextView;
        public ImageView            mProductPhotoImageView;

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

    public void setProductsList(ArrayList<Product> productsList){
        mProductList = productsList;
    }

    // sets the click listener
    public void setClickListener(UserProductsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public Product getProductByPosition(int position){
        return mProductList.get(position);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Product product);
    }
}
