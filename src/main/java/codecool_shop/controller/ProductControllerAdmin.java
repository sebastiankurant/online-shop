package codecool_shop.controller;

import codecool_shop.dao.*;
import codecool_shop.model.Product;
import codecool_shop.model.ProductCategory;
import codecool_shop.utilities.UtilityClass;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductControllerAdmin {
    private ProductInterface productDao = new ProductDao();
    private CategoryInterface categoryDao = new CategoryDao();
    private MetaInterface eventMeta = new ProductDao();
    private UtilityClass calculateClass = new UtilityClass();

    public ModelAndView renderProducts(Request req, Response res) throws SQLException {
        //Get products from database by Dao
        Map params = new HashMap<>();
        try {
            params.put("productContainer", productDao.getAll());
            params.put("categoryAvailable", categoryDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        UtilityClass calculateClass = new UtilityClass();
        params.put("UtilityClass", calculateClass);
        params.put("currentDate", currentDate);
        return new ModelAndView(params, "/admin/products/index");
    }

    public ModelAndView addProduct(Request req, Response res) {
        Map params = new HashMap<>();
        try {
            List<ProductCategory> availableCategory = categoryDao.getAll();
            params.put("availableCategory", availableCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView(params, "/admin/products/add");
    }

    public ModelAndView addProductPost(Request req, Response res) throws SQLException {
        //Get products from database by Dao
        Map params = new HashMap<>();
        try {
            List<ProductCategory> availableCategory = categoryDao.getAll();
            params.put("availableCategory", availableCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        String postDate = req.queryParams("date");
        String[] categoryList = req.queryParamsValues("category");
        Date date = null;
        try {
            date = format.parse(postDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!name.isEmpty() && date != null) {
            List<ProductCategory> catList = new ArrayList<>();
            if (categoryList != null) {
                for (String category_slug : categoryList) {
                    ProductCategory tempCat = categoryDao.getBySlug(category_slug);
                    catList.add(tempCat);
                }
            }
            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setDescription(description);
            newProduct.setDate(date);
            newProduct.setCategories(catList);
            productDao.add(newProduct);
            Integer eventId = productDao.getByName(newProduct.getName());
            newProduct.setId(eventId);
            eventMeta.addMeta(newProduct);
            res.redirect("/admin/products/");
        } else {
            params.put("errorContainer", "Name or Date is not invalid");
            return new ModelAndView(params, "admin/products/add");
        }
        return new ModelAndView(params, "/admin/products/add");
    }

    public ModelAndView editProduct(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        Product productToEdit = productDao.getById(id);
        Map params = new HashMap<>();
        if (!(productToEdit == null)) {
            try { // Set attributes which are already checked
                List<ProductCategory> availableCategory = categoryDao.getAll();
                for (ProductCategory availableCat : availableCategory) {
                    for (ProductCategory cat : productToEdit.getCategories()) {
                        if (availableCat.getId() == cat.getId()) {
                            availableCat.setEqual(true);
                        }
                    }
                }
                productToEdit.setCategories(availableCategory);
                params.put("productContainer", productToEdit);
                return new ModelAndView(params, "/admin/products/edit");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return new ModelAndView(params, "404");
    }

    public String editEventProduct(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        String postDate = req.queryParams("date");
        String[] categoryList = req.queryParamsValues("category");
        Date date = null;
        Product editProduct = productDao.getById(id);
        try {
            date = format.parse(postDate);
            System.out.println(postDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!name.isEmpty() && date != null && editProduct != null) {
            List<ProductCategory> catList = new ArrayList<>();
            if (categoryList != null) {
                for (String category_slug : categoryList) {
                    ProductCategory tempCat = categoryDao.getBySlug(category_slug);
                    catList.add(tempCat);
                }
            }
            System.out.println(catList);
            editProduct.setName(name);
            editProduct.setDescription(description);
            editProduct.setDate(date);
            editProduct.setCategories(catList);
            productDao.update(editProduct);
            eventMeta.removeMeta(editProduct);
            eventMeta.addMeta(editProduct);
            res.redirect("/admin/products/");
            return "Sucess";
        }
        return "Error";
    }

    public String removeProduct(Request req, Response res) throws SQLException {
        System.out.println("Remove Product");
        Integer id = Integer.valueOf(req.params("id"));
        System.out.println(id);
        Product productToDelete = productDao.getById(id);

        if (!(productToDelete == null)) {
            productDao.remove(productToDelete.getId());
            eventMeta.removeMeta(productToDelete);
            res.redirect("/admin/products/");
            return "works";
        }
        res.redirect("/admin/products/");
        return "";
    }

    public ModelAndView pastProducts(Request req, Response res) throws SQLException {
        Map params = new HashMap<>();
        try {
            params.put("productContainer", productDao.getAllPast());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        UtilityClass calculateClass = new UtilityClass();
        params.put("UtilityClass", calculateClass);
        params.put("currentDate", currentDate);
        return new ModelAndView(params, "/admin/products/index");
    }

    public ModelAndView filterCategory(Request req, Response res) throws SQLException {
        Map params = new HashMap<>();
        String category = req.queryParams("cat");
        ProductCategory catObject = null;
        try {
            catObject = categoryDao.getById(Integer.valueOf(category));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (catObject != null) {
            try {
                List<ProductCategory> availableCategory = categoryDao.getAll();
                params.put("categoryAvailable", availableCategory);
                params.put("productContainer", productDao.getByAllCategory(catObject));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            params.put("UtilityClass", calculateClass);
            params.put("currentDate", new Date());
            return new ModelAndView(params, "/admin/products/index");
        }
        return new ModelAndView(params, "/admin/products/index");
    }
}
