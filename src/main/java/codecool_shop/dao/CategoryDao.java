package codecool_shop.dao;

import codecool_shop.Application;
import codecool_shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDao extends Dao implements CategoryInterface {

    private final String ADD = "INSERT INTO product_category (name,description,slug)" +
            " VALUES(?,?,?)";

    private final String UPDATE = "UPDATE product_category SET name=?, description=?, slug=? " +
            " WHERE id=? ";

    private final String DELETE = "DELETE FROM product_category" +
            " WHERE id=? ";

    private final String DELETE_META = "DELETE FROM product_meta" +
            " WHERE category_id=? ";

    private final String GET_BY_ID = "SELECT id,name,description,slug FROM product_category WHERE id=?";

    private final String GET_BY_SLUG = "SELECT id,name,description,slug FROM product_category WHERE slug=?";

    private final String GET_ALL = "SELECT id,name,description,slug FROM product_category";

    public CategoryDao(){
        this.connection = Application.getConnection();
    }

    public CategoryDao(Connection connection){
        this.connection = connection;
    }

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
        parameters.put(3, productCategory.getSlug());
        parameters.put(4, String.valueOf(productCategory.getId()));
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

        ResultSet resultSet = this.executeStatement(GET_BY_ID, parameters);

        if (!(resultSet == null) && resultSet.isBeforeFirst()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String slug = resultSet.getString("slug");
            resultSet.close();
            return new ProductCategory(id, name, description, slug);
        }
        resultSet.close();
        return null;
    }

    @Override
    public ProductCategory getBySlug(String slug) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, slug);
        ResultSet resultSet = this.executeStatement(GET_BY_SLUG, parameters);
        if (!(resultSet == null) && resultSet.isBeforeFirst()) {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            resultSet.close();
            return new ProductCategory(id, name, description, slug);
        }
        resultSet.close();
        return null;
    }

    @Override
    public List<ProductCategory> getAll() throws SQLException {
        ResultSet resultSet = this.executeStatement(GET_ALL);
        List<ProductCategory> categoryEvents = createCategoryList(resultSet);
        resultSet.close();
        return categoryEvents;
    }

    private List<ProductCategory> createCategoryList(ResultSet resultSet) throws SQLException {
        List<ProductCategory> productCategories = new ArrayList<>();

        while (resultSet.next()) {
            ProductCategory productCategory = new ProductCategory(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("slug")
            );
            productCategories.add(productCategory);
        }
        resultSet.close();
        return productCategories;
    }
}
