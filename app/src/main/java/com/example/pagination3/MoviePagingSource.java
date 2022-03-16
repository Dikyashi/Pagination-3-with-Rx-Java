package com.example.pagination3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.example.pagination3.APIClient;
import com.example.pagination3.Model.Movie;
import com.example.pagination3.Model.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource<Integer, Movie> {
    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
       try{
           int page = loadParams.getKey()!=null?loadParams.getKey() : 1;

           //send request to server with server name
           return APIClient.getApiInterface()
                   .getMoviesByPage(page)
                   .subscribeOn(Schedulers.io())

                   //Map result to Movies
                   .map(MovieResponse::getMovies)
                   .map(movies ->toLoadResult(movies,page))
                   .onErrorReturn(LoadResult.Error::new);
       }catch(Exception e){
           return Single.just(new LoadResult.Error(e));
       }
    }

    private LoadResult<Integer, Movie> toLoadResult(List<Movie> movies, int page) {
        return new LoadResult.Page(movies, page == 1 ? null : page - 1, page + 1);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Movie> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}
