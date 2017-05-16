package utilities;

import org.joda.time.DateTimeComparator;

import java.util.Date;

/**
 * Created by pgurdek on 13.05.17.
 */
public class UtilityClass {
    public String calculateClass(Date firstDate, Date secondDate) {
        Integer isEven = DateTimeComparator.getDateOnlyInstance().compare(firstDate, secondDate);
        System.out.println(isEven);
        if (isEven == 1) {
            return "incoming";
        } else if (isEven == 0) {
            return "now";
        }
        return "past";
    }
}
