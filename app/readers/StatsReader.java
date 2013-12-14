package readers;

import model.PeriodizedStatsWrapper;
import model.SpreadSheetApi;
import model.SpreadSheetException;
import authentication.SpreadSheetAuthenticator;


public interface StatsReader {

    public <T> PeriodizedStatsWrapper<T> readStats(String any, Class<T> clazz) throws SpreadSheetException;
    
    public SpreadSheetAuthenticator getAuthenticator();

    public SpreadSheetApi getApi();

}
