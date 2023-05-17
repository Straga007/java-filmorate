package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
    public void addLike(int filmId, Integer userId){
        filmStorage.findFilm(filmId).setLikes(userId);
    }
    public int getAllLikes(int filmId){
        return filmStorage.findFilm(filmId).getLikes().size();
    }

}
