package repository;

import model.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    User getUser(int userId);

    User getUser(User user);
}
