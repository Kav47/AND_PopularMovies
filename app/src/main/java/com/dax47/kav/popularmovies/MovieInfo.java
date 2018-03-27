package com.dax47.kav.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dax47.kav.popularmovies.handler.Movie;
import com.squareup.picasso.Picasso;

public class MovieInfo extends AppCompatActivity {

    private ImageView ivPoster;
    private TextView tvTitle, tvDate, tvOverview, tvRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        //initialisation
        ivPoster = (ImageView) findViewById(R.id.mImageView);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvRating = (TextView) findViewById(R.id.tvRating);

        Movie movie = getIntent().getParcelableExtra("MOVIE");

        if(movie != null){
            Picasso.with(this).load("https://image.tmdb.org/t/p/w185/" + movie.getmThumbnail())
                    .into(ivPoster);
            tvTitle.setText(movie.getmTitle());
            tvDate.setText(movie.getmYear());
            tvOverview.setText(movie.getmOverviewUri());
            tvRating.setText(Double.toString(movie.getmUserRating()));
        }
    }
}