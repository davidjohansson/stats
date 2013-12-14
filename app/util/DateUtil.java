package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public String formatDate(Date time) {
        return format.format(time);
    }

    public String getStartOfWeek(String formatDate) throws ParseException {
        Date parse = format.parse(formatDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        
        return format.format(cal.getTime());
    }

    public String getDateInSameWeek(String dailyDate) throws ParseException {
        Date parse = format.parse(dailyDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == Calendar.MONDAY){
            cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        } else{
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        return format.format(cal.getTime());
    }
}
