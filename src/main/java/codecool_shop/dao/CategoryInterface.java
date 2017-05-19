package codecool_shop.dao;

import codecool_shop.model.ProductCategory;

import java.sql.SQLException;
import java.util.List;

public interface CategoryInterface {

    void add(ProductCategory productCategory) throws SQLException;

    void remove(Integer id) throws SQLException;

    void update(ProductCategory productCategory) throws SQLException;

    void removeMeta(Integer id) throws SQLException;

    ProductCategory getBySlug(String slug) throws SQLException;

    List<ProductCategory> getAll() throws SQLException;

    ProductCategory getById(Integer id) throws SQLException;
}
