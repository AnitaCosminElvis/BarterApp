package com.example.barterapp.data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * The entity User profile.
 */
@IgnoreExtraProperties
public class UserProfile {
    private String        mFirstName;
    private String        mSurname;
    private String        mTelNo;
    private String        mAlias;
    private String        mEmail;

    /**
     * Instantiates a new User profile.
     */
    public UserProfile(){}

    /**
     * Instantiates a new User profile.
     *
     * @param mFirstName the m first name
     * @param mSurname   the m surname
     * @param mTelNo     the m tel no
     * @param mAlias     the m alias
     * @param mEmail     the m email
     */
    public UserProfile(String mFirstName, String mSurname, String mTelNo, String mAlias, String mEmail) {
        this.mFirstName = mFirstName;
        this.mSurname = mSurname;
        this.mTelNo = mTelNo;
        this.mAlias = mAlias;
        this.mEmail = mEmail;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getmFirstName() {
        return mFirstName;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getmSurname() {
        return mSurname;
    }

    /**
     * Gets tel no.
     *
     * @return the tel no
     */
    public String getmTelNo() {
        return mTelNo;
    }

    /**
     * Gets alias.
     *
     * @return the alias
     */
    public String getmAlias() {
        return mAlias;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getmEmail() {
        return mEmail;
    }

    /**
     * Sets first name.
     *
     * @param mFirstName the m first name
     */
    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    /**
     * Sets surname.
     *
     * @param mSurname the m surname
     */
    public void setmSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    /**
     * Sets tel no.
     *
     * @param mTelNo the m tel no
     */
    public void setmTelNo(String mTelNo) {
        this.mTelNo = mTelNo;
    }

    /**
     * Sets alias.
     *
     * @param mAlias the m alias
     */
    public void setmAlias(String mAlias) {
        this.mAlias = mAlias;
    }

    /**
     * Sets email.
     *
     * @param mEmail the m email
     */
    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
