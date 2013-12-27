package writers;
import static org.mockito.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import model.BodyStats;
import model.DailyStats;
import model.PeriodizedStatsWrapper;
import model.SpreadSheetApi;

import org.junit.BeforeClass;
import org.junit.Test;

import readers.StatsReader;

import util.DateUtil;
import authentication.SpreadSheetAuthenticator;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

public class GoogleSpreadSheetStatsWriterUnitTest{

    

    @BeforeClass
    public static void setup(){
        
    }

    @Test
    public void should_call_write_for_body_stats() throws Exception{
        //Given 
        SpreadSheetApi api = mock(SpreadSheetApi.class);
        SpreadSheetAuthenticator authenticator = mock(SpreadSheetAuthenticator.class);
        DateUtil dateUtil = mock(DateUtil.class);
        StatsReader reader = mock(StatsReader.class);
        when(reader.readStats(any(String.class), any(Class.class))).thenReturn(new PeriodizedStatsWrapper<BodyStats>(null, null));
        StatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, dateUtil, reader);
 
        BodyStats bodyStats = new BodyStats();
        String dateString = "dateString";
        
        //When
        writer.writeStats(dateString, bodyStats);

        //Then
        verify(authenticator).authenticateService(any(SpreadsheetService.class));
        verify(api).writeDataByFirstColumnValue(eq(dateString), eq(bodyStats), any(SpreadSheetIntegrationData.class));
    }
    
    @Test
    public void should_call_write_for_daily_stats() throws Exception{
        //Given
        SpreadSheetApi api = mock(SpreadSheetApi.class);
        SpreadSheetAuthenticator authenticator = mock(SpreadSheetAuthenticator.class);
        String dateString = "dateString";
        String weekDateString ="weekDateString";
        DateUtil dateUtil = mock(DateUtil.class);
        StatsReader reader = mock(StatsReader.class);
        when(reader.readStats(any(String.class), any(Class.class))).thenReturn(new PeriodizedStatsWrapper<DailyStats>(null, null));
        
        when(dateUtil.getStartOfWeek(dateString)).thenReturn(weekDateString);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, dateUtil, reader);
        
        DailyStats dailyStats = new DailyStats();
        
        //When
        writer.writeStats(dateString, dailyStats);

        //Then
        verify(authenticator).authenticateService(any(SpreadsheetService.class));
        verify(api).writeDataByFirstColumnValue(eq(dateString), eq(dailyStats), any(SpreadSheetIntegrationData.class));
        verify(api).writeDataByFirstColumnValue(eq(weekDateString), eq(dailyStats), any(SpreadSheetIntegrationData.class));
    }
    
    @Test
    public void should_back_out_old_daily_stat_from_weekly_stat_if_daily_stats_is_just_updated() throws Exception{
        //Given
        SpreadSheetApi api = mock(SpreadSheetApi.class);
        SpreadSheetAuthenticator authenticator = mock(SpreadSheetAuthenticator.class);
        StatsReader reader = mock(StatsReader.class);
        String dateString = "dateString";
        String weekDateString ="weekDateString";
        DateUtil dateUtil = mock(DateUtil.class);
        
        PeriodizedStatsWrapper<DailyStats> wrapper = new PeriodizedStatsWrapper<DailyStats>(DailyStats.getSampleDailyStats(), null);
        when(dateUtil.getStartOfWeek(dateString)).thenReturn(weekDateString);
        when(reader.readStats(dateString, DailyStats.class)).thenReturn(wrapper);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, dateUtil, reader);

        DailyStats dailyStats = DailyStats.getSampleDailyStats();

        //When
        writer.writeStats(dateString, dailyStats);

        //Then
        DailyStats backOutStats = StatsHelper.getBackOutStats(dailyStats);
        verify(authenticator).authenticateService(any(SpreadsheetService.class));
        verify(api).writeDataByFirstColumnValue(eq(dateString), eq(dailyStats), any(SpreadSheetIntegrationData.class));
        verify(api).writeDataByFirstColumnValue(eq(weekDateString), eq(backOutStats), any(SpreadSheetIntegrationData.class));
        verify(api).writeDataByFirstColumnValue(eq(weekDateString), eq(dailyStats), any(SpreadSheetIntegrationData.class));
    }
}