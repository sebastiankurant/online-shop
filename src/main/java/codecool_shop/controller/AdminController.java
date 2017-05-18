package codecool_shop.controller;

import codecool_shop.dao.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pgurdek on 11.05.17.
 */
public class AdminController extends BaseController{

    public ModelAndView displayIndex(Request req, Response res) {
        Map params = new HashMap<>();
        return new ModelAndView(params, "admin/index");
    }
}
