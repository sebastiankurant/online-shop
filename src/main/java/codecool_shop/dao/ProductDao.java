package codecool_shop.dao;

import codecool_shop.model.Product;
import codecool_shop.model.ProductCategory;
import org.sqlite.date.DateFormatUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductDao extends Dao implements ProductInterface, MetaInterface {

    private final String ADD = "INSERT INTO product (name,description,product_date)" +
            " VALUES(?,?,?)";
    private final String REMOVE = "DELETE FROM product" +
            " WHERE id=? ";
    private final String UPDATE = "UPDATE product SET name=?, description=?,product_date=?" +
            " WHERE id=? ";
    private final String ADD_META = "INSERT INTO product_meta (post_id,category_id)" +
            " VALUES(?,?)";
    private final String REMOVE_META = "DELETE FROM product_meta" +
            " WHERE post_id=? ";
    private final String GET_BY_ID = "SELECT id,name,description, product_date FROM product WHERE id=?";
    private final String GET_BY_ID_FUTURE = "SELECT id,name,description, product_date FROM product WHERE id=? AND product_date>=current_date ORDER BY product_date ASC";
    private final String GET_CATEGORIES_META = "SELECT category_id FROM product_meta WHERE post_id=?";
    private final String GET_BY_ALL_CATEGORY  = "SELECT post_id FROM product_meta LEFT JOIN product_category ON product_meta.category_id = product_category.id WHERE category_id = ?";
    private final String GET_ALL_PAST = "SELECT id,name,description, product_date FROM product WHERE product_date < current_date ORDER BY product_date  DESC ;";
    private final String GET_ALL = "SELECT id,name,description, product_date FROM product WHERE product_date>=current_date ORDER BY product_date  ASC ;";
    private final String GET_BY_NAME = "SELECT id  FROM product WHERE name=?";

    @Override
    public void add(Product product) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        String stringDate = DateFormatUtils.format(product.getDate(), "yyyy-MM-dd");
        parameters.put(1, product.getName());
        parameters.put(2, product.getDescription());
        parameters.put(3, stringDate);
        this.executeStatementUpdate(ADD, parameters);
    }

    @Override
    public void remove(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, id.toString());
        this.executeStatementUpdate(REMOVE, parameters);
    }

    @Override
    public void update(Product editProduct) throws SQLException {
        Map<Integer, String > parameters = new HashMap<>();
        String stringDate = DateFormatUtils.format(editProduct.getDate(), "yyyy-MM-dd");
        parameters.put(1, editProduct.getName());
        parameters.put(2, editProduct.getDescription());
        parameters.put(3, stringDate);
        parameters.put(4, editProduct.getId().toString());
        this.executeStatementUpdate(UPDATE, parameters);
    }

    @Override
    public void addMeta(Product product) throws SQLException {
        for (ProductCategory meta : product.getCategories()) {
            Map<Integer,String> parameters = new HashMap<>();
            parameters.put(1, product.getId().toString());
            parameters.put(2, meta.getId().toString());
            this.executeStatementUpdate(ADD_META, parameters);
        }
    }

    @Override
    public void removeMeta(Product product) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, product.getId().toString());
        this.executeStatementUpdate(REMOVE_META, parameters);
    }

    @Override
    public Product getById(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, id.toString());
        ResultSet rs = this.executeStatement(GET_BY_ID, parameters);
        return createProduct(rs);
    }

    public Product getByIdFuture(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, id.toString());
        ResultSet rs = this.executeStatement(GET_BY_ID_FUTURE, parameters);
        return createProduct(rs);
    }

    @Override
    public Integer getByName(String name) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1,name);
        ResultSet rs = executeStatement(GET_BY_NAME,parameters);
        if (!(rs == null)) {
            Integer productId = rs.getInt("id");
            rs.close();
            return productId;
        }
        rs.close();
        return null;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        ResultSet rs = this.executeStatement(GET_ALL);
        List<Product> products = createProductList(rs);
        rs.close();
        return products;
    }

    @Override
    public List<Product> getAllPast() throws SQLException {
        ResultSet rs = this.executeStatement(GET_ALL_PAST);
        List<Product> products = createProductList(rs);
        rs.close();
        return products;
    }

    @Override
    public List<Product> getByAllCategory(ProductCategory category) throws SQLException {
        List<Product> productByCategory = new ArrayList<>();
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(category.getId()));
        ResultSet rs = this.executeStatement(GET_BY_ALL_CATEGORY,parameters);
        while (rs.next()) {
            Integer productId = rs.getInt("post_id");
            Product product = getByIdFuture(productId);
            if (product != null) {
                productByCategory.add(getByIdFuture(productId));
            }
        }
        return productByCategory == null ? null : productByCategory;

    }

    private List<Product> createProductList(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            DateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd");
            String stringDate = rs.getString("product_date");
            Date product_date = null;
            List<ProductCategory> productCatList = getCategoriesFromMeta(rs);
            try {
                product_date = formatNew.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Product product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    product_date,
                    productCatList
            );
            products.add(product);
        }
        return products;
    }

    private List<ProductCategory> getCategoriesFromMeta(ResultSet rs) throws SQLException {
        CategoryDao categoryDao = new CategoryDao();
        List<ProductCategory> productCatList = new ArrayList<>();
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(rs.getInt("id")));
        ResultSet rs_cat = executeStatement(GET_CATEGORIES_META,parameters);
        while (rs_cat.next()) {
            productCatList.add(categoryDao.getById(rs_cat.getInt("category_id")));
        }
        rs_cat.close();
        return productCatList;
    }


    private Product createProduct(ResultSet resultSet) throws SQLException {

        Date product_date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        while (resultSet.next()) {
            String stringDate = resultSet.getString("product_date");
            try {
                product_date = format.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<ProductCategory> productCatList = getCategoriesFromMeta(resultSet);

            Product product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    product_date,
                    productCatList
            );
            resultSet.close();
            return product;
        }
        resultSet.close();
        return null;
    }
}
