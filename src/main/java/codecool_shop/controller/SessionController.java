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


    public void manageBasketSession(Request request, Response response) {
        if (request.session().attributes().isEmpty()) {
            request.session(true);
            List<Product> basketProductList = new ArrayList<>();
            request.session().attribute("basketProductList", basketProductList);
        }
    }
}
