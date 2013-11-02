package readers;

import authentication.SpreadSheetAuthenticator;
import model.BodyStats;
import model.SpreadSheetApi;


public interface StatsReader {

    BodyStats readStats(String any);
    
    SpreadSheetAuthenticator getAuthenticator();

    SpreadSheetApi getApi();

}
