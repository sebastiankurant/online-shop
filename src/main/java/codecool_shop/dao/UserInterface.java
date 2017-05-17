package codecool_shop.dao;

import codecool_shop.model.User;

import java.sql.SQLException;

public interface UserInterface {

    public User getByName(String username) throws SQLException;
    public User getById(Integer id) throws SQLException;
}
