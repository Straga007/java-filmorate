-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
    user_id SERIAL PRIMARY KEY,
    user_name CHARACTER VARYING(100),
    user_email CHARACTER VARYING(100) NOT NULL UNIQUE,
    user_login CHARACTER VARYING(100) NOT NULL UNIQUE,
    user_birthday DATE
);

-- Создание таблицы рейтингов MPA
CREATE TABLE IF NOT EXISTS mpa_ratings (
    rating_id SERIAL PRIMARY KEY,
    rating CHARACTER VARYING(10) NOT NULL,
    description CHARACTER VARYING(200)
);

-- Создание таблицы фильмов
CREATE TABLE IF NOT EXISTS films (
    film_id integer not null primary key auto_increment,
     name CHARACTER VARYING(100) NOT NULL,
    description CHARACTER VARYING(200),
    release_date DATE,
    duration INTEGER,
    mpa_id INTEGER,

    CONSTRAINT films_mpa_fk FOREIGN KEY (mpa_id) REFERENCES mpa_ratings(rating_id)
);

-- Создание таблицы жанров
CREATE TABLE IF NOT EXISTS genres (
    genre_id SERIAL PRIMARY KEY,
    genre_name CHARACTER VARYING(100) NOT NULL
);

-- Создание связующей таблицы фильмов и жанров
CREATE TABLE IF NOT EXISTS films_genres (
    film_id INT NOT NULL,
    genre_id INT NOT NULL,

    PRIMARY KEY(film_id, genre_id),
    CONSTRAINT films_id_fk FOREIGN KEY (film_id) REFERENCES films(film_id) ON DELETE CASCADE,
    CONSTRAINT genre_id_fk FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);

-- Создание связующей таблицы для лайков фильмов
CREATE TABLE IF NOT EXISTS films_likes (
    film_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY(film_id, user_id),
    CONSTRAINT fk_films_id FOREIGN KEY (film_id) REFERENCES films(film_id) ON DELETE CASCADE,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


-- Создание таблицы списка друзей
CREATE TABLE IF NOT EXISTS friend_list (
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    confirmed BOOLEAN,

    PRIMARY KEY(user_id, friend_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT friend_id_fk FOREIGN KEY (friend_id) REFERENCES users(user_id) ON DELETE RESTRICT ON UPDATE RESTRICT
);
CREATE TABLE IF NOT EXISTS reviews (
    review_id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    is_positive BOOLEAN NOT NULL,
    user_id INT NOT NULL,
    film_id INT NOT NULL,
    useful INT DEFAULT 0,
    rating INT DEFAULT 0,


    CONSTRAINT user_id_fk_reviews FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT film_id_fk_reviews FOREIGN KEY (film_id) REFERENCES films(film_id) ON DELETE CASCADE
);
