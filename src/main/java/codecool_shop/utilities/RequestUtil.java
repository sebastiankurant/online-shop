package codecool_shop.utilities;

import spark.Request;

public class RequestUtil {

    public static String getQueryUsername(Request request) {
        return request.queryParams("username");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }

}
