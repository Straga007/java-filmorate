package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Review {
    int reviewId;
    String content;
    boolean isPositive;
    int userId;
    int filmId;
    int useful;
}
