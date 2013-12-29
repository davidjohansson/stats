package readers;

import java.text.ParseException;

import javax.inject.Inject;

import util.DateUtil;
import writers.SpreadSheetIntegrationData;

import model.PeriodizedStatsWrapper;
import model.StatsMetaData;
import model.StatsMetaDataHelper;
import api.SpreadSheetApi;
import api.SpreadSheetException;
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
    public <T> PeriodizedStatsWrapper<T> readStats(String colValue, Class<T> statsClass) throws SpreadSheetException  {

        T dailyStats = null;
        T weeklyStats = null;

        StatsMetaData metaData = StatsMetaDataHelper.getMetaData(statsClass);
        SpreadsheetService service = createService();
        String spreadSheet = metaData.getSpreadSheet();
        String dailyWorkSheet = metaData.getDailyWorkSheet();
        String weeklyWorkSheet = metaData.getWeeklyWorkSheet();
        
        try {
            authenticator.authenticateService(service);
            
            SpreadSheetIntegrationData data = new SpreadSheetIntegrationData(service, spreadSheet, dailyWorkSheet);
            dailyStats = api.getDataByFirstColumnValue(colValue, statsClass, data);
            
            if(weeklyWorkSheet != null && weeklyWorkSheet.length() > 0){
                data = new SpreadSheetIntegrationData(service, spreadSheet, weeklyWorkSheet);
                weeklyStats = api.getDataByFirstColumnValue(new DateUtil().getStartOfWeek(colValue), statsClass, data);
            }
            
            return new PeriodizedStatsWrapper<T>(dailyStats, weeklyStats);
        } catch (ServiceException e) {
            throw new SpreadSheetException(e);
        } catch (ParseException e) {
            throw new SpreadSheetException(e);
        } 
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