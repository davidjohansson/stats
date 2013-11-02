package writers;

import javax.inject.Inject;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.ServiceException;

import authentication.SpreadSheetAuthenticator;
import model.BodyStats;
import model.SpreadSheetApi;
import model.SpreadSheetException;

public class GoogleSpreadSheetStatsWriter implements StatsWriter {

    @Inject
    SpreadSheetAuthenticator authenticator;

    @Inject 
    SpreadSheetApi api;

    public GoogleSpreadSheetStatsWriter(SpreadSheetAuthenticator authenticator, SpreadSheetApi api) {
        this.api = api;
        this.authenticator = authenticator;
    }

    public GoogleSpreadSheetStatsWriter() {
    }

    @Override
    public Object writeStats(String colValue, BodyStats bodyStats) throws SpreadSheetException {
        SpreadsheetService service = createService();
        try {
            authenticator.authenticateService(service);
            return api.writeDataByFirstColumnValue(colValue, service, bodyStats);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
}

    
    private SpreadsheetService createService() {
        SpreadsheetService service = new SpreadsheetService(
        "MySpreadsheetIntegration-v1");
        service.setProtocolVersion(SpreadsheetService.Versions.V3);
        return service;
    }

}
