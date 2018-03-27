package com.dax47.kav.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dax47.kav.popularmovies.handler.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lView;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lView = (ListView) findViewById(R.id.mListView);
        lView.setOnItemClickListener(this);


//        if(savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.movie_container, new MovieFragment())
//                    .commit();
//        }
        onRequestAPI();


    }

    public void onRequestAPI(){
        Log.i("at onRequestAPI", this.toString() );
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
                Log.i("at onResponse", this.toString() );
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    ArrayList<Movie> movieLiset = new ArrayList<>();

                    //Loop through the JsonArray
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        //(String mTitle, String mYear, String mOverviewUri, String mThumbnail, Double mUserRating)
                        movie = new Movie(
                                jObj.getString("title"),
                                jObj.getString("release_date"),
                                jObj.getString("overview"),
                                jObj.getString("poster_path"),
                                jObj.getDouble("vote_average")
                        );
                        movieLiset.add(movie);
                    }
                    MovieAdapter mAdapter = new MovieAdapter(MainActivity.this, movieLiset);
                    lView.setAdapter(mAdapter);

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
        Volley.newRequestQueue(getApplicationContext()).add(mJsonObjectRequest);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i("at Onclick", "invoked at position"+ Integer.toString(position));
            Movie movie2 = new Movie(movie.getmTitle(), movie.getmYear(), movie.getmOverviewUri(), movie.getmThumbnail(), movie.getmUserRating());

            Intent intent = new Intent(this, MovieInfo.class);
            intent.putExtra("MOVIE", movie2);
            startActivity(intent);
    }
}
