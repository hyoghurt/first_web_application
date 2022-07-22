package edu.school21.cinema.services;

import edu.school21.cinema.models.*;

import javax.servlet.http.Part;
import java.util.List;
import java.util.UUID;

public interface UserService {
    int signUp(User user);
    User signIn(String phone, String password, SignIn signIn);

    boolean auth(User user);
    boolean authImages(String phone, UUID uuid);

    List<SignInDTO> getListSignInDTO(String phone);
    List<ImageDTO> getListImages(String phone);
    byte[] getImageById(String id);
    void saveFile(Part part, String phone);
}
