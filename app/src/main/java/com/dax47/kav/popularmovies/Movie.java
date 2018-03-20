package com.dax47.kav.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kav on 19/03/2018.
 */

public class Movie implements Parcelable{

    private static final String TAG = "Movie";

    //Movie attributea
    String mTitle;
    String mYear;
    String mTrailerUri;
    String mThumbnail;

    //list of movies
    private List<Movie> Movies;

    //constructor for movie
    Movie(String mTitle, String mYear, String mTrailerUri, String mThumbnail){
        this.mTitle = mTitle;
        this.mYear = mYear;
        this.mTrailerUri = mTrailerUri;
        this.mThumbnail = mThumbnail;
    }

    private Movie(Parcel in){
        mTitle = in.readString();
        mYear = in.readString();
        mTrailerUri = in.readString();
        mThumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mTitle);
        out.writeString(this.mYear);
        out.writeString(this.mTrailerUri);
        out.writeString(this.mThumbnail);
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

}
