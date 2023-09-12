package ru.yandex.practicum.filmorate.storage.feed;

public interface FeedSaveDao {
    void saveEvent(int userId, int eventType, int operationType, int entityId);
}
