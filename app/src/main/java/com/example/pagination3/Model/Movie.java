package com.example.pagination3.Model;

import com.google.gson.annotations.SerializedName;
/*
    POJO class to contain movie JSON details
 */
public class Movie {

    @SerializedName("id")
    private Integer id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private Double voteAverage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        return false;
    }

}
