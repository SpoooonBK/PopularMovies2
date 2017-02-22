package net.estebanrodriguez.apps.popularmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDetailFactory;
import net.estebanrodriguez.apps.popularmovies.utility.DateParser;
import net.estebanrodriguez.apps.popularmovies.utility.ImageSizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Spoooon on 10/9/2016.
 */
public class MovieItem implements Parcelable {


    private String mTitle;
    private String mID;
    private String mPosterPath;
    private boolean mAdult;
    private String mOverview;
    private Date mReleaseDate;
    private List<String> mGenreIds;
    private boolean mFavorited = false;
    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mBackdropPath;
    private double mPopularity;
    private double mVoteCount;
    private boolean mVideo;
    private double mVoteAverage;
    private String mImageFetchURL;

    private List<MovieClip> mMovieClips = new ArrayList<>();
    private List<MovieReview> mMovieReviews = new ArrayList<>();

    /**
     * Instantiates a new Movie item.
     */
    public MovieItem() {
    }


    /**
     * Gets poster path.
     *
     * @return the poster path
     */
    public String getPosterPath() {
        return mPosterPath;
    }

    /**
     * Sets poster path.
     *
     * @param posterPath the poster path
     */
    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
        setImageFetchURL();
    }

    /**
     * Gets image fetch url.
     *
     * @return the image fetch url
     */
    public String getImageFetchURL() {
        return mImageFetchURL;
    }

    /**
     * Sets image fetch url.
     */
    public void setImageFetchURL() {
        mImageFetchURL = ConstantsVault.IMAGE_FETCH_BASE_URL + ImageSizer.sDefaultImageSize + mPosterPath;
    }

    /**
     * Is adult boolean.
     *
     * @return the boolean
     */
    public boolean isAdult() {
        return mAdult;
    }

    /**
     * Sets adult.
     *
     * @param adult the adult
     */
    public void setAdult(boolean adult) {
        mAdult = adult;
    }

    /**
     * Gets overview.
     *
     * @return the overview
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Sets overview.
     *
     * @param overview the overview
     */
    public void setOverview(String overview) {
        mOverview = overview;
    }

    /**
     * Gets release date.
     *
     * @return the release date
     */
    public Date getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Gets formatted release date.
     *
     * @return the formatted release date
     */
    public String getFormattedReleaseDate() {
        return DateParser.parseDate(mReleaseDate);
    }

    /**
     * Sets release date.
     *
     * @param releaseDate the release date
     */
    public void setReleaseDate(String releaseDate) {
        try{
            mReleaseDate = java.sql.Date.valueOf(releaseDate);
        }catch (IllegalArgumentException e){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            try {
                mReleaseDate = simpleDateFormat.parse(releaseDate);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Gets genre ids.
     *
     * @return the genre ids
     */
    public List<String> getGenreIds() {
        return mGenreIds;
    }

    /**
     * Sets genre ids.
     *
     * @param genreIds the genre ids
     */
    public void setGenreIds(List<String> genreIds) {
        mGenreIds = genreIds;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getID() {
        return mID;
    }

    /**
     * Sets id.
     *
     * @param ID the id
     */
    public void setID(String ID) {
        mID = ID;
    }

    /**
     * Gets original title.
     *
     * @return the original title
     */
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * Sets original title.
     *
     * @param originalTitle the original title
     */
    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    /**
     * Gets original language.
     *
     * @return the original language
     */
    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    /**
     * Sets original language.
     *
     * @param originalLanguage the original language
     */
    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Gets backdrop path.
     *
     * @return the backdrop path
     */
    public String getBackdropPath() {
        return mBackdropPath;
    }

    /**
     * Sets backdrop path.
     *
     * @param backdropPath the backdrop path
     */
    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    /**
     * Gets popularity.
     *
     * @return the popularity
     */
    public double getPopularity() {
        return mPopularity;
    }

    /**
     * Sets popularity.
     *
     * @param popularity the popularity
     */
    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    /**
     * Gets vote count.
     *
     * @return the vote count
     */
    public double getVoteCount() {
        return mVoteCount;
    }

    /**
     * Sets vote count.
     *
     * @param voteCount the vote count
     */
    public void setVoteCount(double voteCount) {
        mVoteCount = voteCount;
    }

    /**
     * Is video boolean.
     *
     * @return the boolean
     */
    public boolean isVideo() {
        return mVideo;
    }

    /**
     * Sets video.
     *
     * @param video the video
     */
    public void setVideo(boolean video) {
        mVideo = video;
    }

    /**
     * Gets vote average.
     *
     * @return the vote average
     */
    public double getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Sets vote average.
     *
     * @param voteAverage the vote average
     */
    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    /**
     * Gets movie clips.
     *
     * @return the movie clips
     */
    public List<MovieClip> getMovieClips() {
        return mMovieClips;
    }


    /**
     * Sets movie clips.
     *
     * @param movieClips the movie clips
     */
    public void setMovieClips(List<MovieClip> movieClips) {
        mMovieClips = movieClips;
    }

    /**
     * Gets movie reviews.
     *
     * @return the movie reviews
     */
    public List<MovieReview> getMovieReviews() {
        return mMovieReviews;
    }

    /**
     * Sets movie reviews.
     *
     * @param movieReviews the movie reviews
     */
    public void setMovieReviews(List<MovieReview> movieReviews) {
        mMovieReviews = movieReviews;
    }

    /**
     * Set movie details.
     *
     * @param map the map
     */
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

    /**
     * Get movie details list.
     *
     * @return the list
     */
    public List<Object> getMovieDetails(){
        List<Object> movieDetails = new ArrayList<>();
        movieDetails.add(this);
        movieDetails.addAll(mMovieClips);
        movieDetails.addAll(mMovieReviews);
        return movieDetails;
    }

    /**
     * Is favorited boolean.
     *
     * @return the boolean
     */
    public boolean isFavorited() {
        return mFavorited;
    }

    /**
     * Sets favorited.
     *
     * @param favorited the favorited
     */
    public void setFavorited(boolean favorited) {
        mFavorited = favorited;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mID);
        dest.writeString(this.mPosterPath);
        dest.writeByte(this.mAdult ? (byte) 1 : (byte) 0);
        dest.writeString(this.mOverview);
        dest.writeLong(this.mReleaseDate != null ? this.mReleaseDate.getTime() : -1);
        dest.writeStringList(this.mGenreIds);
        dest.writeByte(this.mFavorited ? (byte) 1 : (byte) 0);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mOriginalLanguage);
        dest.writeString(this.mBackdropPath);
        dest.writeDouble(this.mPopularity);
        dest.writeDouble(this.mVoteCount);
        dest.writeByte(this.mVideo ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.mVoteAverage);
        dest.writeString(this.mImageFetchURL);
        dest.writeTypedList(this.mMovieClips);
        dest.writeList(this.mMovieReviews);
    }

    /**
     * Instantiates a new Movie item.
     *
     * @param in the in
     */
    protected MovieItem(Parcel in) {
        this.mTitle = in.readString();
        this.mID = in.readString();
        this.mPosterPath = in.readString();
        this.mAdult = in.readByte() != 0;
        this.mOverview = in.readString();
        long tmpMReleaseDate = in.readLong();
        this.mReleaseDate = tmpMReleaseDate == -1 ? null : new Date(tmpMReleaseDate);
        this.mGenreIds = in.createStringArrayList();
        this.mFavorited = in.readByte() != 0;
        this.mOriginalTitle = in.readString();
        this.mOriginalLanguage = in.readString();
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

    /**
     * The constant CREATOR.
     */
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

    @Override
    public String toString() {
        return "MovieItem{" +
                "mTitle='" + mTitle + '\'' +
                ", mID='" + mID + '\'' +
                ", mPosterPath='" + mPosterPath + '\'' +
                ", mAdult=" + mAdult +
                ", mOverview='" + mOverview + '\'' +
                ", mReleaseDate=" + mReleaseDate +
                ", mGenreIds=" + mGenreIds +
                ", mFavorited=" + mFavorited +
                ", mOriginalTitle='" + mOriginalTitle + '\'' +
                ", mOriginalLanguage='" + mOriginalLanguage + '\'' +
                ", mBackdropPath='" + mBackdropPath + '\'' +
                ", mPopularity=" + mPopularity +
                ", mVoteCount=" + mVoteCount +
                ", mVideo=" + mVideo +
                ", mVoteAverage=" + mVoteAverage +
                ", mImageFetchURL='" + mImageFetchURL + '\'' +
                ", mMovieClips=" + mMovieClips +
                ", mMovieReviews=" + mMovieReviews +
                '}';
    }
}