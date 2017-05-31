package codecool_shop;

import codecool_shop.controller.*;
import codecool_shop.dao.SqliteJDBCConnector;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import static spark.Spark.*;

public class Application {

    private static Application app;
    private Connection connection = new SqliteJDBCConnector().connection();


    public static Connection getConnection() {
        return app.connection;
    }

    public static void dropConnection() throws SQLException {
        Application.getConnection().close();
    }

    public static Application runApp() {
        return app = new Application();
    }

    public static void restartTables() {
        try {
            SqliteJDBCConnector temp = new SqliteJDBCConnector();
            temp.dropTables();
            temp.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can't drop and add tables.");
            stop();
        }
    }

    public static void fillIfNotExistTables() {
        try {
            SqliteJDBCConnector temp = new SqliteJDBCConnector();
            temp.createTablesIfNotExist();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Can't fill with not exist tables.");
            stop();
        }
    }

    public static void isDbCreated() {
        try {
            File file = new File("src/main/resources/database.db");
            Boolean bool = file.exists();

            if (!bool) {
                System.out.println("There is no db, app shutdown's ...");
                System.exit(0);
            }
        } catch (Exception e) {

            // if any error occurs
            e.printStackTrace();
        }
    }

    public void start() {

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);
        ProductController productController = new ProductController();
        ProductControllerAdmin productControllerAdmin = new ProductControllerAdmin();
        CategoryControllerAdmin categoryControllerAdmin = new CategoryControllerAdmin();
        SupplierControllerAdmin supplierControllerAdmin = new SupplierControllerAdmin();
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
                    get("/supplier/", productControllerAdmin::filterSupplier, new ThymeleafTemplateEngine());
                    post("/add/","multipart/form-data", productControllerAdmin::addProductPost, new ThymeleafTemplateEngine());
                    post("/remove/:id/", productControllerAdmin::removeProduct);
                    post("/edit/:id/", "multipart/form-data", productControllerAdmin::editProductPost);
                });

                path("/category", () -> {
                    get("/", categoryControllerAdmin::renderCategory, new ThymeleafTemplateEngine());
                    get("/add/", categoryControllerAdmin::addCategory, new ThymeleafTemplateEngine());
                    get("/edit/:id/", categoryControllerAdmin::editCategory, new ThymeleafTemplateEngine());
                    post("/add/", categoryControllerAdmin::addCategoryPost, new ThymeleafTemplateEngine());
                    post("/edit/:id/", categoryControllerAdmin::editCategoryPost, new ThymeleafTemplateEngine());
                    post("/remove/:id/", categoryControllerAdmin::removeCategory);
                });

                path("/supplier", () -> {
                    get("/", supplierControllerAdmin::renderSupplier, new ThymeleafTemplateEngine());
                    get("/add/", supplierControllerAdmin::add, new ThymeleafTemplateEngine());
                    get("/edit/:id/", supplierControllerAdmin::editSupplier, new ThymeleafTemplateEngine());
                    post("/add/", supplierControllerAdmin::addSupplierPost, new ThymeleafTemplateEngine());
                    post("/edit/:id/", supplierControllerAdmin::editSupplierPost, new ThymeleafTemplateEngine());
                    post("/remove/:id/", supplierControllerAdmin::removeSupplier);
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
