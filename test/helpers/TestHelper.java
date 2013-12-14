package helpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import model.BodyStats;
import model.DailyStats;

public class TestHelper {

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static void assertMage(BodyStats bodyStats, double value) {
        assertThat((double) bodyStats.getMage(), is(closeTo(value, 0.01)));
    }

    public static void assertVikt(BodyStats bodyStats, double value) {
        assertThat((double) bodyStats.getVikt(), is(closeTo(value, 0.01)));
    }

    public static void assertFett2(BodyStats bodyStats, double value) {
        assertThat((double) bodyStats.getFett2(), is(closeTo(value, 0.01)));
    }

    public static void assertBröst(BodyStats bodyStats, double value) {
        assertThat((double) bodyStats.getBrost(), is(closeTo(value, 0.01)));
    }

    public static void assertNacke(BodyStats bodyStats, double value) {
        assertThat((double) bodyStats.getNacke(), is(closeTo(value, 0.01)));
    }
    
    public static void assertMat(DailyStats dailyStats, int value) {
        assertThat(dailyStats.getMat(), is(value));
    }

    public static void assertFasta(DailyStats stats, int value) {
        assertThat(stats.getFasta(), is(value));
    }
    
    public static void assertTraning(DailyStats dailyStats, int value) {
        assertThat(dailyStats.getTraning(), is(value));
    }

    public static String getRandomColumnKey() {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1900, 2010);
        gc.set(GregorianCalendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
        
        return TestHelper.format.format(gc.getTime());

    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }


}
