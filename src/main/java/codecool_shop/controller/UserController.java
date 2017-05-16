package codecool_shop.controller;

import codecool_shop.model.User;

/**
 * Created by pgurdek on 13.05.17.
 */
public class UserController {

    public Boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = new User(username, password); // For test, later db will be applied
        if (user == null) {
            return false;
        }
        if (user.getUsername().equals("admin") && user.getPassword().equals("admin")) {

            return true;
        }
        return false;
    }
}
