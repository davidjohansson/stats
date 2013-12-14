package model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;

import model.StatsMetaData.UpdateMode;

import org.junit.Test;
import org.mockito.Mockito;

import writers.SpreadSheetIntegrationData;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

public class GoogleSpreadSheetApiUnitTest {
    
    @Test
    public void should_serialize_to_row() throws Exception{
        //Given
        BodyStats bodyStats = BodyStats.getSampleBodyStats();
        String date = "2013-11-01";
        
        //When
        ListEntry row = new ListEntry();
        new GoogleSpreadSheetApi().serializeToRow(bodyStats, date, row, UpdateMode.OVERWRITE);
        
        //Then
        assertThat(row.getCustomElements().getValue("Datum"), is(date));
        assertThat(parseFloat(row, "Nacke"), is(BodyStats.SAMPLE_NACKE));
        assertThat(parseFloat(row, "Fett2"), is(BodyStats.SAMPLE_FETT2));
        assertThat(parseFloat(row, "Mage"), is(BodyStats.SAMPLE_MAGE));
        assertThat(parseFloat(row, "Vikt"), is(BodyStats.SAMPLE_VIKT));
        assertThat(parseFloat(row, "Brost"), is(BodyStats.SAMPLE_BROST));
    }
    
    @Test
    public void should_accumulate_values_for_daily_stats_row() throws Exception{
        //Given
        GoogleSpreadSheetApi googleSpreadSheetApi = new GoogleSpreadSheetApi();
        DailyStats stats = DailyStats.getSampleDailyStats();
        ListEntry row = new ListEntry();
        String date = "2010-10-10";
        googleSpreadSheetApi.serializeToRow(stats, date, row, UpdateMode.ACCUMULATE);

        //When
        googleSpreadSheetApi.serializeToRow(stats, date, row, UpdateMode.ACCUMULATE);

        //Then
        assertThat(row.getCustomElements().getValue("Datum"), is(date));
        assertThat(parseInt(row, "Mat"), is(DailyStats.SAMPLE_MAT * 2));
        assertThat(parseInt(row, "Traning"), is(DailyStats.SAMPLE_TRANING * 2));
        assertThat(parseInt(row, "Fasta"), is(DailyStats.SAMPLE_FASTA * 2));
    }

    @Test
    public void should_not_accumulate_value_for_body_stats_row() throws Exception{
        //Given
        GoogleSpreadSheetApi googleSpreadSheetApi = new GoogleSpreadSheetApi();
        BodyStats stats = BodyStats.getSampleBodyStats();

        ListEntry row = new ListEntry();
        String date = "2010-10-10";
        googleSpreadSheetApi.serializeToRow(stats, date, row, UpdateMode.OVERWRITE);

        //When
        googleSpreadSheetApi.serializeToRow(stats, date, row, UpdateMode.OVERWRITE);

        //Then
        assertThat(row.getCustomElements().getValue("Datum"), is(date));
        assertThat(parseFloat(row, "Nacke"), is(BodyStats.SAMPLE_NACKE));
        assertThat(parseFloat(row, "Fett2"), is(BodyStats.SAMPLE_FETT2));
        assertThat(parseFloat(row, "Mage"), is(BodyStats.SAMPLE_MAGE));
        assertThat(parseFloat(row, "Vikt"), is(BodyStats.SAMPLE_VIKT));
        assertThat(parseFloat(row, "Brost"), is(BodyStats.SAMPLE_BROST));
    }
    
    private double parseFloat(ListEntry row, String key)
            throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        return (nf.parse(row.getCustomElements().getValue(key))).doubleValue();
    }

    private int parseInt(ListEntry row, String key)
    throws ParseException {
        NumberFormat nf = NumberFormat.getInstance();
        return (nf.parse(row.getCustomElements().getValue(key))).intValue();
    }
    
    @Test
    public void should_update_row() throws Exception{
        //Given
        String colValue = "some val";
        WorksheetEntry worksheet = mock(WorksheetEntry.class);
        SpreadsheetService service = mock(SpreadsheetService.class);
        ExistingRowResolver rowResolver = mock(ExistingRowResolver.class);
        ListEntry row = mock(ListEntry.class);
        URL url = new URL("http://someplace");

        CustomElementCollection elements = mock(CustomElementCollection.class);
        when(row.getCustomElements()).thenReturn(elements);
        when(worksheet.getListFeedUrl()).thenReturn(url);;
        when(rowResolver.resolveRow(service, url, colValue)).thenReturn(row);
        
        SpreadSheetIntegrationData data = new SpreadSheetIntegrationData(service, "", "", UpdateMode.OVERWRITE);
  
        //When
        new GoogleSpreadSheetApi(rowResolver).writeStats(data, worksheet, colValue, BodyStats.getSampleBodyStats());

        //Then
        verify(row).update();
    }
    
    @Test
    public void should_insert_row() throws Exception{
        //Given
        String colValue = "some val";
        WorksheetEntry worksheet = mock(WorksheetEntry.class);
        SpreadsheetService service = mock(SpreadsheetService.class);
        ExistingRowResolver rowResolver = mock(ExistingRowResolver.class);
        URL url = new URL("http://someplace");

        when(worksheet.getListFeedUrl()).thenReturn(url);;
        when(rowResolver.resolveRow(service, url, colValue)).thenReturn(null);

        SpreadSheetIntegrationData data = new SpreadSheetIntegrationData(service, "", "", UpdateMode.OVERWRITE);
        
        //When
        new GoogleSpreadSheetApi(rowResolver).writeStats(data, worksheet, colValue, BodyStats.getSampleBodyStats());

        //Then
        verify(service).insert(eq(url), Mockito.<ListEntry>any(ListEntry.class));
    }
}
