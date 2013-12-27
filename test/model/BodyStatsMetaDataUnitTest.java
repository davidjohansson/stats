package model;

import org.junit.Assert;
import org.junit.Test;

public class BodyStatsMetaDataUnitTest {

    @Test
    public void should_read_spreadsheet_from_test_classpath() throws Exception{
        Assert.assertEquals(new BodyStatsMetaData().getSpreadSheet(), "apptest");
    }
    
    @Test
    public void should_read_dailyworksheet_from_test_classpath() throws Exception{
        Assert.assertEquals(new BodyStatsMetaData().getDailyWorkSheet(), "Stats");
    }
}
