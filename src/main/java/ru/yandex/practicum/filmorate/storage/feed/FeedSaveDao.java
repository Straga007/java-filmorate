package ru.yandex.practicum.filmorate.storage.feed;

import ru.yandex.practicum.filmorate.model.feed.EventType;
import ru.yandex.practicum.filmorate.model.feed.OperationType;

public interface FeedSaveDao {
    void saveEvent(int userId, int eventType, int operationType, int entityId);

    int getEventTypeId(EventType eventType);

    int getOperationTypeId(OperationType operationType);
}
