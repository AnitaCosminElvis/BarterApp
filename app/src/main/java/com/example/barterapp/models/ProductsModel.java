package com.example.barterapp.models;

public class ProductsModel {
    private static volatile ProductsModel       mInstance;

    // private constructor : singleton access
    private ProductsModel() {
        //mDatabase = FirebaseFirestore.getInstance();
        //mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized ProductsModel getInstance() {
        if (mInstance == null) {
            mInstance = new ProductsModel();
        }
        return mInstance;
    }


}
