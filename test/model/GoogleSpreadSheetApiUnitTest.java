package model;

import org.junit.Test;

import com.google.gdata.data.spreadsheet.ListEntry;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class GoogleSpreadSheetApiUnitTest {
    
    @Test
    public void should_serialize_to_row() throws Exception{
        //Given
        BodyStats bodyStats = BodyStats.getSampleBodyStats();
        String date = "2013-11-01";
        
        //When
        ListEntry row = new GoogleSpreadSheetApi().serializeToRow(bodyStats, date);
        
        //Then
        assertThat(row.getCustomElements().getValue("Datum"), is(date));
        assertThat(row.getCustomElements().getValue("Nacke"), is(Double.toString(BodyStats.SAMPLE_NACKE)));
        assertThat(row.getCustomElements().getValue("Fett2"), is(Double.toString(BodyStats.SAMPLE_FETT2)));
        assertThat(row.getCustomElements().getValue("Mage"), is(Double.toString(BodyStats.SAMPLE_MAGE)));
        assertThat(row.getCustomElements().getValue("Vikt"), is(Double.toString(BodyStats.SAMPLE_VIKT)));
        assertThat(row.getCustomElements().getValue("Brost"), is(Double.toString(BodyStats.SAMPLE_BROST)));
    }
}
