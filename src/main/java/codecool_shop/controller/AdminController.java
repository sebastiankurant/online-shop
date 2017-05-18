package codecool_shop.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Created by pgurdek on 11.05.17.
 */
public class AdminController extends BaseController {

    public ModelAndView displayIndex(Request request, Response response) {
        return render("admin/index");
    }
}
