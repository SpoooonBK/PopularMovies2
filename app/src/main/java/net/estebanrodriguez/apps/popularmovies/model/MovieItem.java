package net.estebanrodriguez.apps.popularmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

import net.estebanrodriguez.apps.popularmovies.ImageSizer;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Spoooon on 10/9/2016.
 */
public class MovieItem implements Parcelable {

    public static String PARCELABLE = "Movie Item Parcelable";

    private String mPosterPath;
    private boolean mAdult;
    private String mOverview;
    private Date mReleaseDate;
    private List<String> mGenreIds;
    private String mID;
    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mTitle;
    private String mBackdropPath;
    private double mPopularity;
    private double mVoteCount;
    private boolean mVideo;
    private double mVoteAverage;
    private String mImageFetchURL;


    public MovieItem() {
    }



    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
        setImageFetchURL();
    }

    public String getImageFetchURL() {
        return mImageFetchURL;
    }

    public void setImageFetchURL() {
        mImageFetchURL = ConstantsVault.IMAGE_FETCH_BASE_URL + ImageSizer.sDefaultImageSize + mPosterPath;
    }

    public boolean isAdult() {
        return mAdult;
    }

    public void setAdult(boolean adult) {
        mAdult = adult;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        mReleaseDate = releaseDate;
    }

    public List<String> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<String> genreIds) {
        mGenreIds = genreIds;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    public double getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(double voteCount) {
        mVoteCount = voteCount;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public void setVideo(boolean video) {
        mVideo = video;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }







    protected MovieItem(Parcel in) {
        mPosterPath = in.readString();
        mAdult = in.readByte() != 0x00;
        mOverview = in.readString();
        long tmpMReleaseDate = in.readLong();
        mReleaseDate = tmpMReleaseDate != -1 ? new Date(tmpMReleaseDate) : null;
        if (in.readByte() == 0x01) {
            mGenreIds = new ArrayList<String>();
            in.readList(mGenreIds, String.class.getClassLoader());
        } else {
            mGenreIds = null;
        }
        mID = in.readString();
        mOriginalTitle = in.readString();
        mOriginalLanguage = in.readString();
        mTitle = in.readString();
        mBackdropPath = in.readString();
        mPopularity = in.readDouble();
        mVoteCount = in.readDouble();
        mVideo = in.readByte() != 0x00;
        mVoteAverage = in.readDouble();
        mImageFetchURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPosterPath);
        dest.writeByte((byte) (mAdult ? 0x01 : 0x00));
        dest.writeString(mOverview);
        dest.writeLong(mReleaseDate != null ? mReleaseDate.getTime() : -1L);
        if (mGenreIds == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mGenreIds);
        }
        dest.writeString(mID);
        dest.writeString(mOriginalTitle);
        dest.writeString(mOriginalLanguage);
        dest.writeString(mTitle);
        dest.writeString(mBackdropPath);
        dest.writeDouble(mPopularity);
        dest.writeDouble(mVoteCount);
        dest.writeByte((byte) (mVideo ? 0x01 : 0x00));
        dest.writeDouble(mVoteAverage);
        dest.writeString(mImageFetchURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}