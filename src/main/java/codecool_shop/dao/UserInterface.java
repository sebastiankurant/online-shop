package codecool_shop.dao;

import codecool_shop.model.User;

import java.sql.SQLException;

public interface UserInterface {

    User getByName(String username) throws SQLException;

    User getById(Integer id) throws SQLException;
}
