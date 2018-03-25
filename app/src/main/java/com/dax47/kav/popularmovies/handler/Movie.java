package com.dax47.kav.popularmovies.handler;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kav on 19/03/2018.
 */

public class Movie implements Parcelable{

    private static final String TAG = "Movie";

    //Movie attributea
    private String mTitle;
    private String mYear;
    private String mOverviewUri;
    private String mThumbnail;
    private Double mUserRating;

    //list of movies
    private List<Movie> Movies;

    //constructor for movie
    public Movie(String mTitle, String mYear, String mOverviewUri, String mThumbnail, Double mUserRating){
        this.mTitle = mTitle;
        this.mYear = mYear;
        this.mOverviewUri = mOverviewUri;
        this.mThumbnail = mThumbnail;
        this.mUserRating = mUserRating;
    }

    private Movie(Parcel in){
        mTitle = in.readString();
        mYear = in.readString();
        mOverviewUri = in.readString();
        mThumbnail = in.readString();
        mUserRating = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mTitle);
        out.writeString(this.mYear);
        out.writeString(this.mOverviewUri);
        out.writeString(this.mThumbnail);
        out.writeDouble(this.mUserRating);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public static String getTAG() {
        return TAG;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmYear() {
        return mYear;
    }

    public String getmOverviewUri() {
        return mOverviewUri;
    }

    public Double getmUserRating() {
        return mUserRating;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public List<Movie> getMovies() {
        return Movies;
    }
}
