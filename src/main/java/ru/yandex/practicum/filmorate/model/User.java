package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;

    public User(String email, @NonNull String login, String name, @NonNull LocalDate birthday, int id) {

        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name == null || name.trim().isEmpty() ? login : name;
        this.birthday = birthday;
    }

}