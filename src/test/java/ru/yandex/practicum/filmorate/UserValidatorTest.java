package ru.yandex.practicum.filmorate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.util.UserValidator;

import java.time.LocalDate;

@SpringBootTest
public class UserValidatorTest {

    @Test
    void addCorrectUserTest() throws ValidationException {
        User user = new User(1,"adsas@asda","Arct","",
                LocalDate.of(2000,10,10));
        Assertions.assertEquals(true, UserValidator.validate(user));
    }
    
    @Test
    void addIncorrectEmailUserTest() {
        User user = new User(1,"adsasasda","Arct","",
                LocalDate.of(2000,10,10));
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> UserValidator.validate(user));
        Assertions.assertEquals("Почта не может быть пустой и не содержать символа @ !",
                exception.getMessage());
    }

    @Test
    void addIncorrectBirthdayUserTest() {
        User user = new User(1,"adsas@asda","Arct","",
                LocalDate.of(2100,10,10));
        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> UserValidator.validate(user));
        Assertions.assertEquals("Дата рождения не может быть в будущем!",
                exception.getMessage());

    }

    @Test
    void addIncorrectLoginUserTest() {
        User user1 = new User(1,"adsas@asda","Ar ct","",
                LocalDate.of(2000,10,10));
        User user2 = new User(1,"adsas@asda","","",
                LocalDate.of(2000,10,10));

        ValidationException exception = Assertions.assertThrows(ValidationException.class,
                () -> UserValidator.validate(user1));
        ValidationException exception1 = Assertions.assertThrows(ValidationException.class,
                () -> UserValidator.validate(user2));
        Assertions.assertEquals("Логин не может быть пустым или содержать пробелы!",
                exception.getMessage());
        Assertions.assertEquals("Логин не может быть пустым или содержать пробелы!",
                exception1.getMessage());


    }
}
