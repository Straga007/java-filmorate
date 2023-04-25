package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@RestController

public class FilmController {
    private final List<Film> posts = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/posts")
    public List<Film> findAll() {
        log.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }

    @PostMapping(value = "/post")
    public Film create(@RequestBody Film film) {
        posts.add(film);
        log.debug("Создание нового поста: {}", film.toString());
        return film;
    }

}
