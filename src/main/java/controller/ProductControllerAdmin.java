package controller;

import dao.*;
import model.Product;
import model.ProductCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utilities.UtilityClass;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductControllerAdmin {
    private ProductInterface eventDao = new ProductDao();
    private CategoryInterface categoryDao = new CategoryDao();
    private MetaInterface eventMeta = new ProductDao();
    private UtilityClass calculateClass = new UtilityClass();

    public ModelAndView renderEvents(Request req, Response res) throws SQLException {
        //Get products from database by Dao
        Map params = new HashMap<>();
        try {
            params.put("productContainer", eventDao.getAll());
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

    public ModelAndView addEvent(Request req, Response res) {
        Map params = new HashMap<>();
        try {
            List<ProductCategory> availableCategory = categoryDao.getAll();
            params.put("availableCategory", availableCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView(params, "/admin/products/add");
    }

    public ModelAndView addEventPost(Request req, Response res) throws SQLException {
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
            eventDao.add(newProduct);
            Integer eventId = eventDao.getByName(newProduct.getName());
            newProduct.setId(eventId);
            eventMeta.addMeta(newProduct);
            res.redirect("/admin/products/");
        } else {
            params.put("errorContainer", "Name or Date is not invalid");
            return new ModelAndView(params, "admin/products/add");
        }
        return new ModelAndView(params, "/admin/products/add");
    }

    public ModelAndView editEvent(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        Product productToEdit = eventDao.getById(id);
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

    public String editEventPost(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        String postDate = req.queryParams("date");
        String[] categoryList = req.queryParamsValues("category");
        Date date = null;
        Product editProduct = eventDao.getById(id);
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
            eventDao.update(editProduct);
            eventMeta.removeMeta(editProduct);
            eventMeta.addMeta(editProduct);
            res.redirect("/admin/products/");
            return "Sucess";
        }
        return "Error";
    }

    public String removeEvent(Request req, Response res) throws SQLException {
        System.out.println("Remove Product");
        Integer id = Integer.valueOf(req.params("id"));
        System.out.println(id);
        Product productToDelete = eventDao.getById(id);

        if (!(productToDelete == null)) {
            eventDao.remove(productToDelete.getId());
            eventMeta.removeMeta(productToDelete);
            res.redirect("/admin/products/");
            return "works";
        }
        res.redirect("/admin/products/");
        return "";
    }

    public ModelAndView pastEvents(Request req, Response res) throws SQLException {
        Map params = new HashMap<>();
        try {
            params.put("productContainer", eventDao.getAllPast());
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
                params.put("productContainer", eventDao.getByAllCategory(catObject));
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
