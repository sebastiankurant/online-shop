package codecool_shop.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;


public class AdminController extends BaseController {

    public ModelAndView displayIndex(Request request, Response response) {
        return render("admin/index");
    }
}
