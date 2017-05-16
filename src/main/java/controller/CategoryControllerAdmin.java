package controller;

import dao.CategoryDao;
import dao.CategoryInterface;
import model.ProductCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pgurdek on 10.05.17.
 */
public class CategoryControllerAdmin {
    private CategoryInterface categoryDao = new CategoryDao();

    public ModelAndView renderCategory(Request req, Response res) {
        Map<Object, Object> params = new HashMap<>();
        try {
            params.put("categoryContainer", categoryDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView(params, "admin/category/index");
    }

    public ModelAndView addCategory(Request req, Response res) {
        Map<Object, Object> params = new HashMap<>();
        return new ModelAndView(params, "admin/category/add");
    }

    public ModelAndView editCategory(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        ProductCategory productCategory = categoryDao.getById(id);
        Map params = new HashMap<>();
        if (!(productCategory == null)) {
            params.put("categoryContainer", productCategory);
            System.out.println("tutaj");
            return new ModelAndView(params, "admin/category/edit");
        }
        return new ModelAndView(params, "404");
    }

    public ModelAndView editCategoryPost(Request req, Response res) throws SQLException {
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        Integer id = Integer.valueOf(req.params("id"));
        ProductCategory editCategory = categoryDao.getById(id);
        Map params = new HashMap<>();
        if (!name.isEmpty() && !(editCategory == null)) {
            editCategory.setName(name);
            editCategory.setDescription(description);
            editCategory.setSlug(name);
            categoryDao.update(editCategory);
            res.redirect("/admin/category");
        } else {
            params.put("categoryContainer", editCategory);
            params.put("errorContainer", "Category name cannot be empty");
            return new ModelAndView(params, "admin/category/edit");
        }
        return new ModelAndView(params, "admin/category/edit");

    }

    public ModelAndView addCategoryPost(Request req, Response res) throws SQLException {
        Map params = new HashMap<>();
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        if (!name.isEmpty()) {
            ProductCategory newCategory = new ProductCategory();
            newCategory.setId(0);
            newCategory.setName(name);
            newCategory.setDescription(description);
            newCategory.setSlug(name);
            categoryDao.add(newCategory);
            res.redirect("/admin/category");
        } else {
            params.put("errorContainer", "Category name cannot be empty");
            return new ModelAndView(params, "admin/category/add");
        }
        return new ModelAndView(params, "admin/category/add");
    }

    public String removeCategory(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        ProductCategory catToDelete = categoryDao.getById(id);
        if (null != catToDelete) {
            categoryDao.remove(catToDelete.getId());
            categoryDao.removeMeta(catToDelete.getId());
            res.redirect("/admin/category/");
            return "Sucess";
        } else {
            res.redirect("/admin/category/");
            return "Failed";
        }
    }
}
