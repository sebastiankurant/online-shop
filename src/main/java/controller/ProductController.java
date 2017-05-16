package controller;

import dao.CategoryDao;
import dao.CategoryInterface;
import dao.ProductDao;
import dao.ProductInterface;
import model.Product;
import model.ProductCategory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utilities.UtilityClass;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pgurdek on 14.05.17.
 */
public class ProductController {
    private ProductInterface eventDao = new ProductDao();
    private CategoryInterface categoryDao = new CategoryDao();
    private UtilityClass calculateClass = new UtilityClass();

    public ModelAndView displayEvents(Request req, Response res) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        List<Product> products = eventDao.getAll();
        List<ProductCategory> categoires = categoryDao.getAll();
        params.put("eventContaienr", products);
        params.put("allCategoires", categoires);
        params.put("UtilityClass", calculateClass);
        params.put("currentDate", new Date());
        return new ModelAndView(params, "index");
    }
}
