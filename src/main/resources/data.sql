-- Вставка данных в таблицу жанров
MERGE INTO genres (GENRE_ID, GENRE_NAME)
values (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');

-- Вставка данных в таблицу рейтингов MPA
MERGE INTO MPA_RATINGS (RATING_ID, RATING, DESCRIPTION)
 VALUES (1, 'G', 'Нет возрастных ограничений'),
           (2, 'PG', 'Рекомендуется присутствие родителей'),
           (3, 'PG-13', 'Детям до 13 лет просмотр не желателен'),
           (4, 'R', 'Лицам до 17 лет обязательно присутствие взрослого'),
           (5, 'NC-17', 'Лицам до 18 лет просмотр запрещен');
-- Вставка данных в таблицу пользователей
INSERT INTO users (user_name, user_email, user_login, user_birthday)
VALUES
    ('Alice', 'alice@example.com', 'alice', '1990-05-15'),
    ('Bob', 'bob@example.com', 'bob', '1985-09-30'),
    ('Charlie', 'charlie@example.com', 'charlie', '1995-02-20');


-- Вставка данных в таблицу фильмов
INSERT INTO films (name, description, release_date, duration, mpa_id)
VALUES
    ('Film A', 'Описание фильма A', '2023-08-01', 120, 1),
    ('Film B', 'Описание фильма B', '2023-07-15', 105, 2),
    ('Film C', 'Описание фильма C', '2023-08-10', 140, 3);


-- Вставка данных в таблицу связей фильмов и жанров
INSERT INTO films_genres (film_id, genre_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 3);

-- Вставка данных в таблицу связей лайков фильмов
INSERT INTO films_likes (film_id, user_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 3);
