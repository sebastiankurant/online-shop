package codecool_shop.controller;

import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Galileo on 5/17/17.
 */
public class BaseController {

    protected ModelAndView render(Map<String, Object> parameters, String viewName) {
        return new ModelAndView(parameters, viewName);
    }

    protected ModelAndView render(String viewName) {
        Map<String, Object> parameters = new HashMap<>();
        return new ModelAndView(parameters, viewName);
    }
}
