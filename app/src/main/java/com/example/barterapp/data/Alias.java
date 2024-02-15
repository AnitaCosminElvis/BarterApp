package com.example.barterapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * The entity Alias.
 */
@IgnoreExtraProperties
public class Alias implements Parcelable {
    private String        mAlias;

    protected Alias(Parcel in) {
        mAlias = in.readString();
    }

    public static final Creator<Alias> CREATOR = new Creator<Alias>() {
        @Override
        public Alias createFromParcel(Parcel in) {
            return new Alias(in);
        }

        @Override
        public Alias[] newArray(int size) {
            return new Alias[size];
        }
    };

    public String getmAlias() {
        return mAlias;
    }

    public void setmAlias(String mAlias) {
        this.mAlias = mAlias;
    }

    /**
     * Instantiates a new Alias item.
     *
     * @param mAlias     the malias
     */
    public Alias(String mAlias) {
        this.mAlias = mAlias;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(mAlias);
    }
}
