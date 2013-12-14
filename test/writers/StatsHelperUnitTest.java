package writers;

import helpers.TestHelper;
import model.BodyStats;
import model.DailyStats;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class StatsHelperUnitTest {

    @Test
    public void should_create_backout_of_body_stats() throws Exception{
        
        BodyStats bodyStats = BodyStats.getSampleBodyStats();
        
        BodyStats backOutStats = StatsHelper.getBackOutStats(bodyStats);
        
        TestHelper.assertBröst(backOutStats, bodyStats.getBrost() * -1);
        TestHelper.assertMage(backOutStats, bodyStats.getMage() * -1);
        TestHelper.assertNacke(backOutStats, bodyStats.getNacke() * -1);
        TestHelper.assertFett2(backOutStats, bodyStats.getFett2() * -1);
    }
    
    @Test
    public void should_create_backout_of_daily_stats() throws Exception{
        
        //Given
        DailyStats dailyStats = DailyStats.getSampleDailyStats();
        
        //When
        DailyStats backOutStats = StatsHelper.getBackOutStats(dailyStats);
        
        //Then
        TestHelper.assertMat(backOutStats, dailyStats.getMat() * -1);
        TestHelper.assertTraning(backOutStats, dailyStats.getTraning() * -1);
        TestHelper.assertFasta(backOutStats, dailyStats.getFasta() * -1);
    }
    

    @Test
    public void should_say_non_empty_object_is_not_empty() throws Exception{
        //Given
        DailyStats dailyStats = DailyStats.getSampleDailyStats();
        
        //When
        boolean isEmpty = StatsHelper.isEmpty(dailyStats);
        
        //Then
        assertTrue("Object is not empty", !isEmpty);
    }
    
    @Test
    public void should_say_non_object_is_empty() throws Exception{
        //Given
        DailyStats dailyStats = DailyStats.getEmptyDailyStats();
        
        //When
        boolean isEmpty = StatsHelper.isEmpty(dailyStats);
        
        //Then
        assertTrue("Object is empty", isEmpty);
    }
}
