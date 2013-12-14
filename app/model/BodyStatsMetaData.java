package model;

public class BodyStatsMetaData implements StatsMetaData {

    @Override
    public String getDailyWorkSheet() {
        return "Stats";
    }

    @Override
    public String getWeeklyWorkSheet() {
        return null;
    }

    @Override
    public String getSpreadSheet() {
        return "Benchmark";
    }
}
