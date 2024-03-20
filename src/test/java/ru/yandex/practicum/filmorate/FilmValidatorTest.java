package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.FilmValidator;

import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest
public class FilmValidatorTest {
    @Test
    void addCorrectFilmTest() throws ValidationException {
        Film film = new Film(1, "Зеленая книга", "assdasdasd"
                , LocalDate.of(2000,10,10)
                , Duration.ofMinutes(130));
        Assertions.assertEquals(true, FilmValidator.validate(film));
    }

    @Test
    void addIncorrectDateFilmTest() {
        Film film = new Film(1, "Зеленая книга", "assdasdasd"
                , LocalDate.of(1985,12,27)
                , Duration.ofMinutes(130));
        ValidationException exception = Assertions.assertThrows(ValidationException.class
                , () -> FilmValidator.validate(film));
        Assertions.assertEquals("Фильм не мог выйти раньше 28 декабря 1985 года!", exception.getMessage());
    }

    @Test
    void addIncorrectDurationFilmTest() {
        Film film = new Film(1, "Зеленая книга", "assdasdasd"
                , LocalDate.of(2000,10,10)
                , Duration.ofMinutes(-130));
        ValidationException exception = Assertions.assertThrows(ValidationException.class
                , () -> FilmValidator.validate(film));
        Assertions.assertEquals("Продолжительность фильма должна быть положительной!", exception.getMessage());

    }
    @Test
    void addIncorrectNameFilmTest() {
        Film film = new Film(1, "", "assdasdasd"
                , LocalDate.of(2000,10,10)
                , Duration.ofMinutes(130));
        ValidationException exception = Assertions.assertThrows(ValidationException.class
                , () -> FilmValidator.validate(film));
        Assertions.assertEquals("Название фильма не может быть пустым!", exception.getMessage());
    }
    @Test
    void addIncorrectDescriptionFilmTest() {
        Film film = new Film(1, "DDD", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n"
                + "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.\n"
                + "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur?"
                , LocalDate.of(2000,10,10)
                , Duration.ofMinutes(130));
        ValidationException exception = Assertions.assertThrows(ValidationException.class
                , () -> FilmValidator.validate(film));
        Assertions.assertEquals("Описание фильма не может иметь длину более 200 символов!", exception.getMessage());
    }

}
