package net.estebanrodriguez.apps.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Spoooon on 11/2/2016.
 */

public class MovieClip implements Parcelable {

    private String mClipID;
    private String mLanguageCodeISO639;
    private String mLanguagecodeiso3166;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize;
    private String mClipType;

    public MovieClip() {
    }

    public String getClipID() {
        return mClipID;
    }

    public void setClipID(String clipID) {
        this.mClipID = clipID;
    }

    public String getLanguageCodeISO639() {
        return mLanguageCodeISO639;
    }

    public void setLanguageCodeISO639(String languageCodeISO639) {
        this.mLanguageCodeISO639 = languageCodeISO639;
    }

    public String getLanguagecodeiso3166() {
        return mLanguagecodeiso3166;
    }

    public void setLanguagecodeiso3166(String languagecodeiso3166) {
        this.mLanguagecodeiso3166 = languagecodeiso3166;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String site) {
        this.mSite = site;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(String size) {

        try {
            mSize = Integer.valueOf(size);
        } catch (Exception e) {
            mSize = 0;
        }

    }

    public String getClipType() {
        return mClipType;
    }

    public void setClipType(String clipType) {
        this.mClipType = clipType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mClipID);
        dest.writeString(this.mLanguageCodeISO639);
        dest.writeString(this.mLanguagecodeiso3166);
        dest.writeString(this.mKey);
        dest.writeString(this.mName);
        dest.writeString(this.mSite);
        dest.writeInt(this.mSize);
        dest.writeString(this.mClipType);
    }

    protected MovieClip(Parcel in) {
        this.mClipID = in.readString();
        this.mLanguageCodeISO639 = in.readString();
        this.mLanguagecodeiso3166 = in.readString();
        this.mKey = in.readString();
        this.mName = in.readString();
        this.mSite = in.readString();
        this.mSize = in.readInt();
        this.mClipType = in.readString();
    }

    public static final Creator<MovieClip> CREATOR = new Creator<MovieClip>() {
        @Override
        public MovieClip createFromParcel(Parcel source) {
            return new MovieClip(source);
        }

        @Override
        public MovieClip[] newArray(int size) {
            return new MovieClip[size];
        }
    };
}
