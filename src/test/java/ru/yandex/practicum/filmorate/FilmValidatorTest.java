package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.FilmValidator;
import java.time.LocalDate;

@SpringBootTest
public class FilmValidatorTest {
    @Test
    void addCorrectFilmTest() throws ValidationException {
        Film film = new Film(1, "Зеленая книга", "assdasdasd",
                LocalDate.of(2000,10,10),
                130);
        Assertions.assertTrue(FilmValidator.validate(film));
    }

    @Test
    void addIncorrectDateFilmTest() {
        Film film = new Film(1, "Зеленая книга", "assdasdasd",
                LocalDate.of(1895,12,27),
                130);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> FilmValidator.validate(film));
        Assertions.assertEquals("Фильм не мог выйти раньше 28 декабря 1895 года!", exception.getMessage());
    }

    @Test
    void addIncorrectDurationFilmTest() {
        Film film = new Film(1, "Зеленая книга", "assdasdasd",
                LocalDate.of(2000,10,10),
                -10);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> FilmValidator.validate(film));
        Assertions.assertEquals("Продолжительность фильма должна быть положительной!", exception.getMessage());

    }

    @Test
    void addIncorrectNameFilmTest() {
        Film film = new Film(1, "", "assdasdasd",
                LocalDate.of(2000,10,10),
                130);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
        () -> FilmValidator.validate(film));
        Assertions.assertEquals("Название фильма не может быть пустым!", exception.getMessage());
    }

    @Test
    void addIncorrectDescriptionFilmTest() {
        Film film = new Film(1, "DDD", "D".repeat(201),
                LocalDate.of(2000,10,10), 130);
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> FilmValidator.validate(film));
        Assertions.assertEquals("Описание фильма не может иметь длину более 200 символов!", exception.getMessage());
    }

}
