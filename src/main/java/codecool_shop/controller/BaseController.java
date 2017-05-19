package codecool_shop.controller;

import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;


class BaseController {

    ModelAndView render(Map<String, Object> parameters, String viewName) {
        return new ModelAndView(parameters, viewName);
    }

    ModelAndView render(String viewName) {
        Map<String, Object> parameters = new HashMap<>();
        return new ModelAndView(parameters, viewName);
    }
}
