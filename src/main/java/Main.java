import codecool_shop.Application;

import java.sql.SQLException;


public class Main {


    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--init-db")) {
            Application.restartTables();

        } else if (args.length > 0 && args[0].equals("--migrate-db")) {
            Application.fillIfNotExistTables();
        }
        Application.runApp().start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Thread.sleep(200);
                Application.dropConnection();
                System.out.println("Shouting down ...");

            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }));
    }

}