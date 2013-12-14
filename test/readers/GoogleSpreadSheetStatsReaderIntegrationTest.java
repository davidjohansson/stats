package readers;
import helpers.TestHelper;
import model.BodyStats;
import model.GoogleSpreadSheetApi;
import model.PeriodizedStatsWrapper;

import org.junit.BeforeClass;
import org.junit.Test;

import authentication.ClientLoginAuthenticator;


public class GoogleSpreadSheetStatsReaderIntegrationTest {

    private static PeriodizedStatsWrapper<BodyStats> bodyStats;

    @BeforeClass
    public static void setup() throws Exception{
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(new ClientLoginAuthenticator(), new GoogleSpreadSheetApi());
        bodyStats = reader.readStats("2013-09-20", BodyStats.class);
    }

    @Test
    public void should_set_chest() throws Exception {
        TestHelper.assertBröst(bodyStats.getDaily(), BodyStats.SAMPLE_BROST);
    }

    @Test
    public void should_set_belly() throws Exception{
        //Then
        TestHelper.assertMage(bodyStats.getDaily(), BodyStats.SAMPLE_MAGE);
    }

    @Test
    public void should_set_weight() throws Exception {
        TestHelper.assertVikt(bodyStats.getDaily(), BodyStats.SAMPLE_VIKT);
    }

    @Test
    public void should_set_neck() throws Exception {
        TestHelper.assertNacke(bodyStats.getDaily(), BodyStats.SAMPLE_NACKE);
    }

    @Test
    public void should_set_fat() throws Exception {
        TestHelper.assertFett2(bodyStats.getDaily(), BodyStats.SAMPLE_FETT2);
    }
}
