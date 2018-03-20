package com.dax47.kav.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

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

        if(convertView == null){
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.gridview_images, null);

            viewHolder = new ViewHolder();
            viewHolder.mImageViewMovie = (ImageView) convertView.findViewById(R.id.imageViewMovie);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mActivity).load(mMovieList.get(position).mThumbnail).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewHolder.mImageViewMovie);
        return convertView;
    }
}
