package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String getAirbnbDate(int daysFromToday) {
        LocalDate date = LocalDate.now().plusDays(daysFromToday);
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getAirbnbDateRange(String checkIn, String checkOut) {

        LocalDate in = LocalDate.parse(checkIn);
        LocalDate out = LocalDate.parse(checkOut);
        DateTimeFormatter monthDay = DateTimeFormatter.ofPattern("MMM d");
        DateTimeFormatter monthDayYear = DateTimeFormatter.ofPattern("MMM d, yyyy");

        // both same month?
        if (in.getMonth() == out.getMonth() && in.getYear() == out.getYear()) {
            String start = in.format(monthDay);        // Dec 2
            String end = String.valueOf(out.getDayOfMonth()); // 9
            return start + " – " + end;
        }
        // Different months, same year
        else if (in.getYear() == out.getYear()) {
            // Different months, same year
            String start = in.format(monthDay);   // Dec 27
            String end = out.format(monthDay);    // Jan 3
            return start + " – " + end;
        } else {
            // Different years
            String start = in.format(monthDayYear);   // Dec 27, 2025
            String end = out.format(monthDayYear);    // Jan 3, 2026
            return start + " – " + end;
        }
    }

}
