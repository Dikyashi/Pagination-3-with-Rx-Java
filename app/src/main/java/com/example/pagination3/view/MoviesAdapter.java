package com.example.pagination3.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagination3.Model.Movie;
import com.example.pagination3.databinding.MovieItemBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.NonNull;

public class MoviesAdapter extends PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder> {
    // Define Loading ViewType
    public static final int LOADING_ITEM = 0;
    // Define Movie ViewType
    public static final int MOVIE_ITEM = 1;

    public MoviesAdapter(@NotNull DiffUtil.ItemCallback<Movie> diffCallback) {
        super(diffCallback);
    }


    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = getItem(position);

        if (currentMovie != null) {
            // Set Image of Movie using Picasso Library
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + currentMovie.getPosterPath())
                    .fit().into(holder.movieItemBinding.imageViewMovie);

            // Set rating of movie
            holder.movieItemBinding.textViewRating.setText(String.valueOf(currentMovie.getVoteAverage()));
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        // Define movie_item layout view binding
        MovieItemBinding movieItemBinding;

        public MovieViewHolder(@NonNull MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            // init binding
            this.movieItemBinding = movieItemBinding;
        }
    }
}
