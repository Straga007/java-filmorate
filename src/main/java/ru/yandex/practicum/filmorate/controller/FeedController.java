package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.service.FeedService;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/{id}/feed")
    public Collection<Event> getFeed(@PathVariable("id") int id) {
        log.info("Запрошена лента событий пользователя с id {}", id);
        return feedService.getFeed(id);
    }
}