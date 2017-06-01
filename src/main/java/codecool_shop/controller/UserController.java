package codecool_shop.controller;

import codecool_shop.dao.UserDao;
import codecool_shop.dao.UserInterface;
import codecool_shop.model.User;

import java.sql.SQLException;


class UserController extends BaseController {

    private UserInterface userDao;

    public UserController(UserInterface userDao){
        this.userDao = userDao;
    }

    User authenticate(String username, String password) throws SQLException {
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
