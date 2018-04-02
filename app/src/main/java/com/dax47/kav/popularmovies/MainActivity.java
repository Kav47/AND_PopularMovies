package com.dax47.kav.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dax47.kav.popularmovies.handler.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //private ListView lView;
    private GridView gView;
    Movie movie;
    ArrayList<Movie> movieList;

    //API stuff
    //API STUFF
    final String API = "&api_key=";
    final String API_KEY = BuildConfig.API_KEY;
    //API url
    final String API_URL = "http://api.themoviedb.org/3/movie/";
    //sort order
    //https://api.themoviedb.org/3/movie/popular?&api_key=xxx
    final String SORT_POPUL = "popular?";
    //Highest rating
    final String SORT_RATING = "top_rated?";
    //Movie thumbnail
    final String MOVIE_IMAGE = "https://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialising toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //todo List view
//        lView = (ListView) findViewById(R.id.mListView);
//        lView.setOnItemClickListener(this);
        // gridview
        gView = (GridView) findViewById(R.id.mGridView);
        gView.setOnItemClickListener(this);

        //todo
        //check internet connection
        // if (isInternetAvailable()).....


        //todo
        //Preferencemanager
        //PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

//        if(savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.movie_container, new MovieFragment())
//                    .commit();
//        }

        sortByPopularity();


    }
    //todo
    public boolean isInternetAvailable(){
        try{
            InetAddress ipAdd = InetAddress.getByName("google.com");
            return !ipAdd.equals("");
        }catch (Exception e){
            return  false;
        }
    }



    public void sortByPopularity(){
        String FINAL_URL = API_URL + SORT_POPUL + API + API_KEY;
        onRequestAPI(FINAL_URL);
    }

    public void sortByRating(){
        String FINAL_URL = API_URL + SORT_RATING + API + API_KEY;
        onRequestAPI(FINAL_URL);
    }

    public void onRequestAPI(String s){
        Log.i("at onRequestAPI", this.toString() );

        //Final URL
        String FINAL_URL = s;

        //REF https://www.youtube.com/watch?v=y2xtLqP8dSQ
        final JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("at onResponse", this.toString() );
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                     movieList = new ArrayList<>();

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
                        movieList.add(movie);
                    }
                    MovieAdapter mAdapter = new MovieAdapter(MainActivity.this, movieList);
                    //lView.setAdapter(mAdapter);
                    gView.setAdapter(mAdapter);

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
            //Movie movie2 = new Movie(movie.getmTitle(), movie.getmYear(), movie.getmOverviewUri(), movie.getmThumbnail(), movie.getmUserRating());
            Intent intent = new Intent(this, MovieInfo.class);
            intent.putExtra("MOVIE", movieList.get(position));
            startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if((item.getItemId() == R.id.action_popularity)){
            //Toast.makeText(MainActivity.this, "About us", Toast.LENGTH_SHORT).show();
            sortByPopularity();
            return true;
        }
        if(item.getItemId() == R.id.action_rating){
////            Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
            //todo add settings and about
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
            sortByRating();
            return true;
        }
        if(item.getItemId() == R.id.action_swap_layout){
            //todo
            Toast.makeText(MainActivity.this, "Todo", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
