package dao;

import model.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pgurdek on 10.05.17.
 */
public class CategoryDao extends Dao implements CategoryInterface {

    private final String ADD = "INSERT INTO product_category (name,description,slug)" +
            " VALUES(?,?,?)";
    private final String UPDATE = "DELETE FROM product_category" +
            " WHERE id=? ";
    private final String DELETE = "DELETE FROM product_category" +
            " WHERE id=? ";
    private final String DELETE_META = "DELETE FROM product_meta" +
            " WHERE category_id=? ";
    private final String GET_BY_ID = "SELECT id,name,description,slug FROM product_category WHERE id=?";

    private final String GET_BY_SLUG = "SELECT id,name,description,slug FROM product_category WHERE slug=?";

    private final String GET_ALL = "SELECT id,name,description,slug FROM product_category";

    @Override
    public void add(ProductCategory productCategory) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, productCategory.getName());
        parameters.put(2, productCategory.getDescription());
        parameters.put(3, productCategory.getSlug());
        this.executeStatementUpdate(ADD, parameters);
    }

    @Override
    public void update(ProductCategory productCategory) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, productCategory.getName());
        parameters.put(2, productCategory.getDescription());
        parameters.put(3, String.valueOf(productCategory.getId()));
        this.executeStatementUpdate(UPDATE, parameters);
    }

    @Override
    public void remove(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(id));
        this.executeStatementUpdate(DELETE, parameters);
    }

    @Override
    public void removeMeta(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(id));
        this.executeStatementUpdate(DELETE_META, parameters);
    }

    @Override
    public ProductCategory getById(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(id));

        ResultSet rs = this.executeStatement(GET_BY_ID, parameters);

        if (!(rs == null) && rs.isBeforeFirst()) {
            String name = rs.getString("name");
            String description = rs.getString("description");
            String slug = rs.getString("slug");
            rs.close();
            return new ProductCategory(id, name, description, slug);
        }
        rs.close();
        return null;
    }

    @Override
    public ProductCategory getBySlug(String slug) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, slug);
        ResultSet rs = this.executeStatement(GET_BY_SLUG, parameters);
        if (!(rs == null) && rs.isBeforeFirst()) {
            Integer id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            rs.close();
            return new ProductCategory(id, name, description, slug);
        }
        rs.close();
        return null;
    }

    @Override
    public List<ProductCategory> getAll() throws SQLException {
        ResultSet rs = this.executeStatement(GET_ALL);
        List<ProductCategory> categoryEvents = createCategoryList(rs);
        rs.close();
        return categoryEvents;
    }

    private List<ProductCategory> createCategoryList(ResultSet rs) throws SQLException {
        List<ProductCategory> productCategories = new ArrayList<>();

        while (rs.next()) {
            ProductCategory productCategory = new ProductCategory(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("slug")
            );
            productCategories.add(productCategory);
        }
        return productCategories;
    }
}
