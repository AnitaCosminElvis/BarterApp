package com.example.barterapp.view_models;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.ProductsModel;

public class AddProductViewModel extends ViewModel {
    private  ProductsModel mProductsModel;

    public AddProductViewModel(ProductsModel prodModel) { mProductsModel = prodModel;}

    public MutableLiveData<Response> getAddProductResponseLiveData() { return mProductsModel.getMutableLiveDataAddProductResponse(); }

    public void addProduct(Product product, Uri imgUri, Uri vidUri) {
        mProductsModel.addProductWithMultimedia(product,imgUri,vidUri);
    }
}
