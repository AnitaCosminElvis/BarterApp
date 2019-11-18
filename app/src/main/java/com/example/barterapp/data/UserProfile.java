package com.example.barterapp.data;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserProfile {
    private String        mFirstName;
    private String        mSurname;
    private String        mTelNo;
    private String        mAlias;
    private String        mEmail;

    public UserProfile(){}

    public UserProfile(String mFirstName, String mSurname, String mTelNo, String mAlias, String mEmail) {
        this.mFirstName = mFirstName;
        this.mSurname = mSurname;
        this.mTelNo = mTelNo;
        this.mAlias = mAlias;
        this.mEmail = mEmail;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public String getmSurname() {
        return mSurname;
    }

    public String getmTelNo() {
        return mTelNo;
    }

    public String getmAlias() {
        return mAlias;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setmSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public void setmTelNo(String mTelNo) {
        this.mTelNo = mTelNo;
    }

    public void setmAlias(String mAlias) {
        this.mAlias = mAlias;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
