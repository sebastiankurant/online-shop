package codecool_shop.controller;

import codecool_shop.dao.CategoryDao;
import codecool_shop.dao.CategoryInterface;
import codecool_shop.model.ProductCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class CategoryControllerAdmin extends BaseController {
    private CategoryInterface categoryDao = new CategoryDao();

    public ModelAndView renderCategory(Request request, Response response) {
        Map<String, Object> parameters = new HashMap<>();
        try {
            parameters.put("categoryContainer", categoryDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return render(parameters, "admin/category/index");
    }

    public ModelAndView addCategory(Request request, Response response) {
        return render("admin/category/add");
    }

    public ModelAndView editCategory(Request request, Response response) throws SQLException {
        Integer id = Integer.valueOf(request.params("id"));
        ProductCategory productCategory = categoryDao.getById(id);
        Map<String, Object> parameters = new HashMap<>();
        if (!(productCategory == null)) {
            parameters.put("categoryContainer", productCategory);
            return new ModelAndView(parameters, "admin/category/edit");
        }
        return render(parameters, "404");
    }

    public ModelAndView editCategoryPost(Request request, Response response) throws SQLException {
        String name = request.queryParams("name");
        String description = request.queryParams("description");
        Integer id = Integer.valueOf(request.params("id"));
        ProductCategory editCategory = categoryDao.getById(id);
        Map<String, Object> parameters = new HashMap<>();
        if (!name.isEmpty() && !(editCategory == null)) {
            editCategory.setName(name);
            editCategory.setDescription(description);
            editCategory.setSlug(name);
            categoryDao.update(editCategory);
            response.redirect("/admin/category");
        } else {
            parameters.put("categoryContainer", editCategory);
            parameters.put("errorContainer", "Category name cannot be empty");
            return new ModelAndView(parameters, "admin/category/edit");
        }
        return render(parameters, "admin/category/edit");

    }

    public ModelAndView addCategoryPost(Request request, Response response) throws SQLException {
        Map<String, Object> parameters = new HashMap<>();
        String name = request.queryParams("name");
        String description = request.queryParams("description");
        if (!name.isEmpty()) {
            ProductCategory newCategory = new ProductCategory();
            newCategory.setId(0);
            newCategory.setName(name);
            newCategory.setDescription(description);
            newCategory.setSlug(name);
            categoryDao.add(newCategory);
            response.redirect("/admin/category");
        } else {
            parameters.put("errorContainer", "Category name cannot be empty");
            return render(parameters, "admin/category/add");
        }
        return render(parameters, "admin/category/add");
    }

    public String removeCategory(Request request, Response response) throws SQLException {
        Integer id = Integer.valueOf(request.params("id"));
        ProductCategory catToDelete = categoryDao.getById(id);
        if (null != catToDelete) {
            categoryDao.remove(catToDelete.getId());
            categoryDao.removeMeta(catToDelete.getId());
            response.redirect("/admin/category/");
            return "Success";
        } else {
            response.redirect("/admin/category/");
            return "Failed";
        }
    }
}
