package com.example.pagination3.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/*
    POJO class for to contain movie response json details
 */

public class MovieResponse {

    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<Movie> movies = Collections.emptyList();
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public Integer getPage() {
        return page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
