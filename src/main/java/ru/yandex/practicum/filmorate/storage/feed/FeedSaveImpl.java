package ru.yandex.practicum.filmorate.storage.feed;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Primary
public class FeedSaveImpl implements FeedSaveDao {
    private final JdbcTemplate jdbcTemplate;

    public FeedSaveImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveEvent(int userId, int eventType, int operationType, int entityId) {
        String sqlQuery = "INSERT INTO events (time_stamp, user_id, event_type, operation_type, entity_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, Instant.now(), userId, eventType, operationType, entityId);
    }
}
