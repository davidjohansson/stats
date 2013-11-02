package readers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import model.BodyStats;
import model.SpreadSheetApi;

import org.junit.Test;

import authentication.SpreadSheetAuthenticator;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
public class GoogleSpreadSheetStatsReaderUnitTest {

    
    @Test
    public void should_call_get_line_for_date() throws Exception{
       
        //Given
        SpreadSheetApi api = mock(SpreadSheetApi.class);
        SpreadSheetAuthenticator authenticator = mock(SpreadSheetAuthenticator.class);
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(authenticator, api);
        
        //When
        String dateString = "dateString";
        reader.readStats(dateString);

        //Then
        verify(api).getDataByFirstColumnValue(eq(dateString), any(SpreadsheetService.class), eq(BodyStats.class));
    }
}
