package net.estebanrodriguez.apps.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Spoooon on 11/2/2016.
 */
public class MovieClip extends MovieDetail implements Parcelable {

    private String mClipID;
    private String mLanguageCodeISO639;
    private String mLanguagecodeiso3166;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize;
    private String mClipType;
    private String mClipURI;

    /**
     * Instantiates a new Movie clip.
     */
    public MovieClip() {
    }

    /**
     * Gets clip id.
     *
     * @return the clip id
     */
    public String getClipID() {
        return mClipID;
    }

    /**
     * Sets clip id.
     *
     * @param clipID the clip id
     */
    public void setClipID(String clipID) {
        this.mClipID = clipID;
    }

    /**
     * Gets language code iso 639.
     *
     * @return the language code iso 639
     */
    public String getLanguageCodeISO639() {
        return mLanguageCodeISO639;
    }

    /**
     * Sets language code iso 639.
     *
     * @param languageCodeISO639 the language code iso 639
     */
    public void setLanguageCodeISO639(String languageCodeISO639) {
        this.mLanguageCodeISO639 = languageCodeISO639;
    }

    /**
     * Gets languagecodeiso 3166.
     *
     * @return the languagecodeiso 3166
     */
    public String getLanguagecodeiso3166() {
        return mLanguagecodeiso3166;
    }

    /**
     * Sets languagecodeiso 3166.
     *
     * @param languagecodeiso3166 the languagecodeiso 3166
     */
    public void setLanguagecodeiso3166(String languagecodeiso3166) {
        this.mLanguagecodeiso3166 = languagecodeiso3166;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return mKey;
    }

    /**
     * Sets key.
     *
     * @param key the key
     */
    public void setKey(String key) {
        this.mKey = key;
        setClipURI(key);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Gets site.
     *
     * @return the site
     */
    public String getSite() {
        return mSite;
    }

    /**
     * Sets site.
     *
     * @param site the site
     */
    public void setSite(String site) {
        this.mSite = site;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return mSize;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(String size) {

        try {
            mSize = Integer.valueOf(size);
        } catch (Exception e) {
            mSize = 0;
        }

    }

    /**
     * Gets clip type.
     *
     * @return the clip type
     */
    public String getClipType() {
        return mClipType;
    }

    /**
     * Sets clip type.
     *
     * @param clipType the clip type
     */
    public void setClipType(String clipType) {
        this.mClipType = clipType;
    }

    /**
     * Gets clip uri.
     *
     * @return the clip uri
     */
    public String getClipURI() {
        return mClipURI;
    }

    /**
     * Sets clip uri.
     *
     * @param key the key
     */
    public void setClipURI(String key) {
        mClipURI = "https://www.youtube.com/watch?v=" + key;

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
        dest.writeString(this.mClipURI);
    }

    /**
     * Instantiates a new Movie clip.
     *
     * @param in the in
     */
    protected MovieClip(Parcel in) {
        this.mClipID = in.readString();
        this.mLanguageCodeISO639 = in.readString();
        this.mLanguagecodeiso3166 = in.readString();
        this.mKey = in.readString();
        this.mName = in.readString();
        this.mSite = in.readString();
        this.mSize = in.readInt();
        this.mClipType = in.readString();
        this.mClipURI = in.readString();
    }

    /**
     * The constant CREATOR.
     */
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

    @Override
    public String toString() {
        return "MovieClip{" +
                "mClipID='" + mClipID + '\'' +
                ", mLanguageCodeISO639='" + mLanguageCodeISO639 + '\'' +
                ", mLanguagecodeiso3166='" + mLanguagecodeiso3166 + '\'' +
                ", mKey='" + mKey + '\'' +
                ", mName='" + mName + '\'' +
                ", mSite='" + mSite + '\'' +
                ", mSize=" + mSize +
                ", mClipType='" + mClipType + '\'' +
                ", mClipURI='" + mClipURI + '\'' +
                '}';
    }
}
