package writers;

import java.util.UUID;

import helpers.TestHelper;
import model.BodyStats;
import model.GoogleSpreadSheetApi;

import org.junit.BeforeClass;
import org.junit.Test;

import readers.GoogleSpreadSheetStatsReader;

import writers.GoogleSpreadSheetStatsWriter;
import authentication.ClientLoginAuthenticator;

public class GoogleSpreadSheetStatsWriterIntegrationTest {

    private static BodyStats readStats;

    @BeforeClass
    public static void setup() throws Exception{
        String colValue = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);
        BodyStats bodyStats = BodyStats.getSampleBodyStats();

        //When
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(new ClientLoginAuthenticator(), new GoogleSpreadSheetApi());
        writer.writeStats(colValue, bodyStats);

        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(new ClientLoginAuthenticator(), new GoogleSpreadSheetApi());
        readStats = reader.readStats(colValue);
    }

    @Test
    public void should_set_neck(){
        TestHelper.assertNacke(readStats, BodyStats.SAMPLE_NACKE);
    }

    @Test
    public void should_set_brost(){
        TestHelper.assertBröst(readStats, BodyStats.SAMPLE_BROST);
    }
}
