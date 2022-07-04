package edu.school21.cinema.services;

import edu.school21.cinema.models.*;

public interface UserService {

    int signUp(User user);

    User signIn(User user);
}
