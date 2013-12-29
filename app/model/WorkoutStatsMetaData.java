package model;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class WorkoutStatsMetaData implements StatsMetaData {

    private Config config;

    public WorkoutStatsMetaData(){
        config = ConfigFactory.load();
    }

    @Override
    public String getDailyWorkSheet() {
        return config.getString("bodystats.dailyworksheet");
    }

    @Override
    public String getWeeklyWorkSheet() {
        return null;
    }

    @Override
    public String getSpreadSheet() {
        return config.getString("bodystats.spreadsheet");
    }
}
