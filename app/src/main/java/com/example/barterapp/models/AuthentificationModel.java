package com.example.barterapp.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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

public class AuthentificationModel {
    private static volatile AuthentificationModel   mInstance;
    private FirebaseAuth                            mAuth;
    private FirebaseUser                            mCurrentUser;
    private FirebaseFirestore                       mDatabase;
    private MutableLiveData<Response>               mLoginResponseLiveData          = new MutableLiveData<>();
    private MutableLiveData<Response>               mRegisterResponseLiveData       = new MutableLiveData<>();
    private MutableLiveData<Response>               mResetPassResponseLiveData      = new MutableLiveData<>();
    private MutableLiveData<UserProfile>            mUserProfileLiceData            = new MutableLiveData<>();

    // private constructor : singleton access
    private AuthentificationModel() {
        mDatabase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized AuthentificationModel getInstance() {
        if (mInstance == null) {
            mInstance = new AuthentificationModel();
        }
        return mInstance;
    }

    public MutableLiveData<Response> getMutableLiveDataLoginResponse(){ return mLoginResponseLiveData; }
    public MutableLiveData<Response> getMutableLiveDataRegisterResponse(){ return mRegisterResponseLiveData; }
    public MutableLiveData<Response> getMutableLiveDataResetPass(){ return mResetPassResponseLiveData; }
    public MutableLiveData<UserProfile> getMutableLiveDataUserProfile(){ return mUserProfileLiceData; }

    public void signIn(String email, String pass) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            mCurrentUser = mAuth.getCurrentUser();
                            mLoginResponseLiveData.setValue(new Response("Logged in successfully.",true));
                        } else {
                            // Sign in failed
                            mLoginResponseLiveData.setValue(new Response("Logging failed.",false));
                        }
                    }
                });
    }

    //Registers the a new user according to the profile and password
    public void signUp(UserProfile userProfile, String pass) {
        mAuth.createUserWithEmailAndPassword(userProfile.getmEmail(), pass)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign up success
                    mCurrentUser = mAuth.getCurrentUser();

                    mRegisterResponseLiveData.setValue(
                            new Response("Signed up successfully.",true));

                    String uId = mCurrentUser.getUid();
                    // Populate the user's profile
                    mDatabase.collection("Users").document(uId).set(userProfile);

                    //set the display name for the user in
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userProfile.getmAlias()).build();
                    mCurrentUser.updateProfile(profileUpdates);
                } else {
                    // Sign up failed
                    mRegisterResponseLiveData.setValue(
                            new Response("Signed up failed.",false));
                }
            }
        });
    }

    //Resets the password on the desired email address
    public void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // mail was sent successfully.
                            mResetPassResponseLiveData.setValue(new Response("Email was sent successfully.",true));
                        } else {
                            mResetPassResponseLiveData.setValue(new Response(task.getException().getMessage(),true));
                        }
                    }
                });
    }

    //ToDO: getUserProfile
    //Gets the Users Profile
    public boolean getUserProfile(){

        //if not signed in no use of continuing
        if (!isUserSignedIn()) return false;

        DocumentReference docRef = mDatabase.collection("Users").document(mCurrentUser.getUid());
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

    //Signs out from the current user
    public void signOut(){
        if (null != mAuth) {
            mAuth.signOut();
        }
    }

    public String getUserEmail(){
        String userEmail = "anonymous@user.com";

        if (null != mAuth.getCurrentUser()){
            userEmail = mAuth.getCurrentUser().getEmail();
        }

        return userEmail;
    }

    public String getUserAlias(){
        String userAlias = "anonymous";

        if (null != mAuth.getCurrentUser()){
            userAlias = mAuth.getCurrentUser().getDisplayName();
        }

        return userAlias;
    }

    public boolean isUserSignedIn(){
        if (null != mAuth.getCurrentUser()) return true;
        else return false;
    }

    public String getCurrentUserId() { return mAuth.getCurrentUser().getUid(); }
}
