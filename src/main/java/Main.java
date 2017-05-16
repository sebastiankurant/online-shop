import codecool_shop.Application;
import codecool_shop.controller.*;
import codecool_shop.dao.SgliteJDSCConnector;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.SQLException;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Main {


    public static void main(String[] args) {
        Application.runApp().start();
    }

}