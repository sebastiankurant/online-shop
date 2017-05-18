package codecool_shop.controller;

import codecool_shop.dao.CategoryDao;
import codecool_shop.dao.CategoryInterface;
import codecool_shop.dao.ProductDao;
import codecool_shop.dao.ProductInterface;
import codecool_shop.model.Product;
import codecool_shop.model.ProductCategory;
import codecool_shop.utilities.UtilityClass;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pgurdek on 14.05.17.
 */
public class ProductController extends BaseController{
    private ProductInterface productDao = new ProductDao();
    private CategoryInterface categoryDao = new CategoryDao();
    private UtilityClass calculateClass = new UtilityClass();

    public ModelAndView displayProducts(Request req) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        List<Product> products = productDao.getAll();
        List<ProductCategory> categoires = categoryDao.getAll();
        params.put("eventContainer", products);
        params.put("allCategoires", categoires);
        params.put("UtilityClass", calculateClass);
        params.put("currentDate", new Date());
// Test
        Boolean addedToCart = req.session().attribute("addedToCart");
        params.put("Msg", addedToCart);
        String productName = req.session().attribute("productName");
        params.put("productName", productName);
        req.session().attribute("addedToCart", false);

//        End
        return new ModelAndView(params, "index");
    }
}
