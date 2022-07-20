package edu.school21.cinema.services;

import edu.school21.cinema.models.*;

import javax.servlet.http.Part;
import java.util.List;
import java.util.UUID;

public interface UserService {

    int signUp(User user);

    User signIn(User user);
    int saveSignIn(SignIn signin);
    Boolean auth(User user);

    List<SignInDTO> getListSignInDTO(String phone);
    List<ImageDTO> getListImages(String phone);
    byte[] getImageById(String id);
    void saveFile(Part part, String phone);
    void saveFileLocal(Part part, UUID uuid);
    void saveImage(Image image);
}
