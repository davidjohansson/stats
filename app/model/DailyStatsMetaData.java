package model;

public class DailyStatsMetaData implements StatsMetaData {

    @Override
    public String getDailyWorkSheet() {
        return "Daily daily stats";
    }

    @Override
    public String getWeeklyWorkSheet() {
        return "Stats";
    }

    @Override
    public String getSpreadSheet() {
        return "Benchmark";
    }
}
