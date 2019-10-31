package com.example.barterapp.models;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static com.example.barterapp.utility.DefinesUtility.*;

public class ProductsModel {
    private static volatile ProductsModel       mInstance;
    private FirebaseAuth                        mAuth;
    private DatabaseReference                   mDbGadgetsRef;
    private DatabaseReference                   mDbToolsRef;
    private DatabaseReference                   mDbClothesRef;
    private DatabaseReference                   mDbBikesRef;
    private StorageReference                    mStorageRef;
    private UploadTask                          mUploadTask;
    private MutableLiveData<Response>           mAddProductResponseLiveData         = new MutableLiveData<>();
    private MutableLiveData<Response>           mListProductsResponseLiveData        = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mGadgetsLiveData                    = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mClothesLiveData                    = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mToolsLiveData                      = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mBikesLiveData                      = new MutableLiveData<>();


    public MutableLiveData<Response> getMutableLiveDataAddProductResponse(){ return mAddProductResponseLiveData; }
    public MutableLiveData<Response> getMutableLiveDataListProductsResponse(){ return mListProductsResponseLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataGadgetsChanged() { return mGadgetsLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataClothesChanged() { return mClothesLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataToolsChanged() { return mToolsLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataBikesChanged() { return mBikesLiveData; }


    // private constructor : singleton access
    private ProductsModel() {
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("ProductsMultimedia");
        mDbGadgetsRef = FirebaseDatabase.getInstance().getReference("Products/" + CAT_GADGETS);
        mDbBikesRef = FirebaseDatabase.getInstance().getReference("Products/" + CAT_BIKES);
        mDbClothesRef = FirebaseDatabase.getInstance().getReference("Products/" + CAT_CLOTHES);
        mDbToolsRef = FirebaseDatabase.getInstance().getReference("Products/" + CAT_TOOLS);

        //add listener for gadgets
        mDbGadgetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateMutableLiveData(mGadgetsLiveData,dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //add listener for bikes
        mDbBikesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateMutableLiveData(mBikesLiveData,dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //add listener for clothes
        mDbClothesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateMutableLiveData(mClothesLiveData,dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //add listener for tools
        mDbToolsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateMutableLiveData(mToolsLiveData,dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    public static synchronized ProductsModel getInstance() {
        if (mInstance == null) {
            mInstance = new ProductsModel();
        }
        return mInstance;
    }

    public void setSingleEventByProductCategory(String categoryType){
        final MutableLiveData<ArrayList<Product>> productsLiveData;
        Query query                                                     = null;

        if (categoryType.equals(CAT_GADGETS)){
                query = mDbGadgetsRef.orderByChild("mTimeStamp");
                productsLiveData = mGadgetsLiveData;
        }else if (categoryType.equals(CAT_CLOTHES)){
                query = mDbClothesRef.orderByChild("mTimeStamp");
                productsLiveData = mClothesLiveData;
        }else if (categoryType.equals(CAT_TOOLS)){
                query = mDbToolsRef.orderByChild("mTimeStamp");
                productsLiveData = mToolsLiveData;
        }else if (categoryType.equals(CAT_BIKES)){
                query = mDbBikesRef.orderByChild("mTimeStamp");
                productsLiveData = mBikesLiveData;
        }else return;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    updateMutableLiveData(productsLiveData,dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mListProductsResponseLiveData.setValue(new Response("Unable to list products.", false));
            }
        });
    }

    public void addProductWithMultimedia(Product product, Uri imgUri, Uri vidUri){
        //return if task in progress
        if ((null != mUploadTask) && (true == mUploadTask.isInProgress())) return;

        final DatabaseReference dbRef;

        //set the user uid in the product object
        product.setmUserId(mAuth.getCurrentUser().getUid());
        if (product.getmCategory().equals(CAT_GADGETS)){
            dbRef = mDbGadgetsRef;
        }else if (product.getmCategory().equals(CAT_CLOTHES)){
            dbRef = mDbClothesRef;
        }else if (product.getmCategory().equals(CAT_TOOLS)){
            dbRef = mDbToolsRef;
        }else if (product.getmCategory().equals(CAT_BIKES)){
            dbRef = mDbBikesRef;
        }else return;

        String key = dbRef.push().getKey();
        StorageReference imgRef = mStorageRef.child(key).child("img");
        // upload image
        mUploadTask = imgRef.putFile(imgUri);

        //add listeners to the upload image action
        mUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                mAddProductResponseLiveData.setValue(
                        new Response("Unable to upload image.",false));
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful image uploading

                //Get the download URI
                Task<Uri> taskImgUri = continueToGetTheDownloadUrl(imgRef);

                if (null != vidUri) {// if the video exists continue and upload it
                    StorageReference vidRef = mStorageRef.child(key).child("vid");
                    mUploadTask = vidRef.putFile(vidUri);

                    // add listeners to the upload video action
                    mUploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful video upload
                            mAddProductResponseLiveData.setValue(
                                    new Response("Unable to upload video.",false));
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //video has successfully uploaded

                            //Get the download URI
                            Task<Uri> taskVidUri = continueToGetTheDownloadUrl(vidRef);
                            //Set the download URI
                            product.setImgUriPath(taskVidUri.getResult().toString());

                            //add the product in the realtime db
                            saveProductIntoDatabase(product,dbRef,key);
                        }
                    });
                }else {//if no video was selected
                    // add the product in the realtime db
                    saveProductIntoDatabase(product,dbRef,key);
                }
            }
        });

        //ToDo: set other product dependencies
    }

    private Task<Uri> continueToGetTheDownloadUrl(StorageReference imgRef) {
        return mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imgRef.getDownloadUrl();
            }
        });
    }

    private void saveProductIntoDatabase(Product product, DatabaseReference dbRef, String key){
        dbRef.child(key).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mAddProductResponseLiveData.setValue(new Response("Product added successfully.",true));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mAddProductResponseLiveData.setValue(new Response(e.getMessage(),false));
            }
        });
    }

    private void updateMutableLiveData(MutableLiveData<ArrayList<Product>> productsLiveData,
                                       DataSnapshot dataSnapshot){
        Iterable<DataSnapshot> productCollection =  dataSnapshot.getChildren();
        ArrayList<Product> auxList = new ArrayList<>();

        for (DataSnapshot productDataSnapshot : productCollection){
            Product product = productDataSnapshot.getValue(Product.class);
            auxList.add(product);
        }

        productsLiveData.setValue(auxList);
    }
}
