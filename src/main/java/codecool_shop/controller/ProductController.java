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

public class ProductController {
    private ProductInterface productDao;
    private CategoryInterface categoryDao;
    private UtilityClass calculateClass;

    public ProductController(ProductInterface productDao, CategoryInterface categoryDao){
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.calculateClass = new UtilityClass();
    }

    public ModelAndView displayProducts(Request request, Response response) throws SQLException {
        Map<String, Object> paramteres = new HashMap<>();
        paramteres.put("authenticationSucceeded", request.session().attribute("authenticationSucceeded"));
        List<Product> products = productDao.getAll();
        List<ProductCategory> categoires = categoryDao.getAll();
        paramteres.put("productContainer", products);
        paramteres.put("allCategoires", categoires);
        paramteres.put("UtilityClass", calculateClass);
        paramteres.put("currentDate", new Date());
        Boolean addedToCart = request.session().attribute("addedToCart");
        paramteres.put("Msg", addedToCart);
        String productName = request.session().attribute("productName");
        paramteres.put("productName", productName);
        request.session().attribute("addedToCart", false);

        return new ModelAndView(paramteres, "index");
    }
}
