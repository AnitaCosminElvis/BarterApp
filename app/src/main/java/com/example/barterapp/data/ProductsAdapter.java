package com.example.barterapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barterapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context                 mContext;
    private ArrayList<Product>      mProductsList;

    public ProductsAdapter(Context context, ArrayList<Product> productList){
        mContext = context;
        mProductsList = productList;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView     mTitleTextView;
        public ImageView    mProductImageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.tv_product_title);
            mProductImageView = itemView.findViewById(R.id.iv_product_image);
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
        Picasso.get().load(currentProduct.getImgUriPath()).fit().centerCrop().
                tag(mContext).into(holder.mProductImageView);
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }
}
