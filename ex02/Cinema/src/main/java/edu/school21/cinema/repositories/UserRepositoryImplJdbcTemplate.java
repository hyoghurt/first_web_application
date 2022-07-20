package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.SignIn;
import edu.school21.cinema.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepositoryImplJdbcTemplate implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImplJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int saveUser(User entity) {
        String INSERT = "INSERT INTO users(first_name, last_name, phone, password) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(INSERT, entity.getFirstName(), entity.getLastName(),
                entity.getPhone(), entity.getPassword());
    }

    @Override
    public int saveSignIn(SignIn signin) {
        String INSERT_IP = "INSERT INTO signin_users (phone_user, date, ip) VALUES (?, ?, ?)";
        return jdbcTemplate.update(INSERT_IP, signin.getPhoneUser(), signin.getDate(), signin.getIp());
    }

    @Override
    public int saveImage(Image image) {
        String INSERT_IMAGE = "INSERT INTO images VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(INSERT_IMAGE, image.getId(), image.getPhoneUser(), image.getName(),
                image.getSize(), image.getMime());
    }

    @Override
    public User findUserByPhone(String phone) {
        try {
            String SELECT_BY_PHONE = "SELECT * FROM users WHERE phone = ?";
            return jdbcTemplate.queryForObject(SELECT_BY_PHONE,
                    (rs, rowNum) -> new User(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("phone"),
                            rs.getString("password")),
                    phone);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SignIn> findAllSignInByPhoneUser(String phone) {
        try {
            String SELECT_SIGNIN_BY_PHONE = "SELECT * FROM signin_users WHERE phone_user = ?";
            return jdbcTemplate.query(SELECT_SIGNIN_BY_PHONE,
                    (rs, rowNum) -> new SignIn(
                            rs.getLong("id"),
                            rs.getString("phone_user"),
                            rs.getTimestamp("date"),
                            rs.getString("ip")),
                    phone);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Image> findAllImagesByPhoneUser(String phone) {
        try {
            String SELECT_IMAGES_BY_PHONE = "SELECT * FROM images WHERE phone_user = ?";
            return jdbcTemplate.query(SELECT_IMAGES_BY_PHONE,
                    (rs, rowNum) -> new Image(
                            rs.getObject("id", java.util.UUID.class),
                            rs.getString("phone_user"),
                            rs.getString("name"),
                            rs.getLong("size"),
                            rs.getString("mime")),
                    phone);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
