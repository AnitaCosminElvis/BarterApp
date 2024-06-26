package com.example.barterapp.models;

import static com.example.barterapp.utility.DefinesUtility.*;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.barterapp.data.Alias;
import com.example.barterapp.data.Response;
import com.example.barterapp.data.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The Authentification model handles the requests and responses for FirebaseAuth and FirebaseFirestore
 */
    public class AuthentificationModel {
        private static volatile AuthentificationModel mInstance;
        private FirebaseAuth mAuth;
        private FirebaseUser mCurrentUser;
        private FirebaseFirestore mDatabase;
        private MutableLiveData<Response> mLoginResponseLiveData = new MutableLiveData<>();
        private MutableLiveData<Response> mRegisterResponseLiveData = new MutableLiveData<>();
        private MutableLiveData<Response> mResetPassResponseLiveData = new MutableLiveData<>();
        private MutableLiveData<UserProfile> mUserProfileLiceData = new MutableLiveData<>();

    // private constructor : singleton access
    private AuthentificationModel() {
        mDatabase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized AuthentificationModel getInstance() {
        if (mInstance == null) {
            mInstance = new AuthentificationModel();
        }
        return mInstance;
    }

    /**
     * Get mutable live data login response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveDataLoginResponse(){ return mLoginResponseLiveData; }

    /**
     * Get mutable live data register response mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveDataRegisterResponse(){ return mRegisterResponseLiveData; }

    /**
     * Get mutable live data reset pass mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<Response> getMutableLiveDataResetPass(){ return mResetPassResponseLiveData; }

    /**
     * Get mutable live data user profile mutable live data.
     *
     * @return the mutable live data
     */
    public MutableLiveData<UserProfile> getMutableLiveDataUserProfile(){ return mUserProfileLiceData; }

    /**
     * Sign in.
     *
     * @param email the email
     * @param pass  the pass
     */
    public void signIn(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            mCurrentUser = mAuth.getCurrentUser();
                            mLoginResponseLiveData.setValue(new Response(SUCC_LOGIN,true));
                        } else {
                            // Sign in failed
                            mLoginResponseLiveData.setValue(new Response(ERR_LOGIN,false));
                        }
                    }
                });
    }

    /**
     * Sign up.
     *
     * @param userProfile the user profile
     * @param pass        the pass
     */
//Registers the a new user according to the profile and password
    public void signUp(UserProfile userProfile, String pass) {
        mDatabase.collection(ALIASES_COLLECTION).document(userProfile.getmAlias()).get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Alias exists -> not creating a new user
                        mRegisterResponseLiveData.setValue(new Response(ERR_ALIAS,false));
                    } else {
                        mAuth.createUserWithEmailAndPassword(userProfile.getmEmail(), pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign up success
                                    mCurrentUser = mAuth.getCurrentUser();
                                    String uId = mCurrentUser.getUid();

                                    // Populate the user's profile
                                    mDatabase.collection(USERS_COLLECTION)
                                            .document(uId)
                                            .set(userProfile);
                                    mDatabase.collection(ALIASES_COLLECTION)
                                            .document(userProfile.getmAlias())
                                            .set(new Alias(userProfile.getmAlias()));

                                    //set the display name for the user in
                                    UserProfileChangeRequest profileUpdates =
                                            new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(userProfile.getmAlias()).build();
                                    mCurrentUser.updateProfile(profileUpdates);

                                    // Sign up failed succeeded
                                    mRegisterResponseLiveData.setValue(
                                            new Response(SUCC_REGISTER,true));
                                } else {
                                    // Sign up failed
                                    mRegisterResponseLiveData.setValue(
                                            new Response(ERR_REGISTER,false));
                                }
                            }
                        });
                    }
                } else {
                    // Rare exception
                    mRegisterResponseLiveData.setValue(
                            new Response(task.getException().toString(),false));
                }


            }
        });
    }

    /**
     * Reset password.
     *
     * @param email the email
     */
//Resets the password on the desired email address
    public void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // mail was sent successfully.
                            mResetPassResponseLiveData
                                    .setValue(new Response(SUCC_RESET_PASS,true));
                        } else {
                            mResetPassResponseLiveData
                                    .setValue(new Response(task.getException().getMessage(),true));
                        }
                    }
                });
    }

    /**
     * Get user profile boolean.
     *
     * @return the boolean
     */
//Gets the Users Profile
    public boolean getUserProfile(){

        //if not signed in, no use of continuing
        if (!isUserSignedIn()) return false;

        DocumentReference docRef = mDatabase.collection(USERS_COLLECTION).document(mCurrentUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    UserProfile userProfile = task.getResult().toObject(UserProfile.class);
                    mUserProfileLiceData.setValue(userProfile);
                }
                //
            }
        });

        //request completed successfully
        return true;
    }

    /**
     * Sign out.
     */
//Signs out from the current user
    public void signOut(){
        if (null != mAuth) {
            mAuth.signOut();
        }
    }

    /**
     * Get user email string.
     *
     * @return the string
     */
    public String getUserEmail(){
        String userEmail = "anonymous@user.com";

        if (null != mAuth.getCurrentUser()){
            userEmail = mAuth.getCurrentUser().getEmail();
        }

        return userEmail;
    }

    /**
     * Get user alias string.
     *
     * @return the string
     */
    public String getUserAlias(){
        String userAlias = "anonymous";

        if (null != mAuth.getCurrentUser()){
            userAlias = mAuth.getCurrentUser().getDisplayName();
        }

        return userAlias;
    }

    /**
     * Is user signed in boolean.
     *
     * @return the boolean
     */
    public boolean isUserSignedIn(){
        if (null != mAuth.getCurrentUser()) return true;
        else return false;
    }

    /**
     * Gets current user id.
     *
     * @return the current user id
     */
    public String getCurrentUserId() { return mAuth.getCurrentUser().getUid(); }
}
