package codecool_shop.dao;

import codecool_shop.model.ProductSupplier;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monika on 16.05.17.
 */
public class SupplierDao extends Dao implements SupplierInterface {
    private final String ADD = "INSERT INTO product_supplier (name,address)" +
            " VALUES(?,?)";
    private final String UPDATE = "DELETE FROM product_supplier" +
            " WHERE id=? ";
    private final String DELETE = "DELETE FROM product_supplier" +
            " WHERE id=? ";
    private final String GET_BY_ID = "SELECT id,name,address FROM product_supplier WHERE id=?";

    private final String GET_ALL = "SELECT id,name,address FROM product_supplier";



    @Override
    public void add(ProductSupplier productSupplier) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, productSupplier.getName());
        parameters.put(2, productSupplier.getAddress());
        this.executeStatementUpdate(ADD, parameters);
    }

}
