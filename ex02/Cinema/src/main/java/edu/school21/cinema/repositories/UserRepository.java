package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.SignIn;
import edu.school21.cinema.models.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    int saveUser(User user);
    int saveSignIn(SignIn signIn);
    int saveImage(Image image);
    User findUserByPhone(String phone);
    List<SignIn> findAllSignInByPhoneUser(String phone);
    List<Image> findAllImagesByPhoneUser(String phone);
    boolean findImagesByPhoneByUuid(String phone, UUID uuid);
}
