package codecool_shop.controller;


import codecool_shop.model.Product;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgurdek on 17.05.17.
 */
public class SessionController extends BaseController{


    public void manageBasketSession(Request req) {
        if (req.session().attributes().isEmpty()) {
            req.session(true);
            List<Product> basketProductList = new ArrayList<>();
            req.session().attribute("basketProductList", basketProductList);
        }
    }
}
