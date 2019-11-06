package com.example.barterapp.models;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.barterapp.data.Product;
import com.example.barterapp.data.Response;
import com.example.barterapp.utility.DefinesUtility;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static com.example.barterapp.utility.DefinesUtility.*;

public class ProductsModel {
    private static volatile ProductsModel       mInstance;
    private FirebaseAuth                        mAuth;
    private CollectionReference                 mDbProductsCollection;
    private StorageReference                    mStorageRef;
    private UploadTask                          mUploadTask;
    private MutableLiveData<Response>           mAddProductResponseLiveData         = new MutableLiveData<>();
    private MutableLiveData<Response>           mListProductsResponseLiveData       = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mGadgetsLiveData                    = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mClothesLiveData                    = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mToolsLiveData                      = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mBikesLiveData                      = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mUsersProductsLiveData              = new MutableLiveData<>();


    public MutableLiveData<Response> getMutableLiveDataAddProductResponse(){ return mAddProductResponseLiveData; }
    public MutableLiveData<Response> getMutableLiveDataListProductsResponse(){ return mListProductsResponseLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataGadgetsChanged() { return mGadgetsLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataClothesChanged() { return mClothesLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataToolsChanged() { return mToolsLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataBikesChanged() { return mBikesLiveData; }
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataUserProductsChanged() { return mUsersProductsLiveData; }

    // private constructor : singleton access
    private ProductsModel() {
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference(MULTIMEDIA_PATH);
        mDbProductsCollection = FirebaseFirestore.getInstance().collection(PRODUCTS_COLLECTION);

        //add listener for gadgets
        setListenerForProductsByKey(CATEGORY_KEY, CAT_GADGETS);
        //add listener for bikes
        setListenerForProductsByKey(CATEGORY_KEY, CAT_BIKES);
        //add listener for clothes
        setListenerForProductsByKey(CATEGORY_KEY, CAT_CLOTHES);
        //add listener for tools
        setListenerForProductsByKey(CATEGORY_KEY, CAT_TOOLS);

    }

    public static synchronized ProductsModel getInstance() {
        if (mInstance == null) {
            mInstance = new ProductsModel();
        }
        return mInstance;
    }

    public void triggerGetProductsByKeyFilter(String key, String filterVal){
        final MutableLiveData<ArrayList<Product>> productsLiveData;
        mDbProductsCollection.whereEqualTo(key, filterVal)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        products.add(document.toObject(Product.class));
                    }
                    setLiveDataByFilter(products,filterVal);
                } else {
                    mListProductsResponseLiveData.setValue(
                            new Response(task.getException().getMessage(),false));
                }
            }
        });
    }

    private void setLiveDataByFilter(ArrayList<Product> products, String key) {
        switch(key){
            case CAT_GADGETS:{
                mGadgetsLiveData.setValue(products);
                break;
            }
            case CAT_CLOTHES:{
                mClothesLiveData.setValue(products);
                break;
            }
            case CAT_TOOLS:{
                mToolsLiveData.setValue(products);
                break;
            }
            case CAT_BIKES:{
                mBikesLiveData.setValue(products);
                break;
            }
            case CAT_USER_LIST:{
                mUsersProductsLiveData.setValue(products);
                break;
            }
            default: return;
        }
    }

    public void addProductWithMultimedia(Product product, Uri imgUri, Uri vidUri){
        //return if task in progress
        if ((null != mUploadTask) && (true == mUploadTask.isInProgress())) return;

        //get a unique generated id
        String key = mDbProductsCollection.document().getId();
        //set the user uid in the product object
        product.setmUserId(mAuth.getCurrentUser().getUid());
        //set the alias
        product.setAlias(mAuth.getCurrentUser().getDisplayName());
        //set product id
        product.setProductId(key);

        //use the unique to create path for image
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

                //Get the img download URI
                mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return imgRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri imgDownloadUri = task.getResult();

                            //set the products img download uri
                            product.setImgUriPath(imgDownloadUri.toString());

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
                                        mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    throw task.getException();
                                                }

                                                // Continue with the task to get the download URL
                                                return vidRef.getDownloadUrl();
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    Uri vidDownloadUri = task.getResult();

                                                    //set the products video download uri
                                                    product.setVidUriPath(vidDownloadUri.toString());
                                                    //add the product in the realtime db
                                                    saveProductIntoDatabase(key,product);
                                                } else {
                                                    //skip saving the product if there is no video uri
                                                    mAddProductResponseLiveData.setValue(
                                                            new Response("Unable to upload video, product inserted only with image.",
                                                            false));
                                                    saveProductIntoDatabase(key,product);

                                                }
                                            }
                                        });
                                    }
                                });
                            }else {//if no video was selected
                                // add the product in the realtime db
                                saveProductIntoDatabase(key,product);
                            }
                        } else {
                            //skip saving the product if there is no img uri
                            mAddProductResponseLiveData.setValue(
                                    new Response("Img url not found, product was not inserted.",
                                            false));
                        }
                    }
                });
            }
        });

        //ToDo: set other product dependencies
    }

    private void saveProductIntoDatabase(String key, Product product){

        mDbProductsCollection.document(key).set(product)
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

    private void setListenerForProductsByKey(String key, String filterVal) {

        mDbProductsCollection.whereEqualTo(key, filterVal)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) return;

                        ArrayList<Product> products = new ArrayList<>();
                        products = (ArrayList<Product>)value.toObjects(Product.class);
                        setLiveDataByFilter(products,key);
                    }
                });
    }

}
