package ru.yandex.practicum.filmorate.util;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {
    public static boolean validate(User user) throws ValidationException {
        if (user.getLogin().split(" ").length != 1 || user.getLogin().isBlank()) {
            throw new ValidationException("Логин не может быть пустым или содержать пробелы!");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем!");
        } else if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Почта не может быть пустой и не содержать символа @ !");
        } else {
            return true;
        }
    }
}
