package codecool_shop.utilities;

import org.joda.time.DateTimeComparator;
import spark.Request;

import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.stream.Collectors;

public class UtilityClass {

    public String calculateClass(Date firstDate, Date secondDate) {
        Integer isEven = DateTimeComparator.getDateOnlyInstance().compare(firstDate, secondDate);
        if (isEven == 1) {
            return "incoming";
        } else if (isEven == 0) {
            return "now";
        }
        return "past";
    }

    public String getStringFromInputStream(Part part) {
        try {
            return new BufferedReader(new InputStreamReader(part.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDomainUrl(Request request) {
        if (request.protocol().equals("HTTP/1.1")) {
            return "http://" + request.host() + "/images/";
        }
        return "https://" + request.host() + "/images/";

    }
}
