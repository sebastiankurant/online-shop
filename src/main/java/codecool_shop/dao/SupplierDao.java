package codecool_shop.dao;

import codecool_shop.model.ProductSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public List<ProductSupplier> getAll() throws SQLException {
        ResultSet rs = this.executeStatement(GET_ALL);
        List<ProductSupplier> productSupplierList = createSupplierList(rs);
        rs.close();
        return productSupplierList;
    }


    private List<ProductSupplier> createSupplierList(ResultSet rs) throws SQLException {
        List<ProductSupplier> productSuppliers = new ArrayList<>();

        while (rs.next()) {
            ProductSupplier productSupplier = new ProductSupplier(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("address")
            );
            productSuppliers.add(productSupplier);
        }
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
}
