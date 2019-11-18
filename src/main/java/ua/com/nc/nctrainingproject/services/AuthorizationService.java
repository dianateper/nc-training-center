package ua.com.nc.nctrainingproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.nc.nctrainingproject.models.Admin;
import ua.com.nc.nctrainingproject.models.User;
import ua.com.nc.nctrainingproject.persistance.dao.postgre.UserPostgreDAO;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationService {
    private final UserPostgreDAO userPostgreDAO;

    @Autowired
    public AuthorizationService(UserPostgreDAO userPostgreDAO) {
        this.userPostgreDAO = userPostgreDAO;
    }

    public User auth(String login, String password) {
        User user = userPostgreDAO.getUserByUserName(login);

        if (user != null) {
            if (user.getUserPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User register(String login, String password, String email) {
        if (userPostgreDAO.getUserByUserName(login) == null && userPostgreDAO.getUserByEmail(email) == null) {
            User user = new User(login, password, email);
            userPostgreDAO.createUser(user);

            return userPostgreDAO.getUserByUserName(login);
        }
        return null;
    }

}
