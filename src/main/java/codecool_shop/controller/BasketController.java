package codecool_shop.controller;

import codecool_shop.dao.ProductDao;
import codecool_shop.dao.ProductInterface;
import codecool_shop.model.Product;
import com.sun.xml.internal.rngom.parse.host.Base;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pgurdek on 16.05.17.
 */
public class BasketController extends BaseController{

    private ProductInterface produtDao = new ProductDao();
    private ProductController productController = new ProductController();

    public ModelAndView getBasket(Request req, Response res) {
        Map<String, Object> params = new HashMap<>();
        List<Product> basketProductList;
        basketProductList = req.session().attribute("basketProductList");
        if (!(basketProductList == null)) {
            params.put("basket", basketProductList);
            params.put("removeFromBasket", req.session().attribute("removeFromBasket"));
            req.session().attribute("removeFromBasket",false);
        }

        return new ModelAndView(params, "basket/basket");
    }

    public String addToBasket(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.queryParams("add_product"));
        List<Product> basketProductList;
        basketProductList = req.session().attribute("basketProductList");
        Product tmpProduct = produtDao.getById(id);
        basketProductList.add(tmpProduct);
        req.session().attribute("basketProductList", basketProductList);
        req.session().attribute("addedToCart", true);
        req.session().attribute("productName", tmpProduct.getName());
        res.redirect("/");
        return "";
    }

    public String removeProduct(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.queryParams("remove_product"));
        List<Product> basketProductList;
        basketProductList = req.session().attribute("basketProductList");
        for (Product product : basketProductList) {
            if (product.getId() == id) {
                basketProductList.remove(product);
                req.session().attribute("removeFromBasket", true);
                break;
            }
        }
        req.session().attribute("basketProductList", basketProductList);
        res.redirect("/basket/");
        return "";
    }
}
