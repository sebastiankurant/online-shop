package codecool_shop.dao;

import codecool_shop.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pgurdek on 16.05.17.
 */
public class UserDao extends Dao implements UserInterface{
    private final String GET_BY_NAME = "SELECT id FROM users WHERE username=?";
    private final String GET_BY_ID = "SELECT id,username,firstname,lastname,password,type FROM users WHERE id=?";

    @Override
    public User getByName(String username) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1,username);
        ResultSet rs = executeStatement(GET_BY_NAME,parameters);
        if (rs.isBeforeFirst())
            if (!(rs == null)) {
                Integer userId = rs.getInt("id");
                User user = getById(userId);
                user.setId(userId);
                rs.close();
                return user;
            }
        rs.close();
        return null;
    }

    @Override
    public User getById(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, id.toString());
        ResultSet resultSet = this.executeStatement(GET_BY_ID, parameters);
        while (resultSet.next()){
            User tmp =  new User(
                    resultSet.getString("username"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("password"),
                    resultSet.getString("type")
            );
            resultSet.close();
            return tmp;
        }
        return null;
    }

}
