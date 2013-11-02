package model;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

public interface SpreadSheetApi {

    public <T> T getDataByFirstColumnValue(String dateString, SpreadsheetService service, Class<T> returnType) throws SpreadSheetException;

    public Object writeDataByFirstColumnValue(String colValue,
            SpreadsheetService service, BodyStats bodyStats) throws SpreadSheetException;

}
