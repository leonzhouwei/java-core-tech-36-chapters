package cn.edu.xidian.sc.leonzhou;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateTimeUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private DateTimeUtil() {
    }

    public static String toString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(date);
    }

}
