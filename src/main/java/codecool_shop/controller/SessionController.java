package codecool_shop.controller;


import codecool_shop.model.Product;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class SessionController {

    public void manageBasketSession(Request request, Response response) {
        if (request.session().attributes().isEmpty()) {
            request.session(true);
            List<Product> basketProductList = new ArrayList<>();
            request.session().attribute("authenticationSucceeded", false);
            request.session().attribute("basketProductList", basketProductList);
        }
    }
}
