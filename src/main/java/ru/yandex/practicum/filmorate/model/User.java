package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Data
@NonNull
@ToString
@Log
public class User {

    int id;

    String email;

    String login;

    String name;

    LocalDate birthday;

    public User(@NonNull String email, @NonNull String login, String name, @NonNull LocalDate birthday, int id) {

        if (email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }
        if (login.trim().isEmpty() || login.contains(" ")) {
            throw new IllegalArgumentException("Login must not contain spaces");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        this.login = login;
        this.name = (name == null || name.trim().isEmpty()) ? login : name;
        this.id = id;
        this.email = email;
        this.birthday = birthday;
    }
}
