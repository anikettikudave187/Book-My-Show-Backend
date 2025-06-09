package com.bms.central_api_v1.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {
    UUID Id;
    String name;
    boolean isReleased;
    double duration;
    AppUser movieOwner;
    int review;
    int totalReviewVotes;
    String language;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public void setReleased(boolean released) {
        isReleased = released;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public AppUser getMovieOwner() {
        return movieOwner;
    }

    public void setMovieOwner(AppUser movieOwner) {
        this.movieOwner = movieOwner;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getTotalReviewVotes() {
        return totalReviewVotes;
    }

    public void setTotalReviewVotes(int totalReviewVotes) {
        this.totalReviewVotes = totalReviewVotes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
