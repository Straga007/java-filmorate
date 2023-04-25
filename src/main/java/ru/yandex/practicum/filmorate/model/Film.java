package ru.yandex.practicum.filmorate.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Film {
    //@NonNull
    int id;
    String name;
    String description;
    LocalDateTime releaseDate;
    long duration;

}
