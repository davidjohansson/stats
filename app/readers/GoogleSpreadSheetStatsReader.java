package readers;

import javax.inject.Inject;

import model.BodyStats;
import model.SpreadSheetApi;
import model.SpreadSheetException;
import authentication.SpreadSheetAuthenticator;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.ServiceException;


public class GoogleSpreadSheetStatsReader implements StatsReader {

    @Inject
    SpreadSheetAuthenticator authenticator;

    @Inject 
    SpreadSheetApi api;

    public GoogleSpreadSheetStatsReader(SpreadSheetAuthenticator authenticator, SpreadSheetApi api) {
        this.api = api;
        this.authenticator = authenticator;
    }

    public GoogleSpreadSheetStatsReader() {
    }

    @Override
    public BodyStats readStats(String colValue) {
        SpreadsheetService service = createService();
        try {
            authenticator.authenticateService(service);
            return api.getDataByFirstColumnValue(colValue, service, BodyStats.class);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SpreadSheetException e) {
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

    @Override
    public SpreadSheetAuthenticator getAuthenticator() {
        return authenticator;
    }

    @Override
    public SpreadSheetApi getApi() {
        return api;
    }
}