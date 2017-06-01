package codecool_shop.dao;

import codecool_shop.model.Product;
import codecool_shop.model.ProductCategory;

import java.sql.SQLException;
import java.util.List;

public interface ProductInterface {

    void add(Product product) throws SQLException;

    void remove(Integer id) throws SQLException;

    void update(Product editProduct) throws SQLException;

    Integer getIdByProductName(String name) throws SQLException;

    Product getById(Integer id) throws SQLException;

    List<Product> getAll() throws SQLException;

    List<Product> getAllPast() throws SQLException;

    List<Product> getByAllCategory(ProductCategory category) throws SQLException;

    List<Product> getBySupplier(Integer supplierId) throws SQLException;
}
