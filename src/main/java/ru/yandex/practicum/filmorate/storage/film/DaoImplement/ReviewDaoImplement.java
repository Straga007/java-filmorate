package ru.yandex.practicum.filmorate.storage.film.DaoImplement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.film.dao.ReviewDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class ReviewDaoImplement implements ReviewDao {
    private final JdbcTemplate jdbcTemplate;

    public ReviewDaoImplement(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Review saveReview(Review review) {
        String sql = "INSERT INTO reviews (content, is_positive, user_id, film_id, useful, rating) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"review_id"});
            ps.setString(1, review.getContent());
            ps.setBoolean(2, review.isPositive());
            ps.setInt(3, review.getUserId());
            ps.setInt(4, review.getFilmId());
            ps.setInt(5, review.getUseful());
            ps.setInt(6, review.getRating());
            return ps;
        }, keyHolder);

        long reviewId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        review.setReviewId(reviewId);

        return review;
    }


    @Override
    public Review updateReview(Review review) {
        return null;
    }

    @Override
    public void deleteReview(int id) {

    }

    @Override
    public Review getReviewById(int id) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRow, id);
    }

    @Override
    public List<Review> getReviewsByFilmId(Long filmId, Integer count) {
        return null;
    }

    public Review mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("review_id")); //ID отзыва, генерируем
        review.setContent(resultSet.getString("content")); // Получаем в теле
        review.setPositive(resultSet.getBoolean("is_positive")); // если rating > 0 -> 1 or 0
        review.setUserId(resultSet.getInt("user_id"));// получаем от того кто оставил комент
        review.setFilmId(resultSet.getInt("film_id"));// получаем от того куда поставил комент
        review.setUseful(resultSet.getInt("useful"));// число лайков
        review.setRating(resultSet.getInt("rating"));// если лайк +1 если дизлайк -1, так 0
        return review;
    }
}
