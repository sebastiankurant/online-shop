package codecool_shop.dao;

import codecool_shop.model.ProductSupplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierInterface {
    void add(ProductSupplier productSupplier) throws SQLException;

    void remove(Integer id) throws SQLException;

    void update(ProductSupplier productSupplier) throws SQLException;

    List<ProductSupplier> getAll() throws SQLException;

    ProductSupplier getById(Integer id) throws SQLException;
}
