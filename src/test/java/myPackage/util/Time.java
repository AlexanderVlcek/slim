package myPackage.util;

import net.thucydides.core.annotations.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    private static final Logger log = LogManager.getLogger(Time.class);

    @Step("Returns formatted date, e.g. format dd.MM.yyyy ")
    public static String getMonthYear(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String nextYear = simpleDateFormat.format(date);
        log.info("Calculated year and month:" + nextYear);
        return nextYear;
    }
}
