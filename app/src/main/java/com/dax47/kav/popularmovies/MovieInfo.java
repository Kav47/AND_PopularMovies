package com.dax47.kav.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dax47.kav.popularmovies.handler.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInfo extends AppCompatActivity {

//    private ImageView ivPoster;
//    private TextView tvTitle, tvDate, tvOverview, tvRating;
    @BindView(R.id.ivPoster) ImageView ivPoster;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvRating) TextView tvRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        ButterKnife.bind(this);
        Log.i("At Movie Info", "Hello");

//        //initialisation
//        ivPoster = (ImageView) findViewById(R.id.ivPoster);
//        tvTitle = (TextView) findViewById(R.id.tvTitle);
//        tvDate = (TextView) findViewById(R.id.tvDate);
//        tvOverview = (TextView) findViewById(R.id.tvOverview);
//        tvRating = (TextView) findViewById(R.id.tvRating);


        Movie movie = null;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            movie = intent.getParcelableExtra("MOVIE");

        }

        if(movie != null){
            Log.i("URL:", movie.getmThumbnail());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w185" + movie.getmThumbnail())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            ivPoster.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            ivPoster.setImageResource(R.drawable.error);
                            Log.e("URL:","Failed to load URL");
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            Log.i("URL:","Loading..");
                        }
                    });
            tvTitle.setText(movie.getmTitle());
            tvDate.setText("Release Date: " +movie.getmYear());
            tvOverview.setText(movie.getmOverviewUri());
            tvRating.setText("User Rating: " + Double.toString(movie.getmUserRating()));
        }
    }

}
