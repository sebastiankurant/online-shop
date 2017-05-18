package codecool_shop.controller;

import codecool_shop.dao.ProductDao;
import codecool_shop.dao.ProductInterface;
import codecool_shop.model.Product;
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

    public ModelAndView getBasket(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        List<Product> basketProductList;
        basketProductList = request.session().attribute("basketProductList");
        if (!(basketProductList == null)) {
            params.put("basket", basketProductList);
            params.put("removeFromBasket", request.session().attribute("removeFromBasket"));
            request.session().attribute("removeFromBasket",false);
        }

        return render(params, "basket/basket");
    }

    public String addToBasket(Request request, Response response) throws SQLException {
        Integer id = Integer.valueOf(request.queryParams("add_product"));
        List<Product> basketProductList;
        basketProductList = request.session().attribute("basketProductList");
        Product tmpProduct = produtDao.getById(id);
        basketProductList.add(tmpProduct);
        request.session().attribute("basketProductList", basketProductList);
        request.session().attribute("addedToCart", true);
        request.session().attribute("productName", tmpProduct.getName());
        response.redirect("/");
        return "";
    }

    public String removeProduct(Request request, Response response) throws SQLException {
        Integer id = Integer.valueOf(request.queryParams("remove_product"));
        List<Product> basketProductList;
        basketProductList = request.session().attribute("basketProductList");
        for (Product product : basketProductList) {
            if (product.getId() == id) {
                basketProductList.remove(product);
                request.session().attribute("removeFromBasket", true);
                break;
            }
        }
        request.session().attribute("basketProductList", basketProductList);
        response.redirect("/basket/");
        return "";
    }
}
