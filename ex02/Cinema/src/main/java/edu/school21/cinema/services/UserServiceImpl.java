package edu.school21.cinema.services;

import edu.school21.cinema.models.*;
import edu.school21.cinema.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
@Slf4j
@PropertySource("file:${webapp.root}/WEB-INF/application.properties")
public class UserServiceImpl implements UserService {

    @Value("${path.file.upload}")
    private String pathFileUpload;

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

    @Override
    public Boolean auth(User user) {
        User findUser = userRepository.findUserByPhone(user.getPhone());
        return findUser != null && findUser.getPassword().equals(user.getPassword());
    }

    @Override
    public int saveSignIn(SignIn signin) {
        return userRepository.saveSignIn(signin);
    }

    @Override
    public List<SignInDTO> getListSignInDTO(String phone) {
        List<SignInDTO> signInDTOList = new ArrayList<>();
        List<SignIn> signInList = userRepository.findAllSignInByPhoneUser(phone);

        DateFormat fmtDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        DateFormat fmtTime = new SimpleDateFormat("HH:mm:ss");

        if (signInList != null) {
            for (SignIn signIn : signInList) {
                SignInDTO signInDTO = new SignInDTO();
                signInDTO.setDate(fmtDate.format(signIn.getDate()));
                signInDTO.setTime(fmtTime.format(signIn.getDate()));
                signInDTO.setIp(signIn.getIp());
                signInDTOList.add(signInDTO);
            }
        }

        return signInDTOList;
    }

    @Override
    public List<ImageDTO> getListImages(String phone) {
        List<ImageDTO> imageDTOList = new ArrayList<>();
        List<Image> imageList = userRepository.findAllImagesByPhoneUser(phone);

        if (imageList != null) {
            for (Image image : imageList) {
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(image.getId());
                imageDTO.setName(image.getName());
                imageDTO.setSize(FileUtils.byteCountToDisplaySize(image.getSize()));
                imageDTO.setMime(image.getMime());
                imageDTOList.add(imageDTO);
            }
        }

        return imageDTOList;
    }

    @Override
    public byte[] getImageById(String id) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pathFileUpload);
        if (!pathFileUpload.endsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(id);

        try {
            return Files.readAllBytes(Paths.get(stringBuilder.toString()));
        } catch (IOException e) {
            log.error("not found file: " + stringBuilder);
            return null;
        }
    }

    @Override
    public void saveFile(Part part, String phone) {
        Image image = new Image();

        image.setId(UUID.randomUUID());
        image.setPhoneUser(phone);
        image.setName(part.getSubmittedFileName());
        image.setMime(part.getContentType());
        image.setSize(part.getSize());

        //TODO mutlithread

        saveFileLocal(part, image.getId());
        saveImage(image);
    }

    @Override
    public void saveFileLocal(Part part, UUID uuid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pathFileUpload);
        if (!pathFileUpload.endsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(uuid);

        try {
            Path path = Paths.get(stringBuilder.toString());
            if (!path.getParent().toFile().exists()) {
                Files.createDirectories(path.getParent());
            }
            part.write(stringBuilder.toString());
            log.info("create file: " + stringBuilder);
        } catch (IOException e) {
            log.error("file no saveUser local: " + stringBuilder);
            e.printStackTrace();
        }
    }

    @Override
    public void saveImage(Image image) {
        if (userRepository.saveImage(image) == 1) {
            log.info("saveUser DB: " + image.getId());
        } else {
            log.info("no saveUser DB: " + image.getId());
        }
    }
}
