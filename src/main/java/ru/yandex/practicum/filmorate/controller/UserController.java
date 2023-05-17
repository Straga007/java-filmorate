package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {


    public void validate(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthday = user.getBirthday();

        if (email.trim().isEmpty() || !email.contains("@")) {
            throw new ValidationException("Email must contain @ symbol");
        }
        if (login.trim().isEmpty() || login.contains(" ")) {
            throw new ValidationException("Login must not contain spaces");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new ValidationException("Date of birth cannot be in the future");
        }
    }


}
