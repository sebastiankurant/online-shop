package codecool_shop.dao;

import codecool_shop.model.Product;

import java.sql.SQLException;

/**
 * Created by pgurdek on 12.05.17.
 */
public interface MetaInterface {

    void addMeta(Product product) throws SQLException;

    void removeMeta(Product product) throws SQLException;
}
