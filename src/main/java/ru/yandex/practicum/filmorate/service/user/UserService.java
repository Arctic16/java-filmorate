package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriends(int user1Id, int user2Id) {
        if (userStorage.getUserById(user1Id) != null && userStorage.getUserById(user2Id).getFriends() != null) {
            userStorage.getUserById(user1Id).getFriends().add(user2Id);
            userStorage.getUserById(user2Id).getFriends().add(user1Id);
        }
    }

    public void removeFriends(int user1Id, int user2Id) {
        if(userStorage.getUserById(user1Id) != null && userStorage.getUserById(user2Id).getFriends() != null) {
            userStorage.getUserById(user1Id).getFriends().remove(user2Id);
            userStorage.getUserById(user2Id).getFriends().remove(user1Id);
        }
    }

    public Set<Integer> getFriends(int userId) {
        return userStorage.getUserById(userId).getFriends();
    }

    public Set<Integer> getCommonFriends(int user1Id, int user2Id) {
        Set<Integer> common = new HashSet<>(userStorage.getUserById(user1Id).getFriends());
        common.retainAll(userStorage.getUserById(user2Id).getFriends());
        return common;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
