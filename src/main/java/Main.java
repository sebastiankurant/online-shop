import java.sql.SQLException;
import com.codecool.shop.controller.MainMenuController;
import com.codecool.shop.dao.SgliteJDSCConnector;

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
