package readers;

import util.DateUtil;
import writers.GoogleSpreadSheetStatsWriter;
import writers.StatsWriter;
import api.GoogleSpreadSheetApi;
import api.SpreadSheetApi;
import authentication.ClientLoginAuthenticator;
import authentication.SpreadSheetAuthenticator;

import com.google.inject.AbstractModule;

public class GoogleReaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(StatsReader.class).to(GoogleSpreadSheetStatsReader.class);
        bind(SpreadSheetAuthenticator.class).to(ClientLoginAuthenticator.class);
        bind(SpreadSheetApi.class).to(GoogleSpreadSheetApi.class);
        bind(StatsWriter.class).to(GoogleSpreadSheetStatsWriter.class);
        bind(DateUtil.class);
    }
}
