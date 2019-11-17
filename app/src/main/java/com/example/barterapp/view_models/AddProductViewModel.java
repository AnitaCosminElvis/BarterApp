package com.example.barterapp.view_models;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.models.ProductsModel;

/**
 * The type Add product view model.
 */
public class AddProductViewModel extends ViewModel {
    private  ProductsModel mProductsModel;

    /**
     * Instantiates a new Add product view model.
     *
     * @param prodModel the prod model
     */
    public AddProductViewModel(ProductsModel prodModel) { mProductsModel = prodModel;}

    /**
     * Gets add product response live data.
     *
     * @return the add product response live data
     */
    public MutableLiveData<Response> getAddProductResponseLiveData() { return mProductsModel.getMutableLiveDataAddProductResponse(); }

    /**
     * Add product.
     *
     * @param product the product
     * @param imgUri  the img uri
     * @param vidUri  the vid uri
     */
    public void addProduct(Product product, Uri imgUri, Uri vidUri) {
        mProductsModel.addProductWithMultimedia(product,imgUri,vidUri);
    }
}
