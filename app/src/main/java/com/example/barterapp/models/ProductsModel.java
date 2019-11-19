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

/**
 * The type Products model.
 */
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
    private MutableLiveData<ArrayList<Product>> mOtherProductsLiveData              = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mMyProductsLiveData                 = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mUserProductsLiveData              = new MutableLiveData<>();


    /**
     * Get mutable live data add product response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveDataAddProductResponse(){
        return mAddProductResponseLiveData;
    }

    /**
     * Get mutable live data list products response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveDataListProductsResponse(){
        return mListProductsResponseLiveData;
    }

    /**
     * Gets mutable live data gadgets changed.
     *
     * @return the mutable live data gadgets changed
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataGadgetsChanged() {
        return mGadgetsLiveData;
    }

    /**
     * Gets mutable live data clothes changed.
     *
     * @return the mutable live data clothes changed
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataClothesChanged() {
        return mClothesLiveData;
    }

    /**
     * Gets mutable live data tools changed.
     *
     * @return the mutable live data tools changed
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataToolsChanged() {
        return mToolsLiveData;
    }

    /**
     * Gets mutable live data bikes changed.
     *
     * @return the mutable live data bikes changed
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataBikesChanged() {
        return mBikesLiveData;
    }

    /**
     * Gets mutable live data other changed.
     *
     * @return the mutable live data other changed
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataOtherChanged() { return mOtherProductsLiveData; }

    /**
     * Gets mutable live data my products.
     *
     * @return the mutable live data my products
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataMyProducts() { return
            mMyProductsLiveData;
    }

    /**
     * Gets mutable live data user products.
     *
     * @return the mutable live data user products
     */
    public MutableLiveData<ArrayList<Product>> getMutableLiveDataUserProducts() {
        return mUserProductsLiveData;
    }

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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized ProductsModel getInstance() {
        if (mInstance == null) {
            mInstance = new ProductsModel();
        }
        return mInstance;
    }

    /**
     * Trigger get my products.
     */
    public void triggerGetMyProducts(){
        mDbProductsCollection.whereEqualTo(USER_ID_KEY, mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        products.add(document.toObject(Product.class));
                    }
                    mMyProductsLiveData.setValue(products);
                } else {
                    mListProductsResponseLiveData.setValue(
                            new Response(task.getException().getMessage(),false));
                }
            }
        });
    }

    /**
     * Trigger get products by user id.
     *
     * @param userIdKey the user id key
     * @param userId    the user id
     */
    public void triggerGetProductsByUserId(String userIdKey, String userId){
        mDbProductsCollection.whereEqualTo(userIdKey, userId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        products.add(document.toObject(Product.class));
                    }
                    mUserProductsLiveData.setValue(products);
                } else {
                    mListProductsResponseLiveData.setValue(
                            new Response(task.getException().getMessage(),false));
                }
            }
        });
    }


    /**
     * Trigger get products by category.
     *
     * @param key       the key
     * @param filterVal the filter val
     */
    public void triggerGetProductsByCategory(String key, String filterVal){
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
            case CAT_OTHER:{
                mOtherProductsLiveData.setValue(products);
            }
            default: return;
        }
    }

    /**
     * Add product with multimedia.
     *
     * @param product the product
     * @param imgUri  the img uri
     * @param vidUri  the vid uri
     */
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
                                        mUploadTask.
                                        continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task)
                                            throws Exception {
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
                                                            new Response(ERR_UPLOAD_VID,
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
                                    new Response(ERR_PROD_NOT_ADDED,
                                            false));
                        }
                    }
                });
            }
        });
    }

    private void saveProductIntoDatabase(String key, Product product){

        mDbProductsCollection.document(key).set(product)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mAddProductResponseLiveData.setValue(
                            new Response(SUCC_PROD_ADDED,true));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mAddProductResponseLiveData.setValue(
                            new Response(e.getMessage(),false));
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

    /**
     * Delete product by id.
     *
     * @param getmProductId the getm product id
     */
    public void deleteProductById(String getmProductId) {
        mDbProductsCollection.document(getmProductId).delete();
    }
}
