package codecool_shop.controller;

import codecool_shop.Application;
import codecool_shop.dao.UserDao;
import codecool_shop.model.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static codecool_shop.utilities.RequestUtil.getQueryPassword;
import static codecool_shop.utilities.RequestUtil.getQueryUsername;


public class LoginController extends BaseController {
    UserController userController = new UserController(new UserDao(Application.getConnection()));

    public ModelAndView serveLoginPage(Request request, Response response) {
        Map<String, Object> parameters = new HashMap<>();
        return new ModelAndView(parameters, "login");
    }

    public ModelAndView handleLoginPost(Request request, Response response) throws SQLException {

        Map<String, Object> parameters = new HashMap<>();
        User currentUser = userController.authenticate(getQueryUsername(request), getQueryPassword(request));
        if (currentUser == null) {
            parameters.put("authenticationFailed", true);
            return new ModelAndView(parameters, "login");
        }
        request.session().attribute("authenticationSucceeded", true);
        request.session().attribute("username", currentUser.getUsername());
        request.session().attribute("id", currentUser.getId());
        request.session().attribute("type", currentUser.getType());
        parameters.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", currentUser.getUsername());
        if (request.session().attribute("type").equals("admin")) {
            response.redirect("/admin/");
        } else {
            response.redirect("/");
        }
        return render(parameters, "login");
    }

    public Route handleLogoutPost(Request request, Response response) {
        request.session().removeAttribute("currentUser");
        request.session().removeAttribute("username");
        request.session().attribute("loggedOut", true);
        request.session().attribute("authenticationSucceeded", false);

        response.redirect("/");
        return null;
    }

    public void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            response.redirect("/login/");
        }
        if (request.session().attribute("type").equals("customer")) {
            response.redirect("/");
        }
    }

}
