package writers;

import helpers.TestHelper;

import model.BodyStats;
import model.DailyStats;
import model.PeriodizedStatsWrapper;
import model.WorkoutStats;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import readers.GoogleSpreadSheetStatsReader;
import util.DateUtil;
import api.GoogleSpreadSheetApi;
import authentication.ClientLoginAuthenticator;


public class GoogleSpreadSheetStatsWriterIntegrationTest {


    @Test
    public void should_write_sample_body_stats() throws Exception{

        //Given
        GoogleSpreadSheetApi api = new GoogleSpreadSheetApi();
        ClientLoginAuthenticator authenticator = new ClientLoginAuthenticator();
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(authenticator, api);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, new DateUtil(), reader);
        
        String bodyDate = TestHelper.getRandomColumnKey();
        System.out.println("Random date:" + bodyDate);

        //When
        BodyStats sampleBodyStats = BodyStats.getSampleBodyStats();
        writer.writeStats(bodyDate, sampleBodyStats);
        
        //Then
        PeriodizedStatsWrapper<BodyStats> accumulatedStats = reader.readStats(bodyDate, BodyStats.class);
        
        assertThat(accumulatedStats.getDaily(), equalTo(sampleBodyStats));
    }
    
    @Test
    public void should_write_sample_daily_stats() throws Exception{
        //Given
        GoogleSpreadSheetApi api = new GoogleSpreadSheetApi();
        ClientLoginAuthenticator authenticator = new ClientLoginAuthenticator();
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(authenticator, api);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, new DateUtil(), reader);
        
        String dailyDate = TestHelper.getRandomColumnKey();
        System.out.println("Random date:" + dailyDate);

        //When
        DailyStats sampleDailyStats = DailyStats.getSampleDailyStats();
        writer.writeStats(dailyDate, sampleDailyStats);
        
        //Then
        PeriodizedStatsWrapper<DailyStats> accumulatedStats = reader.readStats(dailyDate, DailyStats.class);
        assertThat(accumulatedStats.getWeekly(), equalTo(sampleDailyStats));
    }

    @Test
    public void should_write_sample_workout_stats() throws Exception{
        //Given
        GoogleSpreadSheetApi api = new GoogleSpreadSheetApi();
        ClientLoginAuthenticator authenticator = new ClientLoginAuthenticator();
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(authenticator, api);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, new DateUtil(), reader);
        
        String dailyDate = TestHelper.getRandomColumnKey();
        System.out.println("Random date:" + dailyDate);

        //When
        WorkoutStats sampleWorkoutStats = WorkoutStats.getSampleWorkoutStats();
        writer.writeStats(dailyDate, sampleWorkoutStats);
        
        //Then
        PeriodizedStatsWrapper<WorkoutStats> accumulatedStats = reader.readStats(dailyDate, WorkoutStats.class);
        assertThat(accumulatedStats.getDaily(), equalTo(sampleWorkoutStats));
    }

    @Test
    public void should_not_accumulate_if_two_dailys_written_the_same_day() throws Exception{
        //Given
        GoogleSpreadSheetApi api = new GoogleSpreadSheetApi();
        ClientLoginAuthenticator authenticator = new ClientLoginAuthenticator();
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(authenticator, api);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, new DateUtil(), reader);
        
        String dailyDate = TestHelper.getRandomColumnKey();
        System.out.println("Random date:" + dailyDate);

        //When
        writer.writeStats(dailyDate, DailyStats.getSampleDailyStatsAndAdd(10));
        writer.writeStats(dailyDate, DailyStats.getSampleDailyStats());

        //Then
        PeriodizedStatsWrapper<DailyStats> accumulatedStats = reader.readStats(dailyDate, DailyStats.class);
        TestHelper.assertMat(accumulatedStats.getWeekly(), DailyStats.SAMPLE_MAT);
    }

    @Test
    public void should_accumulate_mat_if_new_daily_stats_in_same_week() throws Exception{
        //Given
        DateUtil dateUtil = new DateUtil();
        GoogleSpreadSheetApi api = new GoogleSpreadSheetApi();
        ClientLoginAuthenticator authenticator = new ClientLoginAuthenticator();
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(authenticator, api);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, dateUtil, reader);
        
        String dailyDate = TestHelper.getRandomColumnKey();
        String dateInSameWeekAsDailyDate = dateUtil.getDateInSameWeek(dailyDate);
        System.out.println("Random date:" + dailyDate);
        System.out.println("Random date in same week:" + dateInSameWeekAsDailyDate);

        //When
        writer.writeStats(dailyDate, DailyStats.getSampleDailyStats());
        writer.writeStats(dateInSameWeekAsDailyDate, DailyStats.getSampleDailyStats());

        //Then
        PeriodizedStatsWrapper<DailyStats> accumulatedStats = reader.readStats(dailyDate, DailyStats.class);
        TestHelper.assertMat(accumulatedStats.getWeekly(), DailyStats.SAMPLE_MAT * 2);
    }
    /*

    @Test
    public void should_set_mat() throws Exception{
        TestHelper.assertMat(readDailyStats.getDaily(), DailyStats.SAMPLE_MAT);
    }

    @Test
=======
        String dailyDate = TestHelper.getRandomColumnKey();
        System.out.println("Random date:" + dailyDate);

        //When
        writer.writeStats(dailyDate, DailyStats.getSampleDailyStats());
        writer.writeStats(dailyDate, DailyStats.getSampleDailyStats());
        
        //Then
        PeriodizedStatsWrapper<DailyStats> accumulatedStats = reader.readStats(dailyDate, DailyStats.class);
        TestHelper.assertMat(accumulatedStats.getWeekly(), DailyStats.SAMPLE_MAT);
    }

    @Test
    public void should_accumulate_mat_if_new_daily_stats_in_same_week() throws Exception{
        //Given
        DateUtil dateUtil = new DateUtil();
        GoogleSpreadSheetApi api = new GoogleSpreadSheetApi();
        ClientLoginAuthenticator authenticator = new ClientLoginAuthenticator();
        GoogleSpreadSheetStatsReader reader = new GoogleSpreadSheetStatsReader(authenticator, api);
        GoogleSpreadSheetStatsWriter writer = new GoogleSpreadSheetStatsWriter(authenticator, api, dateUtil, reader);
        
        String dailyDate = TestHelper.getRandomColumnKey();
        String dateInSameWeekAsDailyDate = dateUtil.getDateInSameWeek(dailyDate);
        System.out.println("Random date:" + dailyDate);
        System.out.println("Random date in same week:" + dateInSameWeekAsDailyDate);

        //When
        writer.writeStats(dailyDate, DailyStats.getSampleDailyStats());
        writer.writeStats(dateInSameWeekAsDailyDate, DailyStats.getSampleDailyStats());

        //Then
        PeriodizedStatsWrapper<DailyStats> accumulatedStats = reader.readStats(dailyDate, DailyStats.class);
        TestHelper.assertMat(accumulatedStats.getWeekly(), DailyStats.SAMPLE_MAT * 2);
    }
    /*

    @Test
    public void should_set_mat() throws Exception{
        TestHelper.assertMat(readDailyStats.getDaily(), DailyStats.SAMPLE_MAT);
    }

    @Test
>>>>>>> 88e474424d2db14c533567641a207026cd353c79
    @Ignore("Fixa så att träning skrivs i spreadsheeten")
    public void should_set_traning() throws Exception{
        TestHelper.assertTraning(readDailyStats.getDaily(), DailyStats.SAMPLE_TRANING);
    }
    
    @Test
    public void should_set_neck(){
        TestHelper.assertNacke(readBodyStats.getDaily(), BodyStats.SAMPLE_NACKE);
    }

    @Test
    public void should_set_brost(){
        TestHelper.assertBröst(readBodyStats.getDaily(), BodyStats.SAMPLE_BROST);
    }
    */
}
