package api;

import writers.SpreadSheetIntegrationData;

public interface SpreadSheetApi {

    public <T> T getDataByFirstColumnValue(String dateString, Class<T> returnType, SpreadSheetIntegrationData data) throws SpreadSheetException;

    public <T> T writeDataByFirstColumnValue(String colValue,
            T stats, SpreadSheetIntegrationData data) throws SpreadSheetException;
}
