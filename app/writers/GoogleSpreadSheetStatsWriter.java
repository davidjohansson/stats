package writers;

import java.text.ParseException;

import javax.inject.Inject;

import model.PeriodizedStatsWrapper;
import model.SpreadSheetApi;
import model.SpreadSheetException;
import model.StatsMetaData;
import model.StatsMetaData.UpdateMode;
import model.StatsMetaDataHelper;
import readers.StatsReader;
import util.DateUtil;
import authentication.SpreadSheetAuthenticator;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;

public class GoogleSpreadSheetStatsWriter implements StatsWriter {

    @Inject
    SpreadSheetAuthenticator authenticator;

    @Inject 
    SpreadSheetApi api;

    @Inject
    DateUtil dateUtil;

    @Inject
    StatsReader reader;

    public GoogleSpreadSheetStatsWriter(SpreadSheetAuthenticator authenticator, SpreadSheetApi api, DateUtil dateUtil, StatsReader reader) {
        this.api = api;
        this.authenticator = authenticator;
        this.dateUtil = dateUtil;
        this.reader = reader;
    }

    public GoogleSpreadSheetStatsWriter() {
    }

    @Override
    public <T> PeriodizedStatsWrapper<T> writeStats(String colValue, T stats) throws SpreadSheetException {
        SpreadsheetService service = createService();
        try {
            T daily = null;
            T weekly = null;

            authenticator.authenticateService(service);

            T existingDailyData = getExistingDailyData(colValue, stats);
            StatsMetaData metaData = StatsMetaDataHelper.getMetaData(stats.getClass());
            String spreadSheet = metaData.getSpreadSheet();
            String dailyWorkSheet = metaData.getDailyWorkSheet();
            String weeklyWorkSheet = metaData.getWeeklyWorkSheet();
            SpreadSheetIntegrationData data = new SpreadSheetIntegrationData(service, spreadSheet, dailyWorkSheet, UpdateMode.OVERWRITE);

            daily = api.writeDataByFirstColumnValue(colValue, stats, data);

            if(weeklyWorkSheet != null && weeklyWorkSheet.length() > 0) {
                String weeklyString = dateUtil.getStartOfWeek(colValue);
                data = new SpreadSheetIntegrationData(service, spreadSheet, weeklyWorkSheet, UpdateMode.ACCUMULATE);
                backoutAlreadyWrittenDailyStats(stats, existingDailyData, data, weeklyString);
                weekly = api.writeDataByFirstColumnValue(weeklyString, stats, data);
            }

            return new PeriodizedStatsWrapper<T>(daily, weekly);
        } catch (AuthenticationException e) {
            throw new SpreadSheetException(e);
        } catch (ParseException e) {
            throw new SpreadSheetException(e);
        }
    }


    private <T> void backoutAlreadyWrittenDailyStats(T stats, T existingRowData,
            SpreadSheetIntegrationData data, String weeklyString)
            throws SpreadSheetException {
        if(existingRowData != null && !StatsHelper.isEmpty(existingRowData)){
            //T backOutStats = StatsHelper.<T>getBackOutStats(stats);
            T backOutStats = StatsHelper.<T>getBackOutStats(existingRowData);
            api.writeDataByFirstColumnValue(weeklyString, backOutStats, data);
        }
    }

    private <T> T getExistingDailyData(String colValue, T stats) throws SpreadSheetException{
        
        PeriodizedStatsWrapper<T> readStats = (PeriodizedStatsWrapper<T>) reader.readStats(colValue, stats.getClass());
        T existingDaily = readStats.getDaily();
        return existingDaily;
    }

    private SpreadsheetService createService() {
        SpreadsheetService service = new SpreadsheetService(
        "MySpreadsheetIntegration-v1");
        service.setProtocolVersion(SpreadsheetService.Versions.V3);
        return service;
    }

}
