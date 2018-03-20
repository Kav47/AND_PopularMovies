package com.dax47.kav.popularmovies;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_container, new MovieFragment())
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }
}
