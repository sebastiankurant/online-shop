package codecool_shop;

import codecool_shop.controller.*;
import codecool_shop.dao.SgliteJDSCConnector;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.Connection;
import java.sql.SQLException;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by pgurdek on 16.05.17.
 */
public class Application {

    private static Application app = new Application();
    private Connection connection = new SgliteJDSCConnector().connection();


    public static Connection getConnection() {
        return app.connection;
    }


    public static Application runApp() {
        return app;
    }

    public static void restartTables(){
        try {
            SgliteJDSCConnector temp = new SgliteJDSCConnector();
            temp.dropTables();
            temp.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can't drop and add tables.");
            System.exit(1);
        }
    }

    public static void fillIfNotExistTables(){
        try {
            SgliteJDSCConnector temp = new SgliteJDSCConnector();
            temp.createTablesIfNotExist();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can't fill with not exist tables.");
            System.exit(1);
        }
    }

    public void start() {

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        Boolean localhost = true;  //Prevent public files from caching
        if (localhost) {
            String projectDir = System.getProperty("user.dir");
            String staticDir = "/src/main/resources/public";
            staticFiles.externalLocation(projectDir + staticDir);
        } else {
            staticFileLocation("/public");
        }

        port(8888);
        enableDebugScreen();
        ProductController productController = new ProductController();
        ProductControllerAdmin productControllerAdmin = new ProductControllerAdmin();
        CategoryControllerAdmin catController = new CategoryControllerAdmin();
        SupplierControllerAdmin supplierController = new SupplierControllerAdmin();
        LoginController loginController = new LoginController();
        SessionController sessionController = new SessionController();
        AdminController adminController = new AdminController();
        BasketController basketController = new BasketController();

        path("/", () -> {
            before("/*", (req, res) -> { // Ensure that url have "/" on ened
                String path = req.pathInfo();
                if (!path.endsWith("/")) {
                    res.redirect(path + "/");
                }
                sessionController.manageBasketSession(req, res);
            });
            get("/", productController::displayProducts, new ThymeleafTemplateEngine());

//            Front End routes - Not secured routes
            post("/logout/", loginController::handleLogoutPost);
            path("/login/", () -> {
                before((request, response) -> {
                });
                get("/", loginController::serveLoginPage, new ThymeleafTemplateEngine());
                post("/", loginController::handleLoginPost, new ThymeleafTemplateEngine());
            });

//            Admin routes

            path("/basket/", () -> {
                get("/", basketController::getBasket, new ThymeleafTemplateEngine());
                get("/add/", basketController::addToBasket);
                post("/add/", basketController::addToBasket);
                post("/remove/product/", basketController::removeProduct);
            });
            path("/admin/", () -> {
                before("/*", (req, res) -> {
//                    loginController.ensureUserIsLoggedIn(req, res);
                });

                get("/", adminController::displayIndex, new ThymeleafTemplateEngine());
                path("/products", () -> {
                    get("/", productControllerAdmin::renderProducts, new ThymeleafTemplateEngine());
                    get("/add/", productControllerAdmin::addProduct, new ThymeleafTemplateEngine());
                    get("/edit/:id/", productControllerAdmin::editProduct, new ThymeleafTemplateEngine());
                    get("/past/", productControllerAdmin::pastProducts, new ThymeleafTemplateEngine());
                    get("/category/", productControllerAdmin::filterCategory, new ThymeleafTemplateEngine());
                    post("/add/","multipart/form-data", productControllerAdmin::addProductPost, new ThymeleafTemplateEngine());
                    post("/remove/:id/", productControllerAdmin::removeProduct);
                    post("/edit/:id/","multipart/form-data", productControllerAdmin::editProductPost);
                });

                path("/category", () -> {
                    get("/", catController::renderCategory, new ThymeleafTemplateEngine());
                    get("/add/", catController::addCategory, new ThymeleafTemplateEngine());
                    get("/edit/:id/", catController::editCategory, new ThymeleafTemplateEngine());
                    post("/add/", catController::addCategoryPost, new ThymeleafTemplateEngine());
                    post("/edit/:id/", catController::editCategoryPost, new ThymeleafTemplateEngine());
                    post("/remove/:id/", catController::removeCategory);
                });

                path("/supplier", () -> {
                    get("/", supplierController::renderSupplier, new ThymeleafTemplateEngine());
                    get("/add/", supplierController::add, new ThymeleafTemplateEngine());
                    get("/edit/:id/", supplierController::editSupplier, new ThymeleafTemplateEngine());
                    post("/add/", supplierController::addSupplierPost, new ThymeleafTemplateEngine());
                    post("/edit/:id/", supplierController::editSupplierPost, new ThymeleafTemplateEngine());
                    post("/remove/:id/", supplierController::removeSupplier);
                });
            });
        });


        // Using string/html to display not correct routes
        notFound("<!doctype html>\n" +
                "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "\n" +
                "<head th:replace=\"header :: copy\">\n" +
                "    <title>404</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h1>404</h1>\n" +
                "    <h3>Not Found</h3>\n" +
                "</div>\n" +
                "<div th:replace=\"footer :: copy\"></div>\n" +
                "</body>\n" +
                "</html>");
    }


}
