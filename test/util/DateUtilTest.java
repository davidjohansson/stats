package util;

import java.util.Calendar;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DateUtilTest {
    

    @Test
    public void shouldReturnFormattedStringFromDate(){
       //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        
        //When
        String formatDate = new DateUtil().formatDate(cal.getTime());

        //Then
        assertThat(formatDate, equalTo("2011-01-01"));
    }
    
    @Test
    public void shoulReturnStartOfWeekAsStringForMidMonth() throws Exception{
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DATE, 17);
        
        //When
        DateUtil dateUtil = new DateUtil();
        String startOfWeekString = dateUtil.getStartOfWeek(dateUtil.formatDate(cal.getTime()));
        
        //Then
        assertThat(startOfWeekString, equalTo("2013-11-11"));
    }
    
    @Test
    public void shoulReturnStartOfWeekAsStringForWeekCrossingMonth() throws Exception{
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DATE, 1);
        
        //When
        DateUtil dateUtil = new DateUtil();
        String startOfWeekString = dateUtil.getStartOfWeek(dateUtil.formatDate(cal.getTime()));
        
        //Then
        assertThat(startOfWeekString, equalTo("2013-10-28"));
    }
    
    @Test
    public void shoulReturnStartOfWeekAsStringForWeekCrossingYear() throws Exception{
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 3);
        
        //When
        DateUtil dateUtil = new DateUtil();
        String startOfWeekString = dateUtil.getStartOfWeek(dateUtil.formatDate(cal.getTime()));
        
        //Then
        assertThat(startOfWeekString, equalTo("2013-12-30"));
    }
    
    @Test
    public void shoulReturnStartOfWeekAsStringForWeekCrossingLeapDay() throws Exception{
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DATE, 4);
        
        //When
        DateUtil dateUtil = new DateUtil();
        String startOfWeekString = dateUtil.getStartOfWeek(dateUtil.formatDate(cal.getTime()));
        
        //Then
        assertThat(startOfWeekString, equalTo("2016-02-29"));
    }
    
    @Test
    public void shouldCreateDateInSameWeekWednesday() throws Exception{
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DATE, 13);
        
        //When
        
        DateUtil dateUtil = new DateUtil();
        String dayInSameWeek = dateUtil.getDateInSameWeek(dateUtil.formatDate(cal.getTime()));
        
        //Then
        assertThat(dayInSameWeek, equalTo("2013-11-11"));
    }
    
    @Test
    public void shouldCreateDateInSameWeekMonday() throws Exception{
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DATE, 11);
        
        //When
        DateUtil dateUtil = new DateUtil();
        String dayInSameWeek = dateUtil.getDateInSameWeek(dateUtil.formatDate(cal.getTime()));
        
        //Then
        assertThat(dayInSameWeek, equalTo("2013-11-12"));
    }

    @Test
    public void shouldCreateDateInSameWeekSunday() throws Exception{
        //Given
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2013);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 15);

        //When
        DateUtil dateUtil = new DateUtil();
        String dayInSameWeek = dateUtil.getDateInSameWeek(dateUtil.formatDate(cal.getTime()));

        //Then
        assertThat(dayInSameWeek, equalTo("2013-12-09"));
    }
}
