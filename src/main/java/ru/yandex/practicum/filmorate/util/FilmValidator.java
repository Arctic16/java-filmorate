package ru.yandex.practicum.filmorate.util;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;

public class FilmValidator {
    public static boolean validate(Film film) throws ValidationException {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым!");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма не может иметь длину более 200 символов!");
        } else if (!film.getReleaseDate()
                .isAfter(LocalDate.of(1895, 12, 27))) {
            throw new ValidationException("Фильм не мог выйти раньше 28 декабря 1895 года!");
        } else if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной!");
        } else {
            return true;
        }
    }
}
