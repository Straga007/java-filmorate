package ru.yandex.practicum.filmorate.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;

@Slf4j
@Data
@NonNull
@AllArgsConstructor
@ToString
@Builder
@Log
public class User {
    int id;
    String email;
    String login;
    String name;
}
