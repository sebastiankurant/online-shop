package codecool_shop.controller;

import codecool_shop.dao.ProductDao;
import codecool_shop.dao.ProductInterface;
import codecool_shop.model.Product;
import spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.servlet.http.Cookie;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

/**
 * Created by pgurdek on 16.05.17.
 */
public class BasketController {

    private ProductInterface produtDao = new ProductDao();
    private ProductController productController = new ProductController();

    public ModelAndView getBasket(Request req, Response res){
        Map<String,Object> params = new HashMap<>();
        return new ModelAndView(params,"basket/basket");
    }

    public String addToBasket(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.queryParams("add_product"));
        System.out.println(id);
        List<Product> basketProductList;
        basketProductList = req.session().attribute("basketProductList");
        basketProductList.add(produtDao.getById(id));
        req.session().attribute("addedToCart",true);
        for (Product pro:basketProductList){
            System.out.println(pro.getName());
        }
        res.redirect("/");
        return "";

//        RouteImpl route   = new RouteImpl();
//        return route.handle(req, res);
//
//        return (request, responses) -> {
//            responses.redirect("/");
//            return null;
//        };
    }
}
