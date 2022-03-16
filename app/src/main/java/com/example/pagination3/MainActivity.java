package com.example.pagination3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.pagination3.Model.Movie;
import com.example.pagination3.ViewModel.MainActivityViewModel;
import com.example.pagination3.databinding.ActivityMainBinding;
import com.example.pagination3.view.GridSpace;
import com.example.pagination3.view.MovieComparator;
import com.example.pagination3.view.MoviesAdapter;
import com.example.pagination3.view.MoviesLoadStateAdapter;

import io.reactivex.rxjava3.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create View binding object
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // Create new MoviesAdapter object and provide
        MoviesAdapter moviesAdapter = new MoviesAdapter(new MovieComparator());

        // Create ViewModel
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Subscribe to to paging data
        mainActivityViewModel.pagingDataFlow.subscribe(new Consumer<PagingData<Movie>>() {
            @Override
            public void accept(PagingData<Movie> moviePagingData) throws Throwable {
                // submit new data to recyclerview adapter
                moviesAdapter.submitData(MainActivity.this.getLifecycle(), moviePagingData);
            }
        });

        // Create GridlayoutManger with span of count of 2
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        // Finally set LayoutManger to recyclerview
        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager);

        // Add ItemDecoration to add space between recyclerview items
        binding.recyclerViewMovies.addItemDecoration(new GridSpace(2, 20, true));

        // set adapter
        binding.recyclerViewMovies.setAdapter(
                // concat movies adapter with header and footer loading view
                // This will show end user a progress bar while pages are being requested from server
                moviesAdapter.withLoadStateFooter(
                        // Pass footer load state adapter.
                        // When we will scroll down and next page request will be sent
                        // while we get response form server Progress bar will show to end user
                        // If request success Progress bar will hide and next page of movies
                        // will be shown to end user or if request will fail error message and
                        // retry button will be shown to resend the request
                        new MoviesLoadStateAdapter(v -> {
                            moviesAdapter.retry();
                        })));


        // set Grid span
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // If progress will be shown then span size will be 1 otherwise it will be 2
                return moviesAdapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM ? 1 : 2;
            }
        });
    }
}