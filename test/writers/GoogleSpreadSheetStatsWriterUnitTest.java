package writers;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import model.BodyStats;
import model.SpreadSheetApi;

import org.junit.Test;

import authentication.SpreadSheetAuthenticator;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

public class GoogleSpreadSheetStatsWriterUnitTest{

    @Test
    public void should_call_get_line_for_date() throws Exception{
       
        //Given
        SpreadSheetApi api = mock(SpreadSheetApi.class);
        SpreadSheetAuthenticator authenticator = mock(SpreadSheetAuthenticator.class);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api);
        BodyStats bodyStats = new BodyStats();
        String dateString = "dateString";
        
        //When
        writer.writeStats(dateString, bodyStats);

        //Then
        verify(authenticator).authenticateService(any(SpreadsheetService.class));
        verify(api).writeDataByFirstColumnValue(eq(dateString), any(SpreadsheetService.class), eq(bodyStats));
    }
}