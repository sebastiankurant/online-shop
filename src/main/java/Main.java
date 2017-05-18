import codecool_shop.Application;


public class Main {


	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--init-db")) {
			Application.restartTables();

		} else if (args.length > 0 && args[0].equals("--migrate-db")) {
			Application.fillIfNotExistTables();
		}
		Application.runApp().start();
	}

}