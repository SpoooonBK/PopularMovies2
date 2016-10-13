package net.estebanrodriguez.apps.popularmovies.model;

import net.estebanrodriguez.apps.popularmovies.ImageSizer;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;

import java.util.Date;
import java.util.List;

/**
 * Created by Spoooon on 10/9/2016.
 */
public class MovieItem {

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

    @Override
    public String toString() {
        return mOriginalTitle;
    }
}
