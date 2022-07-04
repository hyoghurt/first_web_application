package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;

public interface UserRepository {
    int saveUser(User user);
    User findUserByPhone(String phone);
}
