package huji.ac.il.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by yossi on 1/1/2015.
 */
public class Clock {
    public static long getCurrentTimeMili() {
        return System.currentTimeMillis();
    }

    public static String getHumanTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date resultdate = new Date( System.currentTimeMillis());
        return sdf.format(resultdate);
    }

    public static String getHumanTime(long epoch) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date resultdate = new Date(epoch);
        return sdf.format(resultdate);
    }
}
