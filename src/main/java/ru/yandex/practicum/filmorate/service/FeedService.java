package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.storage.feed.FeedStorage;

import java.util.Collection;

@Service
public class FeedService {
    private final FeedStorage feedStorage;

    public FeedService(FeedStorage feedStorage) {
        this.feedStorage = feedStorage;
    }

    public Collection<Event> getFeed(int id) {
        return feedStorage.getFeed(id);
    }
}
