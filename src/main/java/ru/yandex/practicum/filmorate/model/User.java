package ru.yandex.practicum.filmorate.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;

@Slf4j
@Data
@NonNull
@ToString
@Log
public class User {
    private static int nextId = 1;

    private  int id;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    String email;

    @NotEmpty(message = "Login cannot be empty")
    String login;

    String name;

    @Past(message = "Date of birth cannot be in the future")
    LocalDate dateOfBirth;

    public User(@NonNull String email, @NonNull String login, String name, @NonNull LocalDate dateOfBirth) {

        if ( email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }
        if ( login.trim().isEmpty() || login.contains(" ")) {
            throw new IllegalArgumentException("Login must not contain spaces");
        }
        if ( dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        this.login = login;
        this.name = (name == null || name.trim().isEmpty()) ? login : name;
        this.id = nextId++;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }
}
