package model;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class DailyStatsMetaData implements StatsMetaData {

    private Config config;

    public DailyStatsMetaData(){
        config = ConfigFactory.load();
    }
    @Override
    public String getDailyWorkSheet() {
        return config.getString("dailystats.dailyworksheet");
    }

    @Override
    public String getWeeklyWorkSheet() {
        return config.getString("dailystats.weeklyworksheet");
    }

    @Override
    public String getSpreadSheet() {
        return config.getString("dailystats.spreadsheet");
    }
}
