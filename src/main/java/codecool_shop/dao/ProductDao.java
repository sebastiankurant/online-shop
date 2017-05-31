package codecool_shop.dao;

import codecool_shop.Application;
import codecool_shop.model.Product;
import codecool_shop.model.ProductCategory;
import codecool_shop.model.ProductSupplier;
import org.sqlite.date.DateFormatUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductDao extends Dao implements ProductInterface, MetaInterface {

    private final String ADD = "INSERT INTO product (name,description,product_date,url,supplier_id,price)" +
            " VALUES(?,?,?,?,?,?)";
    private final String REMOVE = "DELETE FROM product" +
            " WHERE id=? ";
    private final String UPDATE = "UPDATE product SET name=?, description=?,product_date=?,url=?,supplier_id=?,price=?" +
            " WHERE id=? ";
    private final String ADD_META = "INSERT INTO product_meta (post_id,category_id)" +
            " VALUES(?,?)";
    private final String REMOVE_META = "DELETE FROM product_meta" +
            " WHERE post_id=? ";
    private final String GET_BY_ID = "SELECT id,name,description, product_date,url,supplier_id,price FROM product WHERE id=?";
    private final String GET_BY_ID_FUTURE = "SELECT id,name,description, product_date,url,supplier_id,price FROM product WHERE id=? AND product_date>=current_date ORDER BY product_date ASC";
    private final String GET_CATEGORIES_META = "SELECT category_id FROM product_meta WHERE post_id=?";
    private final String GET_BY_ALL_CATEGORY = "SELECT post_id FROM product_meta LEFT JOIN product_category ON product_meta.category_id = product_category.id WHERE category_id = ?";
    private final String GET_ALL_PAST = "SELECT id,name,description, product_date, url, supplier_id,price FROM product WHERE product_date < date(current_date,'-1 day') ORDER BY product_date  DESC ;";
    private final String GET_ALL = "SELECT id,name,description, product_date ,url, supplier_id,price FROM product WHERE product_date >= date(current_date,'-1 day') ORDER BY product_date  ASC ;";
    private final String GET_BY_NAME = "SELECT id  FROM product WHERE name=?";
    private final String GET_ALL_BY_SUPPLIER = "SELECT id,name,description, product_date ,url, supplier_id,price FROM product WHERE supplier_id=?";

    private SupplierInterface supplierDao = new SupplierDao();

    public ProductDao() {
        this.connection = Application.getConnection();
    }

    public ProductDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public void add(Product product) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        String supplier = null;
        String stringDate = DateFormatUtils.format(product.getDate(), "yyyy-MM-dd");
        parameters.put(1, product.getName());
        parameters.put(2, product.getDescription());
        parameters.put(3, stringDate);
        parameters.put(4, product.getUrl());
        try {
            supplier = String.valueOf(product.getSupplier().getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        parameters.put(5, supplier);
        parameters.put(6, String.valueOf(product.getPrice()));
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
        Map<Integer, String> parameters = new HashMap<>();
        String stringDate = DateFormatUtils.format(editProduct.getDate(), "yyyy-MM-dd");
        parameters.put(1, editProduct.getName());
        parameters.put(2, editProduct.getDescription());
        parameters.put(3, stringDate);
        parameters.put(4, editProduct.getUrl());
        parameters.put(5, String.valueOf(editProduct.getSupplier().getId()));
        parameters.put(6, String.valueOf(editProduct.getPrice()));
        parameters.put(7, editProduct.getId().toString());
        this.executeStatementUpdate(UPDATE, parameters);
    }

    @Override
    public void addMeta(Product product) throws SQLException {
        for (ProductCategory meta : product.getCategories()) {
            Map<Integer, String> parameters = new HashMap<>();
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
        ResultSet resultSet = this.executeStatement(GET_BY_ID, parameters);
        return createProduct(resultSet);
    }

    private Product getByIdFuture(Integer id) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, id.toString());
        ResultSet rs = this.executeStatement(GET_BY_ID_FUTURE, parameters);
        return createProduct(rs);
    }

    @Override
    public Integer getByName(String name) throws SQLException {
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, name);
        ResultSet resultSet = executeStatement(GET_BY_NAME, parameters);
        if (!(resultSet == null)) {
            Integer productId = resultSet.getInt("id");
            resultSet.close();
            return productId;
        }
        resultSet.close();
        return null;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        ResultSet resultSet = this.executeStatement(GET_ALL);
        List<Product> products = createProductList(resultSet);
        resultSet.close();
        return products;
    }

    @Override
    public List<Product> getAllPast() throws SQLException {
        ResultSet resultSet = this.executeStatement(GET_ALL_PAST);
        List<Product> products = createProductList(resultSet);
        resultSet.close();
        return products;
    }

    @Override
    public List<Product> getByAllCategory(ProductCategory category) throws SQLException {
        List<Product> productByCategory = new ArrayList<>();
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(category.getId()));
        ResultSet resultSet = this.executeStatement(GET_BY_ALL_CATEGORY, parameters);
        while (resultSet.next()) {
            Integer productId = resultSet.getInt("post_id");
            Product product = getByIdFuture(productId);
            if (product != null) {
                productByCategory.add(getByIdFuture(productId));
            }
        }
        resultSet.close();
        return productByCategory == null ? null : productByCategory;

    }

    public List<Product> getBySupplier(Integer supplierId) throws SQLException {
        List<Product> productBySupplier = new ArrayList<>();
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(supplierId));
        ResultSet resultSet = this.executeStatement(GET_ALL_BY_SUPPLIER, parameters);
        while (resultSet.next()) {
            Integer productId = resultSet.getInt("id");
            Product product = getById(productId);
            if (product != null) {
                productBySupplier.add(product);
            }

        }
        resultSet.close();
        return productBySupplier == null ? null : productBySupplier;
    }

    private List<Product> createProductList(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            DateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd");
            String stringDate = resultSet.getString("product_date");
            Date product_date = null;
            List<ProductCategory> productCatList = getCategoriesFromMeta(resultSet);
            ProductSupplier supplier = null;
            String supplierId = resultSet.getString("supplier_id");
            if (supplierId != null && !supplierId.equals("")) {
                supplier = supplierDao.getById(Integer.valueOf(resultSet.getString("supplier_id")));
            }
            try {
                product_date = formatNew.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Product product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    product_date,
                    productCatList,
                    resultSet.getString("url"),
                    supplier,
                    resultSet.getInt("price"));
            products.add(product);
        }
        resultSet.close();
        return products;
    }

    private List<ProductCategory> getCategoriesFromMeta(ResultSet resultSet) throws SQLException {
        CategoryDao categoryDao = new CategoryDao();
        List<ProductCategory> productCategoryList = new ArrayList<>();
        Map<Integer, String> parameters = new HashMap<>();
        parameters.put(1, String.valueOf(resultSet.getInt("id")));
        ResultSet resultSetCategory = executeStatement(GET_CATEGORIES_META, parameters);
        while (resultSetCategory.next()) {
            productCategoryList.add(categoryDao.getById(resultSetCategory.getInt("category_id")));
        }
        resultSetCategory.close();
        return productCategoryList;
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {

        Date product_date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        while (resultSet.next()) {
            ProductSupplier supplier = null;
            String stringDate = resultSet.getString("product_date");
            try {
                product_date = format.parse(stringDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<ProductCategory> productCatList = getCategoriesFromMeta(resultSet);
            String supplierId = resultSet.getString("supplier_id");
            if (supplierId != null && !supplierId.equals("")) {
                supplier = supplierDao.getById(Integer.valueOf(resultSet.getString("supplier_id")));
            }
            Product product = new Product(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    product_date,
                    productCatList,
                    resultSet.getString("url"),
                    supplier,
                    resultSet.getInt("price"));
            resultSet.close();
            return product;
        }
        resultSet.close();
        return null;
    }
}
