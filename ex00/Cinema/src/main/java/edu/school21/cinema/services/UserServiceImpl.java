package edu.school21.cinema.services;

import edu.school21.cinema.models.*;
import edu.school21.cinema.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public int signUp(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userRepository.saveUser(user);
        } catch (DuplicateKeyException e) {
            log.info(e.getMessage());
            return 0;
        }
    }

    @Override
    public User signIn(User user) {
        User findUser = userRepository.findUserByPhone(user.getPhone());
        if (findUser != null && passwordEncoder.matches(user.getPassword(), findUser.getPassword())) {
            return findUser;
        }
        return null;
    }
}
