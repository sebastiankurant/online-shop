package dao;

import model.Product;
import model.ProductCategory;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by pgurdek on 09.05.17.
 */
public interface ProductInterface {

    void add(Product product) throws SQLException;

    void remove(Integer id) throws SQLException;

    void update(Product editProduct) throws SQLException;

    Integer getByName(String name) throws SQLException;

    Product getById(Integer id) throws SQLException;

    List<Product> getAll() throws SQLException;

    List<Product> getAllPast() throws SQLException;

    List<Product> getByAllCategory(ProductCategory category) throws SQLException;
}
