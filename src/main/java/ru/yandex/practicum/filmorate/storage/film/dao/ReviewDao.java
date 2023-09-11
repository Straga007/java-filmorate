package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewDao {

    Review saveReview(Review review);

    Review updateReview(Review review);

    void deleteReview(int id);

    Review getReviewById(int id);

    List<Review> getReviewsByFilmId(Long filmId, Integer count);

}