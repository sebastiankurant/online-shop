package codecool_shop.dao;

import codecool_shop.Application;
import codecool_shop.model.ProductSupplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDao extends Dao implements SupplierInterface {

    private final String ADD = "INSERT INTO product_supplier (name,address)" +
            " VALUES(?,?)";
    private final String UPDATE = "UPDATE product_supplier SET name=?, address=?" +
            " WHERE id=? ";
    private final String DELETE = "DELETE FROM product_supplier" +
            " WHERE id=? ";
    private final String GET_BY_ID = "SELECT id,name,address FROM product_supplier WHERE id=?";

    private final String GET_ALL = "SELECT id,name,address FROM product_supplier";

    public SupplierDao(){
        this.connection = Application.getConnection();
    }

    public SupplierDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(ProductSupplier productSupplier) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, productSupplier.getName());
        parameters.put(2, productSupplier.getAddress());
        this.executeStatementUpdate(ADD, parameters);
    }

    @Override
    public List<ProductSupplier> getAll() throws SQLException {
        ResultSet resultSet = this.executeStatement(GET_ALL);
        List<ProductSupplier> productSupplierList = createSupplierList(resultSet);
        resultSet.close();
        return productSupplierList;
    }


    private List<ProductSupplier> createSupplierList(ResultSet resultSet) throws SQLException {
        List<ProductSupplier> productSuppliers = new ArrayList<>();

        while (resultSet.next()) {
            ProductSupplier productSupplier = new ProductSupplier(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address")
            );
            productSuppliers.add(productSupplier);
        }

        resultSet.close();
        return productSuppliers;
    }

    @Override
    public void update(ProductSupplier productSupplier) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, productSupplier.getName());
        parameters.put(2, productSupplier.getAddress());
        parameters.put(3, String.valueOf(productSupplier.getId()));
        this.executeStatementUpdate(UPDATE, parameters);
    }

    @Override
    public void remove(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(id));
        this.executeStatementUpdate(DELETE, parameters);
    }

    @Override
    public ProductSupplier getById(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(id));

        ResultSet resultSet = this.executeStatement(GET_BY_ID, parameters);

        if (!(resultSet == null) && resultSet.isBeforeFirst()) {
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            resultSet.close();
            return new ProductSupplier(id, name, address);
        }
        resultSet.close();
        return null;
    }
}
