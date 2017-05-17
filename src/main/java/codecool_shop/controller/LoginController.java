package codecool_shop.controller;

import spark.*;

import java.util.HashMap;
import java.util.Map;

import static codecool_shop.utilities.RequestUtil.getQueryPassword;
import static codecool_shop.utilities.RequestUtil.getQueryUsername;


/**
 * Created by pgurdek on 13.05.17.
 */
public class LoginController {
    UserController userController = new UserController();

    public ModelAndView serveLoginPage(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        return new ModelAndView(params, "login");
    }

    public ModelAndView handleLoginPost(Request request, Response response) {
        Map<String, Object> model = new HashMap<>();
        if (!userController.authenticate(getQueryUsername(request), getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return new ModelAndView(model, "login");
        }
        Session userSession = request.session(true);
        userSession.attribute("username", getQueryUsername(request));
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", getQueryUsername(request));
        response.redirect("/admin/");
        return new ModelAndView(model, "login");
    }

    public Route handleLogoutPost(Request request, Response response) {
        System.out.println("jestem");
        request.session().removeAttribute("currentUser");
        request.session().removeAttribute("username");
        request.session().attribute("loggedOut", true);
        response.redirect("/");
        return null;
    }

    public void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            response.redirect("/login/");
        }
    }

}