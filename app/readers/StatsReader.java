package readers;

import model.PeriodizedStatsWrapper;
import api.SpreadSheetApi;
import api.SpreadSheetException;
import authentication.SpreadSheetAuthenticator;


public interface StatsReader {

    public <T> PeriodizedStatsWrapper<T> readStats(String any, Class<T> clazz) throws SpreadSheetException;
    
    public SpreadSheetAuthenticator getAuthenticator();

    public SpreadSheetApi getApi();

}
