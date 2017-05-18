package codecool_shop.controller;

import codecool_shop.dao.UserDao;
import codecool_shop.dao.UserInterface;
import codecool_shop.model.User;

import java.sql.SQLException;

/**
 * Created by pgurdek on 13.05.17.
 */
public class UserController extends BaseController {

    private UserInterface userDao = new UserDao();

    public User authenticate(String username, String password) throws SQLException {
        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }

        User user = userDao.getByName(username);

        if (user == null) {
            return null;
        }
        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
