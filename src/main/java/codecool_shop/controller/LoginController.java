package codecool_shop.controller;

import codecool_shop.model.Product;
import codecool_shop.model.User;
import spark.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static codecool_shop.utilities.RequestUtil.getQueryPassword;
import static codecool_shop.utilities.RequestUtil.getQueryUsername;


/**
 * Created by pgurdek on 13.05.17.
 */
public class LoginController extends BaseController{
    UserController userController = new UserController();

    public ModelAndView serveLoginPage(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        return new ModelAndView(params, "login");
    }

    public ModelAndView handleLoginPost(Request request, Response response) throws SQLException {

        Map<String, Object> model = new HashMap<>();
        User currentUser = userController.authenticate(getQueryUsername(request), getQueryPassword(request));
        if (currentUser == null) {
            model.put("authenticationFailed", true);
            return new ModelAndView(model, "login");
        }
        request.session().attribute("authenticationSucceeded", true);
        request.session().attribute("username", currentUser.getUsername());
        request.session().attribute("id", currentUser.getId());
        request.session().attribute("type",currentUser.getType());
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", currentUser.getUsername());
        if (request.session().attribute("type").equals("admin")){
            response.redirect("/admin/");
        }
        else{
            response.redirect("/");
        }
        return render(model, "login");
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
        if (request.session().attribute("type").equals("customer") ) {
            response.redirect("/");
        }
    }

}
