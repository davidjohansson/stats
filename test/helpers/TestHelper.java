package helpers;

import model.BodyStats;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class TestHelper {

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
}
