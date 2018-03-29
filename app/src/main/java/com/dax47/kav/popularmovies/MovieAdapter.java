package com.dax47.kav.popularmovies;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dax47.kav.popularmovies.handler.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kav on 19/03/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie>{

    private static final String TAG = "MovieAdapter";
    Activity mActivity;
    List<Movie> mMovieList;

    public MovieAdapter(Activity activity, ArrayList<Movie> movies) {
        super(activity, 0, movies);
        this.mActivity = activity;
        this.mMovieList = movies;
    }

    private static class ViewHolder{
        ImageView mImageViewMovie;
        TextView mTextViewMovie;
    }


    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Movie getItem(int position) {
        return mMovieList.get(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Movie movie = mMovieList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.gridview_images, null);

            viewHolder = new ViewHolder();
            viewHolder.mImageViewMovie = (ImageView) convertView.findViewById(R.id.mImageView);
            viewHolder.mTextViewMovie = (TextView) convertView.findViewById(R.id.mTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //set text
        viewHolder.mTextViewMovie.setText(movie.getmTitle());

        Picasso.with(mActivity).load("https://image.tmdb.org/t/p/w185/" + movie.getmThumbnail())
                .into(viewHolder.mImageViewMovie, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i("MovieAdapter_Picasso", "Success!");
                    }

                    @Override
                    public void onError() {
                        Log.e("MovieAdapter_Picasso", "ERROR!");
                    }
                });
        return convertView;
    }

    public void onRequestAPI(){
        //API STUFF
        final String API = "&api_key=";
        final String API_KEY = BuildConfig.API_KEY;
        //API url
        final String API_URL = "http://api.themoviedb.org/3/discover/movie?";
        //sort order
        final String SORT_POPUL = "sort_by=popularity.desc";
        //Highest rating
        final String SORT_RATING = "sort_by=vote_average.desc";
        //Movie thumbnail
        final String MOVIE_IMAGE = "https://image.tmdb.org/t/p/w185/";
        //Final URL
        final String FINAL_URL = API_URL + SORT_POPUL + API + API_KEY;

        //REF https://www.youtube.com/watch?v=y2xtLqP8dSQ
        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    String title;
                    Double userRating;
                    String posterURL;
                    String overview;
                    String releaseDate;

                    //clear existing data
                    mMovieList.clear();

                    //Loop through the JsonArray
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject movie = jsonArray.getJSONObject(i);
                        title = movie.getString("title");
                        userRating = movie.getDouble("vote_average");
                        posterURL = movie.getString("poster_path");
                        overview = movie.getString("overview");
                        releaseDate = movie.getString("release_date");

                        //Add to list
                        // String mTitle, String mYear, String mOverviewUri, String mThumbnail, Double mUserRating
                        mMovieList.add(new Movie(title, releaseDate, overview, posterURL, userRating));
                    }
                    //todo recylerview

                }catch(JSONException e){
                    e.printStackTrace();
                    Log.e("JSON Exception", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("JSON Response", error.getMessage());
            }
        });
        Volley.newRequestQueue(getContext().getApplicationContext()).add(mJsonObjectRequest);

    }
}
