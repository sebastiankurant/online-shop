package controller;

import dao.CategoryDao;
import dao.CategoryInterface;
import dao.ProductDao;
import dao.ProductInterface;
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
    private ProductInterface eventDao = new ProductDao();


    public ModelAndView displayIndex(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "admin/index");
    }
}
