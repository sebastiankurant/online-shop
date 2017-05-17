package codecool_shop.controller;

import codecool_shop.dao.CategoryDao;
import codecool_shop.dao.CategoryInterface;
import codecool_shop.dao.ProductDao;
import codecool_shop.dao.ProductInterface;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pgurdek on 11.05.17.
 */
public class AdminController {
    private CategoryInterface categoryDao = new CategoryDao();
    private ProductInterface productDao = new ProductDao();


    public ModelAndView displayIndex(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "admin/index");
    }
}
