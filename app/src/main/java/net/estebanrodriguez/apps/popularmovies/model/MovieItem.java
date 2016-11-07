package net.estebanrodriguez.apps.popularmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

import net.estebanrodriguez.apps.popularmovies.data_access.MovieDetailFactory;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieItemFactory;
import net.estebanrodriguez.apps.popularmovies.utility.ImageSizer;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Spoooon on 10/9/2016.
 */
public class MovieItem implements Parcelable {



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

    private List<MovieClip> mMovieClips = new ArrayList<>();
    private List<MovieReview> mMovieReviews = new ArrayList<>();

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

    public List<MovieClip> getMovieClips() {
        return mMovieClips;
    }


    public void setMovieClips(List<MovieClip> movieClips) {
        mMovieClips = movieClips;
    }

    public List<MovieReview> getMovieReviews() {
        return mMovieReviews;
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        mMovieReviews = movieReviews;
    }

    public void setMovieDetails(Map<Integer, List<MovieDetail>> map){

        List<MovieDetail> movieClips = map.get(MovieDetailFactory.MOVIE_CLIP);
        for(MovieDetail movieDetail: movieClips){
            mMovieClips.add((MovieClip) movieDetail);
        }

        List<MovieDetail> movieReviews = map.get(MovieDetailFactory.MOVIE_REVIEW);
        for(MovieDetail movieDetail: movieReviews){
            mMovieReviews.add((MovieReview) movieDetail);
        }

    }

    public List<Object> getMovieDetails(){
        List<Object> movieDetails = new ArrayList<>();
        movieDetails.add(this);
        movieDetails.addAll(mMovieClips);
        movieDetails.addAll(mMovieReviews);
        return movieDetails;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPosterPath);
        dest.writeByte(this.mAdult ? (byte) 1 : (byte) 0);
        dest.writeString(this.mOverview);
        dest.writeLong(this.mReleaseDate != null ? this.mReleaseDate.getTime() : -1);
        dest.writeStringList(this.mGenreIds);
        dest.writeString(this.mID);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mOriginalLanguage);
        dest.writeString(this.mTitle);
        dest.writeString(this.mBackdropPath);
        dest.writeDouble(this.mPopularity);
        dest.writeDouble(this.mVoteCount);
        dest.writeByte(this.mVideo ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.mVoteAverage);
        dest.writeString(this.mImageFetchURL);
        dest.writeTypedList(this.mMovieClips);
        dest.writeList(this.mMovieReviews);
    }

    protected MovieItem(Parcel in) {
        this.mPosterPath = in.readString();
        this.mAdult = in.readByte() != 0;
        this.mOverview = in.readString();
        long tmpMReleaseDate = in.readLong();
        this.mReleaseDate = tmpMReleaseDate == -1 ? null : new Date(tmpMReleaseDate);
        this.mGenreIds = in.createStringArrayList();
        this.mID = in.readString();
        this.mOriginalTitle = in.readString();
        this.mOriginalLanguage = in.readString();
        this.mTitle = in.readString();
        this.mBackdropPath = in.readString();
        this.mPopularity = in.readDouble();
        this.mVoteCount = in.readDouble();
        this.mVideo = in.readByte() != 0;
        this.mVoteAverage = in.readDouble();
        this.mImageFetchURL = in.readString();
        this.mMovieClips = in.createTypedArrayList(MovieClip.CREATOR);
        this.mMovieReviews = new ArrayList<MovieReview>();
        in.readList(this.mMovieReviews, MovieReview.class.getClassLoader());
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}