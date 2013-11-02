package readers;
import helpers.TestHelper;
import model.BodyStats;
import model.GoogleSpreadSheetApi;

import org.junit.BeforeClass;
import org.junit.Test;

import authentication.ClientLoginAuthenticator;


public class GoogleSpreadSheetStatsReaderIntegrationTest {

    private static BodyStats bodyStats;

    @BeforeClass
    public static void setup(){
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(new ClientLoginAuthenticator(), new GoogleSpreadSheetApi());
        bodyStats = reader.readStats("2013-09-20");
    }

    @Test
    public void should_set_chest() throws Exception {
        TestHelper.assertBröst(bodyStats, 111.0f);
    }

    @Test
    public void should_set_belly() throws Exception{
        //Then
        TestHelper.assertMage(bodyStats, 222.0f);
    }

    @Test
    public void should_set_weight() throws Exception {
        TestHelper.assertVikt(bodyStats, 333.0f);
    }

    @Test
    public void should_set_neck() throws Exception {
        TestHelper.assertNacke(bodyStats, 444.0f);
    }

    @Test
    public void should_set_fat() throws Exception {
        TestHelper.assertFett2(bodyStats, 555.0f);
    }
}
