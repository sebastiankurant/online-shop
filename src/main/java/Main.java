import com.codecool.shop.controller.MainMenuController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.SgliteJDSCConnector;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if(args.length>0 && args[0].equals("--create-tables")){
            try{
            SgliteJDSCConnector.createTables();
            SgliteJDSCConnector.makeSeeds();
            } catch (SQLException e) {
                System.out.println("Connection failed");
                e.printStackTrace();
            }
        }

        MainMenuController menu = new MainMenuController();
        menu.mainMenuAction();

    }


}
